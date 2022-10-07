/**
 *
 */
package com.topideal.dscp.common.exception;

/**
 * Exception - 业务异常
 *
 * @author anthony.guo
 */
public class BizException extends GlobalException {

    private static final long serialVersionUID = 5624783885721023424L;

    /**
     * @param code
     * @param message
     */
    public BizException(int code, String message) {
        super(code, message);
    }

    /**
     * @param message
     */
    public BizException(String message) {
        super(message);
    }

}
