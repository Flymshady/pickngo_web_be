package cz.uhk.fim.bs.pickngo_web_be.Item;

import cz.uhk.fim.bs.pickngo_web_be.BaguetteItem.BaguetteItem;
import cz.uhk.fim.bs.pickngo_web_be.BaguetteOrder.BaguetteOrder;
import cz.uhk.fim.bs.pickngo_web_be.Ingredient.Ingredient;
import cz.uhk.fim.bs.pickngo_web_be.SpecialOffer.SpecialOffer;
import cz.uhk.fim.bs.pickngo_web_be.SpecialOffer.SpecialOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
        Item item = new Item(id, 5, 2.0, "name", specialOffer, new Ingredient());
        //when
        given(specialOfferRepository.findById(specialOffer.getId())).willReturn(Optional.of(specialOffer));
        underTest.addToSpecialOffer(specialOffer.getId(), item);

    }

    @Test
    void updateItem(){
        SpecialOffer specialOffer = new SpecialOffer(
                5L,
                "name",
                2.0,
                new ArrayList<>(),
                false
        );
        Ingredient ingredient = new Ingredient();
        Long id = 84L;
        Item item = new Item(id, 5, 2.0, "name", specialOffer, ingredient);
        Item item2 = new Item(id, 55, 5.0, "name2", specialOffer,ingredient);

        given(specialOfferRepository.findById(specialOffer.getId())).willReturn(Optional.of(specialOffer));
        given(itemRepository.findById(id)).willReturn(Optional.of(item));

        underTest.updateItem(specialOffer.getId(), item2.getId(), item2.getAmount());
    }
    @Test
    void ThrowWhenUpdateItem(){
        SpecialOffer specialOffer = new SpecialOffer(
                5L,
                "name",
                2.0,
                new ArrayList<>(),
                false
        );
        Ingredient ingredient = new Ingredient();
        Long id = 84L;
        Item item = new Item(id, 5, 2.0, "name", specialOffer, ingredient);
        Item item2 = new Item(id, 55, 5.0, "name2", specialOffer,ingredient);
        given(specialOfferRepository.findById(specialOffer.getId())).willReturn(Optional.empty());
//        given(itemRepository.findById(id)).willReturn(Optional.of(item));

        assertThatThrownBy(() ->underTest.updateItem(specialOffer.getId(), item2.getId(), item2.getAmount()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("chyba, speciální nabídka nenalezena");

        verify(itemRepository, never()).save(any());
    }
    @Test
    void AlsoThrowWhenUpdateItem(){
        SpecialOffer specialOffer = new SpecialOffer(
                5L,
                "name",
                2.0,
                new ArrayList<>(),
                false
        );
        Ingredient ingredient = new Ingredient();
        Long id = 84L;
        Item item = new Item(id, 5, 2.0, "name", specialOffer, ingredient);
        Item item2 = new Item(id, 55, 5.0, "name2", specialOffer,ingredient);
        given(specialOfferRepository.findById(specialOffer.getId())).willReturn(Optional.of(specialOffer));
        given(itemRepository.findById(id)).willReturn(Optional.empty());

        assertThatThrownBy(() ->underTest.updateItem(specialOffer.getId(), item2.getId(), item2.getAmount()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("chyba, položka nenalezena");

        verify(itemRepository, never()).save(any());
    }

    @Test
    void removeItem(){
        SpecialOffer specialOffer = new SpecialOffer(
                5L,
                "name",
                2.0,
                new ArrayList<>(),
                false
        );
        Ingredient ingredient = new Ingredient();
        Long id = 84L;
        Item item = new Item(id, 5, 2.0, "name", specialOffer, ingredient);
        given(specialOfferRepository.findById(specialOffer.getId())).willReturn(Optional.of(specialOffer));
        given(itemRepository.findById(id)).willReturn(Optional.of(item));

        underTest.removeItem(specialOffer.getId(), item.getId());
        verify(itemRepository).delete(item);
    }

    @Test
    void WontRemoveItem(){
        SpecialOffer specialOffer = new SpecialOffer(
                5L,
                "name",
                2.0,
                new ArrayList<>(),
                false
        );
        Ingredient ingredient = new Ingredient();
        Long id = 84L;
        Item item = new Item(id, 5, 2.0, "name", specialOffer, ingredient);
        given(specialOfferRepository.findById(specialOffer.getId())).willReturn(Optional.empty());

        assertThatThrownBy(() ->underTest.removeItem(specialOffer.getId(), item.getId()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("chyba, speciální nabídka nenalezena");

        verify(itemRepository, never()).save(any());
    }
    @Test
    void AlsoWontRemoveItem(){
        SpecialOffer specialOffer = new SpecialOffer(
                5L,
                "name",
                2.0,
                new ArrayList<>(),
                false
        );
        Ingredient ingredient = new Ingredient();
        Long id = 84L;
        Item item = new Item(id, 5, 2.0, "name", specialOffer, ingredient);
        given(specialOfferRepository.findById(specialOffer.getId())).willReturn(Optional.of(specialOffer));
        given(itemRepository.findById(id)).willReturn(Optional.empty());
        assertThatThrownBy(() ->underTest.removeItem(specialOffer.getId(), item.getId()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("chyba, položka nenalezena");

        verify(itemRepository, never()).save(any());
    }
}