package com.shtf.edu.utils.encryption;


import com.shtf.edu.constant.SystemConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.message.AuthException;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author chenlingyu
 */
public class JWTHelper {

    /**
     * JWT签发
     * 使用登录账户uuid
     * 登录用户分为两种
     * <p>
     * - 0 普通登录用户
     * - 1 PSM
     *
     * @param account_uuid
     * @param password     加盐后的
     * @param singType
     * @return
     */
    public static String signJwt(String account_uuid, int singType) {
        long nowMillis = System.currentTimeMillis();
        long expireMillis = singType == 0 ? SystemConstants.ShiroConstants.JWT_EXPIRES_USER : SystemConstants.ShiroConstants.JWT_EXPIRES_PSM;
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(DatatypeConverter.parseBase64Binary(SystemConstants.ShiroConstants.JWT_API_KEY_SECRET), signatureAlgorithm.getJcaName());
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim(SystemConstants.ShiroConstants.JWT_CLAIM_ACCOUNT_UUID, account_uuid)
                .claim(SystemConstants.ShiroConstants.JWT_CLAIM_ACCOUNT_SING_TYPE, singType)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + expireMillis))
                .setNotBefore(new Date(nowMillis))
                .signWith(signatureAlgorithm, signingKey)
                .compact();
    }

    /**
     * @param token
     * @return
     */
    public static Claims getClaimsFromJwt(String token) throws AuthException {
        Claims claims = (Claims) Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SystemConstants.ShiroConstants.JWT_API_KEY_SECRET))
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public static boolean isExpiration(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    public static String getAccountUuId(Claims claims) {
        return (String) claims.get(SystemConstants.ShiroConstants.JWT_CLAIM_ACCOUNT_UUID);
    }

    public static String getSignType(Claims claims) {
        return (String) claims.get(SystemConstants.ShiroConstants.JWT_CLAIM_ACCOUNT_SING_TYPE);
    }

}
