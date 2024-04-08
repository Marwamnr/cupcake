package app.controllers;

import app.entities.Topping;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ToppingMapper;
import io.javalin.http.Context;
import io.javalin.Javalin;

import java.util.List;

public class ToppingController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/toppingList", ctx -> showToppingList(ctx, connectionPool));
    }

    public static void showToppingList(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Topping> toppingList = ToppingMapper.getToppings(connectionPool);

            ctx.attribute("toppingList", toppingList);

            ctx.render("cupcake.html");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error retrieving topping list: " + e.getMessage());
        }
    }
}