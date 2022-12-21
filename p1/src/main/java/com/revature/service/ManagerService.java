package com.revature.service;

import com.revature.exceptions.PasswordIncorrectException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.EmailNotUniqueException;
import com.revature.persistence.ManagerDao;
import com.revature.persistence.UserDao;
import com.revature.pojos.Manager;
import com.revature.pojos.User;

import java.util.*;

public class ManagerService{
    private ManagerDao dao;

    public ManagerService(ManagerDao dao) {
        this.dao = dao;
    }

    public void registerManager(Manager manager) throws EmailNotUniqueException{
        dao.create(manager);
    }


}