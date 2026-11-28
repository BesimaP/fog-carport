package app.controllers;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.services.OrderService;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class OrderController {

    private final OrderService orderService;

    // The service is given from outside (constructor injection), like in OrderService
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Shows the order form (GET /) with the lists of measurements for the dropdowns
    public void showOrderForm(Context ctx) {
        List<Integer> widths = new ArrayList<>();
        for (int width = 240; width <= 600; width += 10) {
            widths.add(width);
        }
        List<Integer> lengths = new ArrayList<>();
        for (int length = 240; length <= 780; length += 10) {
            lengths.add(length);
        }

        // Shed: width and depth (the limits must be agreed in the group)
        List<Integer> shedWidths = new ArrayList<>();
        for (int shedWidth = 150; shedWidth <= 600; shedWidth += 10) {
            shedWidths.add(shedWidth);
        }
        List<Integer> shedLengths = new ArrayList<>();
        for (int shedLength = 150; shedLength <= 300; shedLength += 10) {
            shedLengths.add(shedLength);
        }

        ctx.attribute("widths", widths);
        ctx.attribute("lengths", lengths);
        ctx.attribute("shedWidths", shedWidths);
        ctx.attribute("shedLengths", shedLengths);
        ctx.render("index.html");


    }

    // Receives the form (POST /order), saves the customer and the order, and shows the confirmation
    public void createOrder(Context ctx) {
        // Read the measurements from the form (formParam gives a String, so we parse to int)
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int height = Integer.parseInt(ctx.formParam("height"));

        // Shed: only read the fields if the checkbox is checked (otherwise they stay null)
        Integer shedWidth = null;
        Integer shedLength = null;
        if (ctx.formParam("hasShed") != null) {
            shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
            shedLength = Integer.parseInt(ctx.formParam("shedLength"));
        }

        // Read the customer details (text, so no parsing needed)
        String name = ctx.formParam("name");
        String address = ctx.formParam("address");
        String email = ctx.formParam("email");
        String email2 = ctx.formParam("email2");
        String phone = ctx.formParam("phone");
        String password = ctx.formParam("password");
        String comment = ctx.formParam("comment");

        // The two e-mails must be the same, otherwise show the form again with a message
        if (!email.equals(email2)) {
            ctx.attribute("errorMessage", "E-mailadresserne er ikke ens");
            ctx.attribute("errorField", "email");
            keepFormValues(ctx);
            showOrderForm(ctx);
            return;
        }
        User user = new User(name, email, phone, address, null);
        Order order = new Order(user, width, length, height, shedWidth, shedLength, comment);
        // Save the customer and the order in the database
        try {
            orderService.placeOrder(order,password);
            ctx.render("order-confirmation.html");
        } catch (DatabaseException e) {
            // Show the form again with the message, so the customer only has to fix the error
            ctx.attribute("errorMessage", e.getMessage());
            if (e.getMessage().equals("Forkert e-mail eller adgangskode")) {
                ctx.attribute("errorField", "password");
            }
            keepFormValues(ctx);
            showOrderForm(ctx);
        }

    }

    // Puts what the customer wrote back on the page, so the form is not empty after an error
    // (the password is not put back, the customer types it again)
    private void keepFormValues(Context ctx) {
        ctx.attribute("selectedWidth", Integer.parseInt(ctx.formParam("width")));
        ctx.attribute("selectedLength", Integer.parseInt(ctx.formParam("length")));
        ctx.attribute("selectedHeight", Integer.parseInt(ctx.formParam("height")));
        ctx.attribute("hasShed", ctx.formParam("hasShed") != null);
        ctx.attribute("selectedShedWidth", Integer.parseInt(ctx.formParam("shedWidth")));
        ctx.attribute("selectedShedLength", Integer.parseInt(ctx.formParam("shedLength")));
        ctx.attribute("name", ctx.formParam("name"));
        ctx.attribute("address", ctx.formParam("address"));
        ctx.attribute("email", ctx.formParam("email"));
        ctx.attribute("email2", ctx.formParam("email2"));
        ctx.attribute("phone", ctx.formParam("phone"));
        ctx.attribute("comment", ctx.formParam("comment"));
    }
}
