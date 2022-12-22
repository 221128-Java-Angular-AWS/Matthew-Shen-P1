package com.revature.service;

import com.revature.exceptions.PasswordIncorrectException;
import com.revature.exceptions.UserNotFoundException;

import java.util.ArrayList;

import com.revature.exceptions.EmailNotUniqueException;
import com.revature.exceptions.InvalidUserInputException;
import com.revature.persistence.ManagerDao;
import com.revature.pojos.Manager;

public class ManagerService{
    private ManagerDao dao;

    public ManagerService(ManagerDao dao) {
        this.dao = dao;
    }

    public void registerManager(Manager manager) throws EmailNotUniqueException, InvalidUserInputException{
        dao.create(manager);
    }

    public Manager authenticateUser(String username, String password) throws UserNotFoundException, PasswordIncorrectException {
        return dao.authenticate(username, password);
    }

    public ArrayList<Manager> getAllManagers() {
        return dao.getAllManagers();
    }

}