package app.controllers;

import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class OrderController {

    public static void showOrderForm(Context ctx) {
        List<Integer> widths = new ArrayList<>();
        for (int width = 240; width <= 600; width += 10) {
            widths.add(width);
        }
        ctx.attribute("widths", widths);
        ctx.render("index.html");
    }
}
