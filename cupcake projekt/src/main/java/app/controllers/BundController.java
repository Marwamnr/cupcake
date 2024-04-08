package app.controllers;

import app.entities.Bund;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.BundMapper;
import io.javalin.http.Context;
import io.javalin.Javalin;

import java.util.List;
public class BundController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/bundList", ctx -> showBundList(ctx, connectionPool));
    }

    public static void showBundList(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Bund> bundList = BundMapper.getBunds(connectionPool);

            System.out.println(bundList);

            ctx.attribute("bundList", bundList);

            ctx.render("cupcake.html");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error retrieving bund list: " + e.getMessage());
        }
    }
}