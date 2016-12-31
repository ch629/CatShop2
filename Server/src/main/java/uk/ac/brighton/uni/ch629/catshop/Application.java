package uk.ac.brighton.uni.ch629.catshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.ac.brighton.uni.ch629.catshop.connections.ServerRunnable;
import uk.ac.brighton.uni.ch629.catshop.data.Order;
import uk.ac.brighton.uni.ch629.catshop.data.Product;
import uk.ac.brighton.uni.ch629.catshop.data.services.interfaces.OrderService;
import uk.ac.brighton.uni.ch629.catshop.data.services.interfaces.ProductService;

@SpringBootApplication
public class Application {
    private static final int SERVER_SUBSCRIPTION_PORT = 8080;
    private static ProductService productService;
    private static OrderService orderService;
    private static Thread serverThread; //TODO: Not sure if I should keep this like this so I can check if it's still running, or I may need it to close the ServerSocket when closing the server

    @Autowired
    public Application(ProductService productService, OrderService orderService) {
        Application.productService = productService;
        Application.orderService = orderService;
    }

    private static void createDummyData() {
        System.out.println("CREATING DUMMY DATA");
        productService.create(new Product("40 inch LED HD TV", 269.00f, 90, "pic0001.jpg"));
        productService.create(new Product("DAB Radio", 29.99f, 20, "pic0002.jpg"));
        productService.create(new Product("Toaster", 19.99f, 33, "pic0003.jpg"));
        productService.create(new Product("Watch", 29.99f, 10, "pic0004.jpg"));
        productService.create(new Product("Digital Camera", 89.99f, 17, "pic0005.jpg"));
        productService.create(new Product("MP3 Player", 7.99f, 15, "pic0006.jpg"));
        productService.create(new Product("32GB USB2 Drive", 6.99f, 1, "pic0007.jpg"));
        System.out.println("DONE CREATING DUMMY DATA");

        System.out.println("CREATING TEST ORDER");
        Order testOrder = new Order();
        testOrder.addProduct(productService.findByProductNumber(1), 5);
        testOrder.addProduct(productService.findByProductNumber(2), 2);

        orderService.create(testOrder);
        Product newProduct = productService.findByProductNumber(3);
        orderService.addProduct(1, newProduct, 7);
        System.out.println("DONE CREATING TEST ORDER");
    }
    public static void main(String[] args) {
        serverThread = new Thread(new ServerRunnable(SERVER_SUBSCRIPTION_PORT));
        serverThread.start();
        SpringApplication.run(Application.class, args);
        createDummyData();
        System.out.println(productService.findByProductNumber(1).getDescription());
    }
}

//TODO: Could have a page on the website to get the port for subscriptions, to allow the server's port to change without changing client settings