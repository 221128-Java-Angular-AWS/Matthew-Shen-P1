package com.revature.exceptions;

public class EmailNotUniqueException extends Exception{
    public EmailNotUniqueException(String msg) {
        super(msg);
    }
}
