package cz.uhk.fim.bs.pickngo_web_be.SpecialOffer;

import cz.uhk.fim.bs.pickngo_web_be.Item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/specialOffer")
public class SpecialOfferController {

    private final SpecialOfferService specialOfferService;

    @Autowired
    public SpecialOfferController(SpecialOfferService specialOfferService){
        this.specialOfferService=specialOfferService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<SpecialOffer> getSpecialOffers() {
        return specialOfferService.getSpecialOffers();
    }

    @RequestMapping(value = "/all/{active}", method = RequestMethod.GET)
    public Optional<List<SpecialOffer>> getSpecialOffersByActive(@PathVariable boolean active) {
        return specialOfferService.getSpecialOffersByActive(active);
    }


    @RequestMapping(value = "/detail/{specialOfferId}", method = RequestMethod.GET)
    public Optional<SpecialOffer> getSpecialOffer(
            @PathVariable("specialOfferId") Long specialOfferId) {
        return  specialOfferService.getSpecialOffer(specialOfferId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public SpecialOffer createNewSpecialOffer(@RequestBody SpecialOffer specialOffer){
        if(specialOffer.getPrice()==0.0){
            double priceCounted = 0.0;
            for(Item item : specialOffer.getItems()){
                priceCounted+=item.getPrice();
            }
            specialOffer.setPrice(priceCounted);
        }
        return specialOfferService.createNewSpecialOffer(specialOffer);
    }

    @RequestMapping(value = "/update/{specialOfferId}", method = RequestMethod.PUT)
    public void updateSpecialOffer(
            @PathVariable("specialOfferId") Long specialOfferId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) double price,
            @RequestParam(required = false) List<Item> items,
            @RequestParam(required = false) boolean active){
        specialOfferService.updateSpecialOffer(specialOfferId, name, price, items, active);
    }

    @RequestMapping(value = "/updateActivity/{specialOfferId}", method = RequestMethod.PUT)
    public void updateActivitySpecialOffer(
            @PathVariable("specialOfferId") Long specialOfferId,
            @RequestParam(required = false) boolean active){
        specialOfferService.updateActivitySpecialOffer(specialOfferId, active);
    }


}
