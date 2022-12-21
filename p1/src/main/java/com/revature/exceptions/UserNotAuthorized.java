package com.revature.exceptions;

public class UserNotAuthorized extends Exception{
    public UserNotAuthorized(String msg) {
        super(msg);
    }
}
