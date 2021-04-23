package cz.uhk.fim.bs.pickngo_web_be.BaguetteItem;

import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrder;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrderRepository;
import jdk.nashorn.internal.ir.Optimistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BaguetteItemService {

    private final BaguetteOrderRepository baguetteOrderRepository;
    private final BaguetteItemRepository baguetteItemRepository;

    @Autowired
    public BaguetteItemService(BaguetteOrderRepository baguetteOrderRepository, BaguetteItemRepository baguetteItemRepository) {
        this.baguetteOrderRepository = baguetteOrderRepository;
        this.baguetteItemRepository = baguetteItemRepository;
    }

    public List<BaguetteItem> getBaguetteItems() {
        return baguetteItemRepository.findAll();
    }

    public List<BaguetteItem> getBaguetteItemsByBaguetteOrder(Long baguetteOrderId) {
        Optional<BaguetteOrder> baguetteOrderOptional = baguetteOrderRepository.findById(baguetteOrderId);
        if(!baguetteOrderOptional.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "baguette order with id "+ baguetteOrderId + " doesnt exist");
        }
        return baguetteItemRepository.findAllByBaguetteOrder_Id(baguetteOrderId);
    }

    public Optional<BaguetteItem> getBaguetteItem(Long baguetteItemId) {
        Optional<BaguetteItem> baguetteItemOptional = baguetteItemRepository.findById(baguetteItemId);
        if (!baguetteItemOptional.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "baguette item with id "+ baguetteItemId + " doesnt exist");
        }
        return baguetteItemOptional;
    }
}
