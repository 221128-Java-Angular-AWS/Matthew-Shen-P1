package com.revature.persistence;

import com.revature.exceptions.PasswordIncorrectException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.EmailNotUniqueException;
import com.revature.pojos.User;

import java.sql.*;
import java.util.ArrayList;



public class UserDao {
    private Connection connection;

    public UserDao() {
        this.connection = ConnectionManager.getConnection();
    }

    public void create(User user) throws EmailNotUniqueException{
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                throw new EmailNotUniqueException("Email is not unique");
            }

            String sql2 = "INSERT INTO users (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            pstmt2.setString(1, user.getFirstName());
            pstmt2.setString(2, user.getLastName());
            pstmt2.setString(3, user.getUsername());
            pstmt2.setString(4, user.getPassword());
            pstmt2.executeUpdate();
            ResultSet rs2 = pstmt2.getGeneratedKeys();
            if(rs2.next()) {
                user.setUserId(rs2.getInt("user_id"));
                System.out.println("DEBUG - auto generated key: " + user.getUserId());
            }        
            

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public User authenticate(String username, String password) throws UserNotFoundException, PasswordIncorrectException {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()) {
                throw new UserNotFoundException("This username was not found");
            }

            User user = new User(rs.getInt("user_id"), rs.getString("first_name"),
                    rs.getString("last_name"), rs.getString("username"), rs.getString("password"));

            if(user.getPassword().equals(password)) {
                return user;
            }

            throw new PasswordIncorrectException("That password is not correct");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<User> getAllUsers() {
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<User> results = new ArrayList<>();
            while(rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("username"), rs.getString("password"));
                results.add(user);
            }

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUser(Integer userId){
        User user = new User();
        try {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getString("first_name"),
                        rs.getString("last_name"), rs.getString("username"), rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void update(User user) {
        try {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, user.getUserId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(Integer userId) {

        try {
            String sql = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);

            pstmt.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}