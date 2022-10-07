/**
 *
 */
package com.topideal.dscp.common.exception;

/**
 * 认证异常 
 *
 * @author anthony.guo
 */
public class AuthenticateException extends GlobalException {

    private static final long serialVersionUID = 4922839120277674799L;

    /**
     * @param code
     * @param message
     */
    public AuthenticateException(int code, String message) {
        super(code, message);
    }

    /**
     * @param message
     */
    public AuthenticateException(String message) {
        super(message);
    }

}
