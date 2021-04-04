package cz.uhk.fim.bs.pickngo_web_be.Order;


import cz.uhk.fim.bs.pickngo_web_be.Ingredient.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping(path = "{userId}")
    public List<Optional<Order>> getOrdersByUser(@PathVariable("userId") Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @PutMapping(path = "{orderId}")
    public void updateUser(
            @PathVariable("orderId") Long orderId,
            @RequestParam(required = false) int state) {
        orderService.updateOrder(orderId, state);
    }
}
