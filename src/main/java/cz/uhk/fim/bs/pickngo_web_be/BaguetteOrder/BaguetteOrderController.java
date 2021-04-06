package cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/baguetteOrder")
public class BaguetteOrderController {

    private final BaguetteOrderService baguetteOrderService;

    @Autowired
    public BaguetteOrderController(BaguetteOrderService baguetteOrderService) {
        this.baguetteOrderService = baguetteOrderService;
    }

    @GetMapping
    public List<BaguetteOrder> getBaguetteOrders() {
        return baguetteOrderService.getBaguetteOrders();
    }

    @GetMapping(path = "{customerId}")
    public List<Optional<BaguetteOrder>> getBaguetteOrdersByCustomer(@PathVariable("customerId") Long customerId) {
        return baguetteOrderService.getBaguetteOrdersByCustomer(customerId);
    }

    @PutMapping(path = "{baguetteOrderId}")
    public void updateCustomer(
            @PathVariable("baguetteOrderId") Long baguetteOrderId,
            @RequestParam(required = false) int state) {
        baguetteOrderService.updateBaguetteOrder(baguetteOrderId, state);
    }
}
