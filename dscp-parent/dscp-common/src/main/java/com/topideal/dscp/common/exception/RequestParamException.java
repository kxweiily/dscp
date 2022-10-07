/**
 *
 */
package com.topideal.dscp.common.exception;

/**
 * 请求参数不完整 异常
 *
 * @author lizhenni
 */
public class RequestParamException extends GlobalException {

    private static final long serialVersionUID = 4922839120277674799L;

    /**
     * @param code
     * @param message
     */
    public RequestParamException(int code, String message) {
        super(code, message);
    }

}
