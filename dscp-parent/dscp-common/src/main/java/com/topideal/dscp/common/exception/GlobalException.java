package com.topideal.dscp.common.exception;

/**
 * 全局异常
 *
 * @author anthony.guo
 */
public class GlobalException extends RuntimeException {

    private static final long serialVersionUID = 4526502524535634660L;

    private int code;

    public GlobalException() {
        super();
    }

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }
}
