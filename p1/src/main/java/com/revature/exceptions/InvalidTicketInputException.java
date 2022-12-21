package com.revature.exceptions;

public class InvalidTicketInputException extends Exception {
    public InvalidTicketInputException(String msg) {
        super(msg);
    }
}