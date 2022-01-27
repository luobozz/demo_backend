package com.shtf.edu.bean.exception;

import javax.security.auth.message.AuthException;

/**
 * ForcedOfflineAuthException class
 *
 * @author chenlingyu
 * @date 2021/5/4 18:46
 */
public class ForcedOfflineAuthException extends AuthException {
    public ForcedOfflineAuthException() {
    }

    public ForcedOfflineAuthException(String msg) {
        super(msg);
    }
}
