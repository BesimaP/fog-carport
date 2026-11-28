package app.controllers;

import app.dto.OrderDto;
import app.entities.Status;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.services.OrderService;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminController {
    private OrderService orderService;

    public AdminController(ConnectionPool connectionPool){
        this.orderService = new OrderService(connectionPool);
    }

    public void setRoutes(JavalinConfig app){
        app.routes.get("/admin/orders", ctx ->  showAdminOrders(ctx));
    }

    // Shows the admin order list. Optional filter: ?status=FORESPOERGSEL / TILBUD_SENDT / BETALT
    private void showAdminOrders(@NotNull Context ctx) {
        User user = ctx.sessionAttribute("currentUser");
        if (user == null || !user.isAdmin()){
            ctx.redirect("/login");
            return;
        }

        try {
            String statusParam = ctx.queryParam("status");
            Status status = null;
            if (statusParam != null){
                status = Status.valueOf(statusParam);
            }
            List<OrderDto> orders = orderService.getOrders(status);
            int newCount = orderService.countNewOrders();

            ctx.attribute("orders", orders);
            ctx.attribute("newCount", newCount);
            ctx.render("admin-orders");
        } catch (DatabaseException e){
            ctx.attribute("message", e.getMessage());
            ctx.render("admin-orders");
        }
    }
}
