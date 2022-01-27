package com.shtf.edu.constant;

/**
 * @author chenlingyu
 */
public class SystemConstants {
    public SystemConstants() {

    }

    public class ShiroConstants {

        public static final String REQUEST_HEADER_TOKEN = "ACCESS_TOKEN";

        public static final String REQUEST_HEADER_ORIGIN = "ACCESS_ORIGIN";

        public static final String SHIRO_PRINCIPAL_MAP_TOKEN = REQUEST_HEADER_TOKEN;

        public static final String SHIRO_PRINCIPAL_MAP_ORIGIN = REQUEST_HEADER_ORIGIN;

        public static final String SHIRO_PRINCIPAL_MAP_SESSION_ACCOUNT = "SESSION_ACCOUNT";

        public static final String JWT_API_KEY_SECRET = "4itaYxIsbgbJXxln69kNDDNyk3G0P7qw";

        public static final String JWT_CLAIM_ACCOUNT_UUID = "account_uuid";

        public static final String JWT_CLAIM_ACCOUNT_SING_TYPE = "account_sign_type";

        public static final long JWT_EXPIRES_USER = 30 * 60 * 1000;

        public static final long JWT_EXPIRES_PSM = 90 * 24 * 60 * 60 * 1000;

        public ShiroConstants() {

        }
    }


}
