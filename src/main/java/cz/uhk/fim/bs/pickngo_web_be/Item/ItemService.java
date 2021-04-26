package cz.uhk.fim.bs.pickngo_web_be.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public Optional<List<Item>> getItemsByBaguetteItem(Long baguetteItemId) {
        return itemRepository.findAllByBaguetteItem_Id(baguetteItemId);
    }

    public Optional<Item> getItem(Long itemId) {
        return itemRepository.findById(itemId);
    }
}
