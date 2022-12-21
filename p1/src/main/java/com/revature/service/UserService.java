package com.revature.service;

import com.revature.exceptions.PasswordIncorrectException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.EmailNotUniqueException;
import com.revature.persistence.UserDao;
import com.revature.pojos.User;

import java.util.*;

public class UserService {
    private UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public void registerNewUser(User user) throws EmailNotUniqueException{
        dao.create(user);
    }

    public ArrayList<User> getAllUsers() {
        return dao.getAllUsers();
    }

    public void updateUser(User user) {
        dao.update(user);
    }

    public void deleteUser(Integer userId) {
        dao.delete(userId);
    }

    public void deleteUser(User user) {
        dao.delete(user.getUserId());
    }

    public User authenticateUser(User user) throws UserNotFoundException, PasswordIncorrectException {
        return dao.authenticate(user.getUsername(), user.getPassword());
    }

    public User authenticateUser(String username, String password) throws UserNotFoundException, PasswordIncorrectException {
        return dao.authenticate(username, password);
    }

    public User getUser(Integer userId) {
        return dao.getUser(userId);
    }

}