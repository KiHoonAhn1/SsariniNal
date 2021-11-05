package com.playssafy.playssafy.exception;

public class MemberNotFoundException extends RuntimeException {

    /**
     * 생성자
     *
     * @param msg the detail message
     * @param cause the root cause
     */
    public MemberNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 생성자
     *
     * @param msg the detail message
     */
    public MemberNotFoundException(String msg) {
        super(msg);
    }


}
