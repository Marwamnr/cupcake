package app.controllers;

import app.entities.Orderline;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.http.Context;
import io.javalin.Javalin;
import java.util.List;
public class OrderController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/orderList", ctx -> showOrderList(ctx, connectionPool));
    }

    public static void showOrderList(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Orderline> orderList = OrderMapper.getOrders(connectionPool);

            ctx.attribute("orderList", orderList);

            ctx.render("order.html");
        } catch (Exception e) {
            ctx.status(500).result("Fejl ved hentning af ordreliste: " + e.getMessage());
        }
    }
}
