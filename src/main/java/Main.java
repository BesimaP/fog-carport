import app.configuration.ThymeleafConfig;
import app.controllers.OrderController;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import app.services.OrderService;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "carport";
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {
        // Create the objects once (mappers -> service -> controller)
        UserMapper userMapper = new UserMapper(connectionPool);
        OrderMapper orderMapper = new OrderMapper(connectionPool);
        OrderService orderService = new OrderService(connectionPool, userMapper, orderMapper);
        OrderController orderController = new OrderController(orderService);

        Javalin.create(config -> {

            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));

            config.routes.get("/", ctx -> orderController.showOrderForm(ctx));
            config.routes.post("/order", ctx -> orderController.createOrder(ctx));



        }).start(7070);

    }

}
