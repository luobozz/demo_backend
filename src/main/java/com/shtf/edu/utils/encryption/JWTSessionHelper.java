package com.shtf.edu.utils.encryption;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shtf.edu.bean.entity.SysAccount;
import com.shtf.edu.bean.entity.SysRole;
import com.shtf.edu.bean.exception.ForcedOfflineAuthException;
import com.shtf.edu.bean.api.system.account.enums.JWTSessionEnum;
import com.shtf.edu.bean.api.system.account.vo.JWTSession;
import com.shtf.edu.bean.api.system.account.vo.JWTSessionAccount;
import com.shtf.edu.constant.RedisConstants;
import com.shtf.edu.mapper.SysAccountMapper;
import com.shtf.edu.mapper.SysRoleMapper;
import com.shtf.edu.utils.spring.RedisTemplateHelper;
import com.shtf.edu.utils.spring.SpringContextHelper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;

import javax.security.auth.message.AuthException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JWTSessionHelper class
 *
 * @author chenlingyu
 * @date 2021/5/4 19:46
 */
public class JWTSessionHelper {

    /**
     * jwt的Session化处理，存储用户基本信息在session(redis)中
     * 分为session部分和account部分
     *
     * @param token
     * @param jwtSessionAccount
     * @return
     */
    public static JWTSession signJwtSession(String token, JWTSessionAccount jwtSessionAccount) throws AuthException {
        RedisTemplateHelper redisTemplateHelper = SpringContextHelper.getBean(RedisTemplateHelper.class);
        String sessionKey = RedisConstants.Key.JwtSession.JWT_SESSION + token;
        String sessionAccountKey = RedisConstants.Key.JwtSession.JWT_SESSION_ACCOUNT + jwtSessionAccount.getUuid();
        jwtSessionAccount.setToken(token);
        JWTSession jwtSession = new JWTSession();
        jwtSession.setAccountUuid(jwtSessionAccount.getUuid());
        jwtSession.setStatus(JWTSessionEnum.NORMAL.getContent());
        if (!(redisTemplateHelper.setOneKey(sessionKey, jwtSession, RedisConstants.Key.JwtSession.JWT_SESSION_EXPIRE)
                && redisTemplateHelper.setOneKey(sessionAccountKey, jwtSessionAccount, RedisConstants.Key.JwtSession.JWT_SESSION_ACCOUNT_EXPIRE))) {
            throw new AuthException("session写入失败");
        }
        return jwtSession;
    }

    private static JWTSessionAccount rebuildJWTSessionAccount(String accountUuid,String token,long expire) throws AuthException {
        JWTSessionAccount jwtSessionAccount=new JWTSessionAccount();
        RedisTemplateHelper redisTemplateHelper = SpringContextHelper.getBean(RedisTemplateHelper.class);
        SysAccountMapper sysAccountMapper=SpringContextHelper.getBean(SysAccountMapper.class);
        SysRoleMapper sysRoleMapper=SpringContextHelper.getBean(SysRoleMapper.class);
        String sessionAccountKey = RedisConstants.Key.JwtSession.JWT_SESSION_ACCOUNT + accountUuid;

        SysAccount sysAccount=sysAccountMapper.selectOne(new QueryWrapper<SysAccount>().eq("account","admin"));

        List<SysRole> sysRoles=sysRoleMapper.selectListByAccountUuId(sysAccount.getUuid());

        jwtSessionAccount.setRoleUuIds(
                sysRoles.stream()
                        .map(p->p.getUuid())
                        .collect(Collectors.toList()));
        BeanUtils.copyProperties(sysAccount,jwtSessionAccount);
        jwtSessionAccount.setToken(token);

        if(!redisTemplateHelper.setOneKey(sessionAccountKey, jwtSessionAccount, expire>0?expire:RedisConstants.Key.JwtSession.JWT_SESSION_ACCOUNT_EXPIRE)){
            throw new AuthException("session写入失败");
        }
        return jwtSessionAccount;
    }

    /**
     * jwt-session的持久化处理
     * 规则为：
     * 1. 以用户首次签发的jwt作为用户的refreshToken
     * 2. 用户持有的token的过期时间为过期第一要素，即用户持有token未过期则直接续发session(signJwtSession())，常在redis-session异常失效的情况下，正常情况下用户的token的失效时间小于session的失效时间，即不可能发生redis-session比用户token先失效的情况
     * 3. 用户每次进入时刷新redis-session的失效时间，key不变只是刷新过期时间
     * 4. 在redis-session真正失效后(这时用户token早就已失效了)，这时应是用户在失效时间内无任何操作则让用户执行logout
     * 5. session中有session的状态值用来执行主动签退等操作
     *
     * @param token
     * @return
     */
    public static JWTSession getJwtSession(String token, Claims jwtClaims) throws AuthException {
        JWTSession jwtSession = null;
        RedisTemplateHelper redisTemplateHelper = SpringContextHelper.getBean(RedisTemplateHelper.class);
        String sessionKey = RedisConstants.Key.JwtSession.JWT_SESSION + token;
        boolean hasKey = redisTemplateHelper.hasKey(sessionKey);
        boolean jwtExpiration = JWTHelper.isExpiration(jwtClaims);

        //没有session并且jwt过期
        if (!hasKey && !jwtExpiration) {
            throw new AuthException("非法登录");
        }
        //没有session但是jwt在首签
        else if (!hasKey && jwtExpiration) {
            //重新续发session
            jwtSession = signJwtSession(token, null);
        }

        jwtSession = jwtSession == null ? redisTemplateHelper.getValueForEntity(sessionKey, JWTSession.class) : jwtSession;

        return jwtSession;
    }

    /**
     * @param session
     * @param token
     * @return
     * @throws AuthException
     */
    public static JWTSessionAccount getJwtSessionAccount(JWTSession session,String token) throws AuthException {
        JWTSessionAccount jwtSessionAccount=new JWTSessionAccount();
        String sessionAccountKey = RedisConstants.Key.JwtSession.JWT_SESSION_ACCOUNT + session.getAccountUuid();
        RedisTemplateHelper redisTemplateHelper = SpringContextHelper.getBean(RedisTemplateHelper.class);
        if(redisTemplateHelper.hasKey(sessionAccountKey)){
            jwtSessionAccount=redisTemplateHelper.getValueForEntity(sessionAccountKey,JWTSessionAccount.class);
        }else {
            String sessionKey = RedisConstants.Key.JwtSession.JWT_SESSION + token;
            jwtSessionAccount=rebuildJWTSessionAccount(session.getAccountUuid(),token,redisTemplateHelper.getExpire(sessionKey));
        }
        return jwtSessionAccount;
    }


    public static void refreshJwtSession(String token, JWTSession session) throws AuthException {
        RedisTemplateHelper redisTemplateHelper = SpringContextHelper.getBean(RedisTemplateHelper.class);
        String sessionKey = RedisConstants.Key.JwtSession.JWT_SESSION + token;
        String sessionAccountKey = RedisConstants.Key.JwtSession.JWT_SESSION_ACCOUNT + session.getAccountUuid();

         //session状态检验
        if (session.getStatus().equals(JWTSessionEnum.BANNED.getContent())) {
            throw new ForcedOfflineAuthException(String.format("该用户处于【%s】异常状态", JWTSessionEnum.BANNED.getDescription()));
        }

        redisTemplateHelper.expire(sessionKey, RedisConstants.Key.JwtSession.JWT_SESSION_EXPIRE);
        redisTemplateHelper.expire(sessionAccountKey, RedisConstants.Key.JwtSession.JWT_SESSION_ACCOUNT_EXPIRE);

    }
}
