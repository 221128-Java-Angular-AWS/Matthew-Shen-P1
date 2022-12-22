package com.revature.exceptions;

public class TicketAlreadyProcessedException extends Exception{
    public TicketAlreadyProcessedException(String msg) {
        super(msg);
    }
}
