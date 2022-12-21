package com.revature.persistence;

import com.revature.pojos.Expense;
import com.revature.exceptions.UserNotAuthorized;
import com.revature.exceptions.InvalidTicketInputException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.omg.CORBA.DynAnyPackage.Invalid;

public class ExpenseDao {
    private Connection connection;

    public ExpenseDao() {
        this.connection = ConnectionManager.getConnection();
    }

    public void create(Expense expense) throws InvalidTicketInputException{
        try {
            if(expense.getDescription().equals("") || expense.getAmount() == 0){
                throw new InvalidTicketInputException("ticket needs description and amount");
            }
            
            String sql = "INSERT INTO expenses (title, description, amount, status, user_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, expense.getTitle());
            pstmt.setString(2, expense.getDescription());
            pstmt.setDouble(3, expense.getAmount());
            pstmt.setString(4, "Pending");
            pstmt.setInt(5, expense.getUserId());

            pstmt.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    // reads a single expense given the expense id number
    public Expense read(Integer id) {
        Expense expense = new Expense();
        try {
            String sql = "SELECT * FROM expenses WHERE expense_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                expense.setExpenseId(rs.getInt("expense_id"));
                expense.setTitle(rs.getString("title"));
                expense.setDescription(rs.getString("description"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setStatus(rs.getString("status"));
                expense.setUserId(rs.getInt("user_id"));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return expense;
    }

    // returns an array list of all the tickets in the system
    public ArrayList<Expense> getAll(){
        try {
            String sql = "SELECT * FROM expenses";
            
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Expense> results = new ArrayList<>();
            while(rs.next()) {
                Expense expense = new Expense(rs.getInt("expense_id"), rs.getString("title"),
                        rs.getString("description"), rs.getDouble("amount"), rs.getString("status"), rs.getInt("user_id"));
                results.add(expense);
            }

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // returns an array list of all the tickets from a specific user
    public ArrayList<Expense> getAll(Integer user_id){
        try {
            String sql = "SELECT * FROM expenses WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Expense> results = new ArrayList<>();
            while(rs.next()) {
                Expense expense = new Expense(rs.getInt("expense_id"), rs.getString("title"),
                        rs.getString("description"), rs.getDouble("amount"), rs.getString("status"), rs.getInt("user_id"));
                results.add(expense);
            }

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // returns an array list of all pending tickets
    public ArrayList<Expense> getPending(){
        try {
            String sql = "SELECT * FROM expenses WHERE status = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "Pending");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Expense> results = new ArrayList<>();
            while(rs.next()) {
                Expense expense = new Expense(rs.getInt("expense_id"), rs.getString("title"),
                        rs.getString("description"), rs.getDouble("amount"), rs.getString("status"), rs.getInt("user_id"));
                results.add(expense);
            }

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
 
    // returns an array list of all pending tickets
    public ArrayList<Expense> getApproved(){
        try {
            String sql = "SELECT * FROM expenses WHERE status = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "Approved");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Expense> results = new ArrayList<>();
            while(rs.next()) {
                Expense expense = new Expense(rs.getInt("expense_id"), rs.getString("title"),
                        rs.getString("description"), rs.getDouble("amount"), rs.getString("status"), rs.getInt("user_id"));
                results.add(expense);
            }

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // returns an array list of all pending tickets
    public ArrayList<Expense> getDenied(){
        try {
            String sql = "SELECT * FROM expenses WHERE status = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "Denied");
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Expense> results = new ArrayList<>();
            while(rs.next()) {
                Expense expense = new Expense(rs.getInt("expense_id"), rs.getString("title"),
                        rs.getString("description"), rs.getDouble("amount"), rs.getString("status"), rs.getInt("user_id"));
                results.add(expense);
            }

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }   

    // approve or deny a ticket
    public void consider(Integer expenseId, Integer managerId, String status) throws UserNotAuthorized{
        try {
            String sql = "SELECT * FROM managers WHERE manager_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, managerId);
            pstmt.execute();
            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()) {
                throw new UserNotAuthorized("UserNotAuthorized");
            }

            String sql2 = "UPDATE expenses SET status = ? WHERE expense_id = ?";
            PreparedStatement pstmt2 = connection.prepareStatement(sql2);
            pstmt2.setString(1, status);
            pstmt2.setInt(2, expenseId);
            pstmt2.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    // get the complete history of a user's submissions
    public ArrayList<Expense> getHistory(Integer user_id){
        try {
            String sql = "SELECT * FROM expenses WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Expense> results = new ArrayList<>();
            while(rs.next()) {
                Expense expense = new Expense(rs.getInt("expense_id"), rs.getString("title"),
                        rs.getString("description"), rs.getDouble("amount"), rs.getString("status"), rs.getInt("user_id"));
                results.add(expense);
            }

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }  

    public void update(Expense expense) {
        try {
            String sql = "UPDATE expenses SET title = ?, description = ?, amount = ?, status = ?, user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, expense.getTitle());
            pstmt.setString(2, expense.getDescription());
            pstmt.setDouble(3, expense.getAmount());
            pstmt.setString(4, expense.getStatus());
            pstmt.setInt(5, expense.getUserId());

            pstmt.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        try {
            String sql = "DELETE FROM expenses WHERE expense_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();


        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}