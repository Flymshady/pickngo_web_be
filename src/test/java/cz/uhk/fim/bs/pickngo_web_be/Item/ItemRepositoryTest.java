package cz.uhk.fim.bs.pickngo_web_be.Item;

import cz.uhk.fim.bs.pickngo_web_be.BaguetteItem.BaguetteItem;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteItem.BaguetteItemRepository;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrder;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrderRepository;
import cz.uhk.fim.bs.pickngo_web_be.Ingredient.Ingredient;
import cz.uhk.fim.bs.pickngo_web_be.Ingredient.IngredientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository underTest;
    @Autowired
    private BaguetteItemRepository baguetteItemRepository;
    @Autowired
    private IngredientRepository ingredientRepository;

    @AfterEach
    void tearDown(){
        ingredientRepository.deleteAll();
        baguetteItemRepository.deleteAll();
        underTest.deleteAll();
    }
    @Test
    void findAllByBaguetteItem_Id() {
        BaguetteItem baguetteItem = new BaguetteItem();
        baguetteItem.setId(1L);
        baguetteItemRepository.save(baguetteItem);

        Ingredient ingredient = new Ingredient();
        ingredientRepository.save(ingredient);

        Item item = new Item(2, 2.0,  "name", baguetteItem, ingredient);
        underTest.save(item);

        //
        Optional<List<Item>> list = underTest.findAllByBaguetteItem_Id(baguetteItem.getId());
        //then
        assertThat(list).isPresent();
    }
    @Test
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
}