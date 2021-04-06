package cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder;


import cz.uhk.fim.bs.pickngo_web_be.Item.ItemRepository;
import cz.uhk.fim.bs.pickngo_web_be.Customer.Customer;
import cz.uhk.fim.bs.pickngo_web_be.Customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BaguetteOrderService {

    private final BaguetteOrderRepository baguetteOrderRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BaguetteOrderService(BaguetteOrderRepository baguetteOrderRepository, CustomerRepository customerRepository) {
        this.baguetteOrderRepository = baguetteOrderRepository;
        this.customerRepository = customerRepository;
    }

    public List<BaguetteOrder> getBaguetteOrders() {
        return baguetteOrderRepository.findAll();
    }

    public List<Optional<BaguetteOrder>> getBaguetteOrdersByCustomer(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(!customerOptional.isPresent()){
            throw new IllegalStateException("customer with id "+ customerId + " doesnt exist");
        }
        return baguetteOrderRepository.findAllByCustomer(customerOptional);
    }


    @Transactional
    public void updateBaguetteOrder(Long  baguetteOrderId, int state){
        BaguetteOrder baguetteOrder = baguetteOrderRepository.findById(baguetteOrderId).orElseThrow(()-> new IllegalStateException("baguette order with id "+ baguetteOrderId+ "doesnt exist"));
        if (!Objects.equals(baguetteOrder.getState(), state)){
            baguetteOrder.setState(state);
        }
    }
}
