package com.surpass.vision.exception;

public enum ExceptionEnum {

    GIRL_FRIEND_NOT_FOUND(100000, "girl friend not found");

    private int code;

    private String message;

    ExceptionEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
