package cz.uhk.fim.bs.pickngo_web_be.SpecialOffer;

import cz.uhk.fim.bs.pickngo_web_be.Item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SpecialOfferService {

    private final SpecialOfferRepository specialOfferRepository;

    @Autowired
    public SpecialOfferService(SpecialOfferRepository specialOfferRepository){
        this.specialOfferRepository=specialOfferRepository;
    }

    public List<SpecialOffer> getSpecialOffers() {
        return specialOfferRepository.findAll();
    }

    public Optional<SpecialOffer> getSpecialOffer(Long specialOfferId) {
        return specialOfferRepository.findById(specialOfferId);
    }

    public SpecialOffer createNewSpecialOffer(SpecialOffer specialOffer) {
        specialOfferRepository.save(specialOffer);
        return specialOffer;
    }

    public void updateSpecialOffer(Long specialOfferId, String name, double price, List<Item> items, boolean active) {
        Optional<SpecialOffer> specialOffer = specialOfferRepository.findById(specialOfferId);
        if(!specialOffer.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Speciální nabídka nenalezena");
        }
        if (name !=null && name.length() > 0 && !Objects.equals(specialOffer.get().getName(), name)){
            specialOffer.get().setName(name);
        }
        if (price > 0.0 && !Objects.equals(specialOffer.get().getPrice(), price)){
            specialOffer.get().setPrice(price);
        }
        if (!items.isEmpty() && !Objects.equals(specialOffer.get().getItems(), items)){
            specialOffer.get().setItems(items);
        }
        if (active==true && !specialOffer.get().isActive()){
            specialOffer.get().setActive(true);
        }
        if (active==false && specialOffer.get().isActive()){
            specialOffer.get().setActive(false);
        }

        specialOfferRepository.save(specialOffer.get());
    }

    public void updateActivitySpecialOffer(Long specialOfferId, boolean active) {
        Optional<SpecialOffer> specialOffer = specialOfferRepository.findById(specialOfferId);
        if(!specialOffer.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Speciální nabídka nenalezena");
        }
        if (active==true && !specialOffer.get().isActive()){
            specialOffer.get().setActive(true);
        }
        if (active==false && specialOffer.get().isActive()){
            specialOffer.get().setActive(false);
        }

        specialOfferRepository.save(specialOffer.get());
    }

    public Optional<List<SpecialOffer>> getSpecialOffersByActive(boolean active) {
        return specialOfferRepository.findAllByActive(active);
    }
}
