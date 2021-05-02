package cz.uhk.fim.bs.pickngo_web_be.Item;

import cz.uhk.fim.bs.pickngo_web_be.BaguetteItem.BaguetteItem;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteItem.BaguetteItemRepository;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrder;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrderRepository;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrderService;
import cz.uhk.fim.bs.pickngo_web_be.Customer.Customer;
import cz.uhk.fim.bs.pickngo_web_be.Customer.CustomerRepository;
import cz.uhk.fim.bs.pickngo_web_be.Ingredient.Ingredient;
import cz.uhk.fim.bs.pickngo_web_be.Ingredient.IngredientRepository;
import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientType;
import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientTypeRepository;
import cz.uhk.fim.bs.pickngo_web_be.SpecialOffer.SpecialOffer;
import cz.uhk.fim.bs.pickngo_web_be.SpecialOffer.SpecialOfferRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository underTest;
    @Autowired
    private BaguetteItemRepository baguetteItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BaguetteOrderRepository baguetteOrderRepository;
    @Autowired
    private SpecialOfferRepository specialOfferRepository;
    @Autowired
    private IngredientTypeRepository ingredientTypeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @AfterEach
    void tearDown(){
        customerRepository.deleteAll();
        ingredientTypeRepository.deleteAll();
        specialOfferRepository.deleteAll();
        baguetteItemRepository.deleteAll();
        underTest.deleteAll();
        ingredientRepository.deleteAll();
        baguetteOrderRepository.deleteAll();
    }

    @Test
    void findAllBySpecialOffer_Id() {
        SpecialOffer specialOffer = new SpecialOffer();
        specialOffer.setId(1L);
        specialOfferRepository.save(specialOffer);

        Ingredient ingredient = new Ingredient();
        ingredientRepository.save(ingredient);

        Item item = new Item(2, 2.0,  "name", specialOffer, ingredient);
        underTest.save(item);

        //
        Optional<List<Item>> list = underTest.findAllBySpecialOffer_Id(specialOffer.getId());
        //then
        assertThat(list).isPresent();
    }


    @Test
    @Disabled
    void existByIngredient(){
        BaguetteItem baguetteItem = new BaguetteItem();
        baguetteItem.setId(1L);
        baguetteItemRepository.save(baguetteItem);

        Ingredient ingredient = new Ingredient();
        ingredientRepository.save(ingredient);

        Item item = new Item(2, 2.0,  "name", baguetteItem, ingredient);
        underTest.save(item);

        boolean exist = underTest.existsByIngredient(ingredient);
        //then
        assertThat(exist).isTrue();
    }

    @Test
    void findAllByBaguetteItem_Id() {

        Customer customer = new Customer("name", "email", "eshort");
        customerRepository.save(customer);
        BaguetteOrder baguetteOrder = new BaguetteOrder(customer, 2.0, new Date(), 0, "note");
        baguetteOrderRepository.save(baguetteOrder);
        BaguetteItem baguetteItem = new BaguetteItem(baguetteOrder, 2.0, false);
        baguetteItemRepository.save(baguetteItem);
        IngredientType ingredientType = new IngredientType("ingredientTypeName");
        ingredientTypeRepository.save(ingredientType);
        Ingredient ingredient = new Ingredient("name", 2.0, ingredientType);
        ingredientRepository.save(ingredient);
        Item item = new Item(2, 2.0,  "name", baguetteItem, ingredient);
        underTest.save(item);
        Optional<List<Item>> list = underTest.findAllByBaguetteItem_Id(baguetteItem.getId());
        assertThat(list.get()).contains(item);

    }
}