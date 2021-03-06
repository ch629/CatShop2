package uk.ac.brighton.uni.ch629.catshop.data.services.interfaces;


import uk.ac.brighton.uni.ch629.catshop.data.Order;
import uk.ac.brighton.uni.ch629.catshop.data.OrderProduct;
import uk.ac.brighton.uni.ch629.catshop.data.Product;

import java.util.List;

public interface OrderService {
    Order create(Order order);

    Order delete(int id);

    List<Order> findAll();

    Order update(Order order);

    Order findByID(int id);

    Order addProduct(Order order, Product product, int quantity);

    Order addProduct(OrderProduct product);

    Order addProduct(int orderID, Product product, int quantity);

    Order addProduct(int orderID, int productID, int quantity);
}
