package uk.ac.brighton.uni.ch629.catshop.database.model.data.services.implementations;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.brighton.uni.ch629.catshop.database.model.Order;
import uk.ac.brighton.uni.ch629.catshop.database.model.data.repositories.OrderRepository;
import uk.ac.brighton.uni.ch629.catshop.database.model.data.services.interfaces.OrderService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order delete(int id) {
        Order deletedOrder = orderRepository.findOne(id);
        if (deletedOrder == null) return null; //Throw Exception
        orderRepository.delete(deletedOrder);
        return deletedOrder;
    }

    @Override
    @Transactional
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order update(Order order) {
        Order updatedOrder = orderRepository.findOne(order.getOrderID());
        if (updatedOrder == null) return null; //Throw Exception
        updatedOrder.setOrderProducts(order.getOrderProducts());
        return updatedOrder;
    }

    @Override
    @Transactional
    public Order findByID(int id) {
        return orderRepository.findOrderByOrderID(id);
    }
}
