package cz.uhk.fim.bs.pickngo_web_be.Item;

import cz.uhk.fim.bs.pickngo_web_be.BaguetteItem.BaguetteItem;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrder;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrderService;
import cz.uhk.fim.bs.pickngo_web_be.Ingredient.Ingredient;
import cz.uhk.fim.bs.pickngo_web_be.SpecialOffer.SpecialOffer;
import cz.uhk.fim.bs.pickngo_web_be.SpecialOffer.SpecialOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock private ItemRepository itemRepository;
    @Mock private SpecialOfferRepository specialOfferRepository;
    private ItemService underTest;
    @BeforeEach
    void setUp() {
        underTest = new ItemService(itemRepository, specialOfferRepository);
    }

    @Test
    void getItems() {
        //when
        underTest.getItems();
        //then
        verify(itemRepository).findAll();
    }

    @Test
    void getItemsByBaguetteItem() {
        BaguetteItem baguetteItem = new BaguetteItem(6L, new BaguetteOrder(), 5.0, false);
        List<Item> list = new ArrayList<>();
        Item item = new Item(89L, 5, 5.0, "name", baguetteItem, new Ingredient() );
        list.add(item);
        baguetteItem.setItems(list);
        underTest.getItemsByBaguetteItem(baguetteItem.getId());
        verify(itemRepository).findAllByBaguetteItem_Id(baguetteItem.getId());
    }

    @Test
    void getItem() {
        Long id = 84L;

        Item item = new Item(id, 5, 2.0, "name", new BaguetteItem(), new Ingredient());
        given(itemRepository.findById(id)).willReturn(Optional.of(item));
        underTest.getItem(id);
        verify(itemRepository).findById(id);

    }

    @Test
    void addToSpecialOffer(){
        //given
        SpecialOffer specialOffer = new SpecialOffer(
                5L,
                "name",
                2.0,
                new ArrayList<>(),
                false
        );
        Long id = 84L;
        Item item = new Item(id, 5, 2.0, "name", new BaguetteItem(), new Ingredient());
        //when
        given(specialOfferRepository.findById(specialOffer.getId())).willReturn(Optional.of(specialOffer));
        underTest.addToSpecialOffer(specialOffer.getId(), item);

    }

    @Test
    void updateItem(){

    }

    @Test
    void removeItem(){

    }
}