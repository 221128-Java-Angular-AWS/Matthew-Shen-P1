package com.revature.persistence;

import com.revature.exceptions.PasswordIncorrectException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.EmailNotUniqueException;
import com.revature.pojos.Manager;
import com.revature.pojos.User;

import java.sql.*;
import java.util.ArrayList;

public class ManagerDao extends UserDao{
    private Connection connection;

    public ManagerDao() {
        this.connection = ConnectionManager.getConnection();
    }
    public void create(Manager manager) throws EmailNotUniqueException{
        try {
            String sql = "SELECT * FROM managers WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, manager.getUsername());

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                throw new EmailNotUniqueException("Email is not unique");
            }

            String sql2 = "INSERT INTO managers (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            pstmt2.setString(1, manager.getFirstName());
            pstmt2.setString(2, manager.getLastName());
            pstmt2.setString(3, manager.getUsername());
            pstmt2.setString(4, manager.getPassword());
            pstmt2.executeUpdate();
            ResultSet rs2 = pstmt2.getGeneratedKeys();
            if(rs2.next()) {
                manager.setManagerId(rs2.getInt("manager_id"));
                System.out.println("DEBUG - auto generated key: " + manager.getManagerId());
            }        
            

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
