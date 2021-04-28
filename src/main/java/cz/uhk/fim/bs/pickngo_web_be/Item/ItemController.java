package cz.uhk.fim.bs.pickngo_web_be.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @RequestMapping(value = "/{baguetteItemId}/all", method = RequestMethod.GET)
    public Optional<List<Item>> getItemsByBaguetteItem(@PathVariable Long baguetteItemId) {
        return itemService.getItemsByBaguetteItem(baguetteItemId);
    }
    @RequestMapping(value = "/{specialOfferId}/all", method = RequestMethod.GET)
    public Optional<List<Item>> getItemsBySpecialOffer(@PathVariable Long specialOfferId) {
        return itemService.getItemsBySpecialOffer(specialOfferId);
    }

    @RequestMapping(value = "/create/{specialOfferId}", method = RequestMethod.POST)
    public void addToSpecialOffer(@PathVariable("specialOfferId") Long specialOfferId, @RequestBody Item item){
        itemService.addToSpecialOffer(specialOfferId, item);
    }
    @RequestMapping(value = "/update/{specialOfferId}/{itemId}", method = RequestMethod.PUT)
    public void updateItem(@PathVariable("itemId") Long itemId,
                           @PathVariable("specialOfferId") Long specialOfferId,
                           @RequestParam(required = false) int amount){

        itemService.updateItem(specialOfferId, itemId, amount);
    }

    @RequestMapping(value = "/remove/{specialOfferId}/{itemId}", method = RequestMethod.DELETE)
    public void removeItem(@PathVariable("itemId") Long itemId, @PathVariable("specialOfferId") Long specialOfferId){
        itemService.removeItem(specialOfferId, itemId);
    }


    @RequestMapping(value = "/detail/{itemId}", method = RequestMethod.GET)
    public Optional<Item> getItem(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }
}
