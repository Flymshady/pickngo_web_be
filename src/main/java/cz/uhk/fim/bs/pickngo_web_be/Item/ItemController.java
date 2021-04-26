package cz.uhk.fim.bs.pickngo_web_be.Item;

import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value = "/detail/{itemId}", method = RequestMethod.GET)
    public Optional<Item> getItem(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }
}
