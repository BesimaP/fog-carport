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
        List<Integer> lengths = new ArrayList<>();
        for(int length = 240; length <=780; length +=10){
            lengths.add(length);
        }

        // Redskabsrum: bredde og dybde (grænserne skal aftales i gruppen)
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
}
