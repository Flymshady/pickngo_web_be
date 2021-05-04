package cz.uhk.fim.bs.pickngo_web_be.Item;

import cz.uhk.fim.bs.pickngo_web_be.SpecialOffer.SpecialOffer;
import cz.uhk.fim.bs.pickngo_web_be.SpecialOffer.SpecialOfferController;
import cz.uhk.fim.bs.pickngo_web_be.SpecialOffer.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final SpecialOfferRepository specialOfferRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, SpecialOfferRepository specialOfferRepository) {
        this.itemRepository = itemRepository;
        this.specialOfferRepository = specialOfferRepository;
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

    public Optional<List<Item>> getItemsBySpecialOffer(Long specialOfferId) {
        return itemRepository.findAllBySpecialOffer_Id(specialOfferId);
    }

    @Transactional
    public Item addToSpecialOffer(Long specialOfferId, Item item) {
        Optional<SpecialOffer> specialOfferOptional = specialOfferRepository.findById(specialOfferId);
        if (!specialOfferOptional.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "chyba, speciální nabídka nenalezena");
        }
        Item itemNew = new Item();
        itemNew.setAmount(item.getAmount());
        itemNew.setIngredient(item.getIngredient());
        itemNew.setName(item.getName());
        itemNew.setPrice(item.getPrice());
        itemNew.setSpecialOffer(specialOfferOptional.get());
        itemRepository.save(itemNew);
        if(specialOfferOptional.get().getItems()==null){
            List<Item> items = new ArrayList<>();
            items.add(item);
            specialOfferOptional.get().setItems(items);
        }else {
            specialOfferOptional.get().getItems().add(item);
        }
        specialOfferOptional.get().setPrice(specialOfferOptional.get().getPrice()+item.getPrice());
        specialOfferRepository.save(specialOfferOptional.get());
        return itemNew;
    }

    @Transactional
    public void updateItem(Long specialOfferId, Long itemId, int amount) {
        Optional<SpecialOffer> specialOfferOptional = specialOfferRepository.findById(specialOfferId);
        if (!specialOfferOptional.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "chyba, speciální nabídka nenalezena");
        }
        Optional<Item> item = itemRepository.findById(itemId);
        if( !item.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "chyba, položka nenalezena");
        }
        if(amount<=0) {
            removeItem(specialOfferId, itemId);
        }
        int amountPrev = item.get().getAmount();
        double itemPrice = item.get().getPrice();
        specialOfferOptional.get().setPrice(specialOfferOptional.get().getPrice()-amountPrev*itemPrice+itemPrice*amount);
        specialOfferRepository.save(specialOfferOptional.get());
        item.get().setAmount(amount);
        itemRepository.save(item.get());

    }

    @Transactional
    public void removeItem(Long specialOfferId, Long itemId) {
        Optional<SpecialOffer> specialOfferOptional = specialOfferRepository.findById(specialOfferId);
        if (!specialOfferOptional.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "chyba, speciální nabídka nenalezena");
        }
        Optional<Item> item = itemRepository.findById(itemId);
        if(!item.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "chyba, položka nenalezena");
        }
        double price = item.get().getPrice() * item.get().getAmount();
        int amount = item.get().getAmount();
        specialOfferOptional.get().getItems().remove(item.get());
        itemRepository.delete(item.get());
        if(specialOfferOptional.get().getItems().isEmpty()){
            specialOfferRepository.delete(specialOfferOptional.get());
        }else {
            specialOfferOptional.get().setPrice(specialOfferOptional.get().getPrice()-amount*price+price*amount);
            specialOfferRepository.save(specialOfferOptional.get());
        }
    }
}
