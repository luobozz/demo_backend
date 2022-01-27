package com.shtf.edu.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shtf.edu.bean.api.system.account.vo.JWTSessionAccount;
import com.shtf.edu.bean.entity.SysAccount;
import com.shtf.edu.bean.entity.SysRole;
import com.shtf.edu.mapper.SysAccountMapper;
import com.shtf.edu.mapper.SysRoleMapper;
import com.shtf.edu.mapper.SysRoleRouteMapper;
import com.shtf.edu.bean.entity.SysRoleRoute;
import com.shtf.edu.service.SysAccountService;
import com.shtf.edu.utils.encryption.JWTHelper;
import com.shtf.edu.utils.encryption.JWTSessionHelper;
import com.shtf.edu.utils.mybatis.UUIDHelper;
import com.shtf.edu.utils.responseMessage.ResponseMessage;
import com.shtf.edu.utils.responseMessage.ResponseMessageHandle;
import com.shtf.edu.utils.spring.RedisTemplateHelper;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.message.AuthException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenlingyu
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private SysAccountMapper sysAccountMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysAccountService sysAccountService;
    @Autowired
    private RedisTemplateHelper redisTemplateHelper;
    @Autowired
    private ResponseMessageHandle responseMessageHandle;
    @GetMapping("t1")
    public ResponseMessage t1() throws AuthException {

        if(true){
            throw new AuthenticationException("123");
        }

        SysAccount sysAccount=sysAccountMapper.selectOne(new QueryWrapper<SysAccount>().eq("account","admin"));
        List<SysRole> sysRoles=sysRoleMapper.selectListByAccountUuId(sysAccount.getUuid());

        String token= JWTHelper.signJwt(sysAccount.getUuid(),0);

        JWTSessionAccount jwtSessionAccount= new JWTSessionAccount();
        jwtSessionAccount.setRoleUuIds(
                sysRoles.stream()
                        .map(p->p.getUuid())
                        .collect(Collectors.toList()));
        BeanUtils.copyProperties(sysAccount,jwtSessionAccount);

        JWTSessionHelper.signJwtSession(token,jwtSessionAccount);

        return responseMessageHandle.code(200).data(token);
    }

    @GetMapping("t2")
    public ResponseMessage t2(){
        return responseMessageHandle.code(200).data(UUIDHelper.getSomeUuid(10)).customMsg(UUIDHelper.getUuid());
    }

    @GetMapping("t3")
    public ResponseMessage t3(){
        List<String> res=new ArrayList<>();
        String[] roles=new String[]{"role","route","permission"};
        String[] permission=new String[]{"list","add","edit","detail","delete"};
        for(String r:roles){
            for(String p:permission){
                String re=String.format("insert into sys_permission(uuid,title,sign,content,del_flag,created_by,created_time,updated_by,updated_time) VALUES('%s','%s','%s:%s',' ','0','admin',now(),'admin',now())",UUIDHelper.getUuid(),r,r,p);
                res.add(re);
            }
        }
        return responseMessageHandle.code(200).dataList(res);
    }
}
