package cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/baguetteOrder")
public class BaguetteOrderController {

    private final BaguetteOrderService baguetteOrderService;

    @Autowired
    public BaguetteOrderController(BaguetteOrderService baguetteOrderService) {
        this.baguetteOrderService = baguetteOrderService;
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<BaguetteOrder> getBaguetteOrders() {
        return baguetteOrderService.getBaguetteOrders();
    }


    @RequestMapping(value = "/allByCustomer/{customerId}", method = RequestMethod.GET)
    public List<Optional<BaguetteOrder>> getBaguetteOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        return baguetteOrderService.getBaguetteOrdersByCustomer(customerId);
    }

    @RequestMapping(value = "/update/{baguetteOrderId}", method = RequestMethod.PUT)
    public void updateCustomer(
            @PathVariable("baguetteOrderId") Long baguetteOrderId,
            @RequestParam(required = false) int state) {
        baguetteOrderService.updateBaguetteOrder(baguetteOrderId, state);
    }

    @RequestMapping(value = "/detail/{baguetteOrderId}", method = RequestMethod.GET)
    public BaguetteOrder getBaguetteOrder(@PathVariable("baguetteOrderId") Long baguetteOrderId){

        return baguetteOrderService.getBaguetteOrderById(baguetteOrderId);
    }
}
