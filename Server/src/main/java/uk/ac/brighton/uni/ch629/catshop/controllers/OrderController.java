package uk.ac.brighton.uni.ch629.catshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.ac.brighton.uni.ch629.catshop.OrderAddProduct;
import uk.ac.brighton.uni.ch629.catshop.database.model.Order;
import uk.ac.brighton.uni.ch629.catshop.database.model.OrderProduct;
import uk.ac.brighton.uni.ch629.catshop.database.model.data.services.interfaces.OrderService;

import java.util.List;

@RestController("order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/order/{id}")
    public Order getOrder(@PathVariable int id) {
        return orderService.findByID(id);
    }

    @GetMapping(value = {"/order/all", "/order", "/orders"})
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @PostMapping(value = {"/order"})
    public void addOrder(@RequestBody Order order) {
        orderService.create(order);
    }

    @PostMapping(value = "/order/product")
    public void addProductToOrder(@RequestBody OrderProduct orderProduct) {
        orderService.addProduct(orderProduct);
    }

    public void addProductToOrder(@RequestBody OrderAddProduct orderAddProduct) {

    }
}
