/**
 *
 */
package com.topideal.dscp.common.exception;

/**
 * 未登录异常
 *
 * @author lizhenni
 */
public class NotLoginException extends GlobalException {

    private static final long serialVersionUID = 4922839120277674799L;

    /**
     * @param code
     * @param message
     */
    public NotLoginException(int code, String message) {
        super(code, message);
    }

}
