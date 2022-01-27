package com.shtf.edu.constant;

/**
 * @author chenlingyu
 */
public class RedisConstants {

    public RedisConstants() {

    }

    public static final long BASE_EXPIRE = 7 * 24 * 60 * 60 * 1000;

    public static class Key {
        public static final String SPACE_JWT_SESSION = "JWT_SESSION:";


        public Key() {
        }

        public static class JwtSession {

            public static final String JWT_SESSION = SPACE_JWT_SESSION + "JWT_SESSION_";

            public static final long JWT_SESSION_EXPIRE = SystemConstants.ShiroConstants.JWT_EXPIRES_USER;

            public static final String JWT_SESSION_ACCOUNT = SPACE_JWT_SESSION + "JWT_SESSION_ACCOUNT_";

            public static final long JWT_SESSION_ACCOUNT_EXPIRE = SystemConstants.ShiroConstants.JWT_EXPIRES_USER;

            public JwtSession() {

            }

        }

    }
}
