package cz.uhk.fim.bs.pickngo_web_be.Order;


import cz.uhk.fim.bs.pickngo_web_be.Employee.Employee;
import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientType;
import cz.uhk.fim.bs.pickngo_web_be.Item.ItemRepository;
import cz.uhk.fim.bs.pickngo_web_be.User.User;
import cz.uhk.fim.bs.pickngo_web_be.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public List<Optional<Order>> getOrdersByUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()){
            throw new IllegalStateException("user with id "+ userId + " doesnt exist");
        }
        return orderRepository.findAllByUser(userOptional);
    }


    @Transactional
    public void updateOrder(Long  orderId, int state){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new IllegalStateException("order with id "+ orderId+ "doesnt exist"));
        if (!Objects.equals(order.getState(), state)){
            order.setState(state);
        }
    }
}
