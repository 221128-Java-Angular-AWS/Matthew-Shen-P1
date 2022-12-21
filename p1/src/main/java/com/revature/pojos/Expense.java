package com.revature.pojos;

import java.util.Objects;

public class Expense {
    private Integer expenseId;
    private String title;
    private String description;
    private String status;
    private Integer userId;
    private Double amount;

    public Expense() {
    }

    public Expense(Integer expenseId, String title, String description, Double amount, String status, Integer userId) {
        this.expenseId = expenseId;
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.userId = userId;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount(){
        return amount;
    }

    public void setAmount(Double amount){
        this.amount = amount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(expenseId, expense.expenseId) && Objects.equals(title, expense.title) && Objects.equals(description, expense.description) && Objects.equals(status, expense.status) && Objects.equals(userId, expense.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, title, description, status, userId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + expenseId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", userId=" + userId +
                '}';
    }
}