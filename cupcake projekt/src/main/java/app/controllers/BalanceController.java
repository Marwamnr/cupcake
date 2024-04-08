package app.controllers;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.BalanceMapper;
import io.javalin.http.Context;
import io.javalin.Javalin;


public class BalanceController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/addBalance", ctx -> addBalance(ctx, connectionPool));
        app.get("/addBalance", ctx -> ctx.render("balance.html"));
    }

    public static void addBalance(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        int balance = 0;

        try {
            balance = Integer.parseInt(ctx.formParam("balance"));

            BalanceMapper.addBalance(email, balance, connectionPool);

            ctx.result("Balance added successfully");
        } catch (NumberFormatException e) {
            ctx.result("Invalid balance value");
            ctx.status(400);
        } catch (DatabaseException e) {
            ctx.result("Failed to add balance: " + e.getMessage());
            ctx.status(500);
        }
    }
}