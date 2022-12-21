package com.revature.javalin;

import com.revature.exceptions.EmailNotUniqueException;
import com.revature.exceptions.InvalidTicketInputException;
import com.revature.exceptions.PasswordIncorrectException;
import com.revature.exceptions.UserNotAuthorized;
import com.revature.exceptions.UserNotFoundException;
import com.revature.persistence.ExpenseDao;
import com.revature.persistence.ManagerDao;
import com.revature.persistence.UserDao;
import com.revature.pojos.Expense;
import com.revature.pojos.Manager;
import com.revature.pojos.User;
import com.revature.service.ExpenseService;
import com.revature.service.UserService;
import com.revature.service.ManagerService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.*;

public class JavalinApp {
    private static Javalin app;
    private static UserService userService;
    private static ExpenseService expenseService;
    private static ManagerService managerService;

    private JavalinApp() {

    }

    public static Javalin getApp(int port) {
        if(app == null) {
            userService = new UserService(new UserDao());
            expenseService = new ExpenseService(new ExpenseDao());
            managerService = new ManagerService(new ManagerDao());
            init(port);
        }
        return app;
    }

    private static void init(int port) {
        app = Javalin.create()
                .start(port);

        app.get("/ping", JavalinApp::ping);
        app.post("/user", JavalinApp::postNewUser);
        app.get("/user", JavalinApp::getAllUsers);
        app.post("/user/auth", JavalinApp::authenticateUser);

        app.get("/expense", JavalinApp::getExpenseById);
        app.get("/expense/pending", JavalinApp::getPending);
        app.put("/expense/consider", JavalinApp::updateExpenseStatus);
        app.post("/expense", JavalinApp::postNewExpense);
        app.put("/expense", JavalinApp::updateExpense);
        app.delete("/expense", JavalinApp::deleteExpense);
        app.get("/expense/history", JavalinApp::getHistory);

        app.post("/manager", JavalinApp::postNewManager);
        app.get("/manager", JavalinApp::getAllManagers);

    }

    public static void ping(Context ctx) {
        ctx.result("ping!");
        ctx.status(200);

    }

    public static void postNewUser(Context ctx){
        User newUser = ctx.bodyAsClass(User.class);
        try {
            userService.registerNewUser(newUser);
        }
        catch(EmailNotUniqueException e){
            ctx.status(401);
            ctx.result("email not unique");
        }

        ctx.status(201);
    }

    public static void getAllUsers(Context ctx) {
        ArrayList<User> users = userService.getAllUsers();
        ctx.json(users);
        ctx.status(200);
    }

    public static void postNewManager(Context ctx){
        Manager newManager = ctx.bodyAsClass(Manager.class);
        try {
            managerService.registerManager(newManager);
        }
        catch(EmailNotUniqueException e){
            ctx.status(401);
            ctx.result("email not unique");
        }

        ctx.status(201);
    }

    public static void getAllManagers(Context ctx) {
        ArrayList<User> users = userService.getAllUsers();
        ctx.json(users);
        ctx.status(200);
    }

    public static void authenticateUser(Context ctx) {
        User auth = ctx.bodyAsClass(User.class);
        try {
            userService.authenticateUser(auth.getUsername(), auth.getPassword());
        } catch(UserNotFoundException e) {
            ctx.status(401);
            ctx.result("User not found.");
        } catch(PasswordIncorrectException e) {
            ctx.status(401);
            ctx.result("Password incorrect.");

        }
    }

    public static void getExpenseById(Context ctx) {
        int id = Integer.parseInt(ctx.queryParam("expense_id"));
        Expense expense = expenseService.getExpense(id);

        ctx.json(expense);
        ctx.status(200);
    }

    public static void getPending(Context ctx) {
        ArrayList<Expense> expense = expenseService.getPending();
        ctx.json(expense);
        ctx.status(200);
    }

    public static void postNewExpense(Context ctx) throws InvalidTicketInputException {
        Expense expense = ctx.bodyAsClass(Expense.class);
        try {
            expenseService.createNewExpense(expense);
        }
        catch(InvalidTicketInputException e){
            ctx.status(401);
            ctx.result("Description and amount must be submitted");
        }
        ctx.status(201);
    }

    public static void getHistory(Context ctx) {
        int id = Integer.parseInt(ctx.queryParam("user_id"));
        ArrayList <Expense> expense = expenseService.getHistory(id);

        ctx.json(expense);
        ctx.status(200);
    }

    public static void updateExpense(Context ctx) {
        Expense expense = ctx.bodyAsClass(Expense.class);
        expenseService.updateExpense(expense);

        ctx.status(201);
    }

    public static void updateExpenseStatus(Context ctx) throws UserNotAuthorized {
        int expenseId = Integer.parseInt(ctx.queryParam("expense_id"));
        int managerId = Integer.parseInt(ctx.queryParam("manager_id"));
        String status = (ctx.queryParam("status"));
        try {
            expenseService.consider(expenseId, managerId, status);
        }
        catch(UserNotAuthorized e){
            ctx.status(401);
            ctx.result("This user is not authorized");
        }
        ctx.status(201);
    }

    public static void deleteExpense(Context ctx) {
        int id = Integer.parseInt(ctx.queryParam("expense_id"));
        expenseService.deleteExpense(id);

        ctx.status(200);
    }


}