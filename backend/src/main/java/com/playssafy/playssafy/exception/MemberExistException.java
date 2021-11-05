package com.playssafy.playssafy.exception;

public class MemberExistException extends RuntimeException {

    /**
     * 생성자
     *
     * @param msg the detail message
     * @param cause the root cause
     */
    public MemberExistException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 생성자
     * 
     * @param msg the detail message
     */
    public MemberExistException(String msg) {
        super(msg);
    }


}
