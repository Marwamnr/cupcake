package app.controllers;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CustomerMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class CustomerController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/customerList", ctx -> showCustomerList(ctx, connectionPool));
        app.post("/deleteOrder", ctx -> deleteOrder(ctx, connectionPool));
    }

    public static void showCustomerList(Context ctx, ConnectionPool connectionPool) {
        try {
            List<Order> customerList = CustomerMapper.getCustomer(connectionPool);

            ctx.attribute("customerList", customerList);

            ctx.render("customer.html");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error retrieving order list: " + e.getMessage());
        }
    }

    public static void deleteOrder(Context ctx, ConnectionPool connectionPool) {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));

        try {
            CustomerMapper customerMapper = new CustomerMapper();
            customerMapper.deleteOrderAndOrderLinesByOrderId(connectionPool, orderId);
            ctx.result("Order and associated order lines deleted successfully");
        } catch (RuntimeException e) {
            ctx.status(500).result("Error deleting order and order lines: " + e.getMessage());
        }
    }
}


