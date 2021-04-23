package cz.uhk.fim.bs.pickngo_web_be.BaguetteItem;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/baguetteItem")
public class BaguetteItemController {

    private final BaguetteItemService baguetteItemService;

    @Autowired
    public BaguetteItemController(BaguetteItemService baguetteItemService) {
        this.baguetteItemService = baguetteItemService;
    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<BaguetteItem> getBaguetteItems() {
        return baguetteItemService.getBaguetteItems();
    }


    @RequestMapping(value = "/allByBaguetteOrder/{baguetteOrderId}", method = RequestMethod.GET)
    public List<BaguetteItem> getBaguetteItemsByBaguetteOrder(@PathVariable("baguetteOrderId") Long baguetteOrderId) {
        return baguetteItemService.getBaguetteItemsByBaguetteOrder(baguetteOrderId);
    }

    @RequestMapping(value = "/detail/{baguetteItemId}", method = RequestMethod.GET)
    public Optional<BaguetteItem> getBaguetteItem(
            @PathVariable("baguetteItemId") Long baguetteItemId) {
        return  baguetteItemService.getBaguetteItem(baguetteItemId);
    }
}
