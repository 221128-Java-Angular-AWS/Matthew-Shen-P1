package com.revature.service;
import com.revature.exceptions.InvalidTicketInputException;
import com.revature.exceptions.UserNotAuthorized;
import com.revature.persistence.ExpenseDao;
import com.revature.pojos.Expense;
import java.util.*;
public class ExpenseService {
    private ExpenseDao dao;


    public ExpenseService(ExpenseDao dao) {
        this.dao = dao;
    }

    public void createNewExpense(Expense expense) throws InvalidTicketInputException {
        dao.create(expense);
    }

    public Expense getExpense(Integer ExpenseId) {
        return dao.read(ExpenseId);
    }

    public Expense getExpense(Expense expense) {
        return dao.read(expense.getExpenseId());
    }

    public void updateExpense(Expense expense) {
        dao.update(expense);
    }

    public void consider(Integer expenseId, Integer managerId, String status) throws UserNotAuthorized{
        dao.consider(expenseId, managerId, status);
    }

    public ArrayList<Expense> getPending(){
        return dao.getPending();
    }

    public ArrayList<Expense> getHistory(Integer userId){
        return dao.getHistory(userId);
    }

    public void deleteExpense(Integer ExpenseId) {
        dao.delete(ExpenseId);
    }

    public void deleteExpense(Expense expense) {
        dao.delete(expense.getExpenseId());
    }

    public ArrayList<Expense> getAllExpenseForUser(Integer userId) {
        return dao.getAll(userId);
    }

    public ArrayList<Expense> getAllExpenseForUser(Expense expense) {
        return dao.getAll(expense.getUserId());
    }
}
