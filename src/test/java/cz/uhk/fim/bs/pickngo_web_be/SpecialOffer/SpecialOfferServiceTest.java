package cz.uhk.fim.bs.pickngo_web_be.SpecialOffer;

import cz.uhk.fim.bs.pickngo_web_be.Ingredient.Ingredient;
import cz.uhk.fim.bs.pickngo_web_be.Item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SpecialOfferServiceTest {

    @Mock
    private SpecialOfferRepository specialOfferRepository;
    private SpecialOfferService underTest;
    @BeforeEach
    void setUp() {
        underTest = new SpecialOfferService(specialOfferRepository);
    }

    @Test
    void getSpecialOffers() {
        //when
        underTest.getSpecialOffers();
        //then
        verify(specialOfferRepository).findAll();
    }

    @Test
    void getSpecialOffer() {
        Long id = 84L;
        SpecialOffer specialOffer = new SpecialOffer(id, "name", 2.0, true);
        given(specialOfferRepository.findById(id)).willReturn(Optional.of(specialOffer));
        underTest.getSpecialOffer(id);
        verify(specialOfferRepository).findById(id);
    }

    @Test
    void createNewSpecialOffer() {
        //given
        SpecialOffer specialOffer = new SpecialOffer(
                5L,
                "name",
                2.0,
                false
        );
        //when
        underTest.createNewSpecialOffer(specialOffer);
        //then
        ArgumentCaptor<SpecialOffer> ingredientArgumentCaptor = ArgumentCaptor.forClass(SpecialOffer.class);
        verify(specialOfferRepository).save(ingredientArgumentCaptor.capture());
        SpecialOffer captured = ingredientArgumentCaptor.getValue();
        assertThat(captured).isEqualTo(specialOffer);
    }

    @Test
    void updateSpecialOffer() {

        List<Item> items = new ArrayList<>();
        List<Item> items2 = new ArrayList<>();
         //given
        Long id = 42L;
        SpecialOffer specialOffer = new SpecialOffer(
                id,
                "name",
                2.0,
                false
        );
        SpecialOffer specialOffer2 = new SpecialOffer(
                id,
                "name2",
                4.0,
                true
        );
        Item item = new Item(2, 2.0, "name1", specialOffer, new Ingredient());
        Item item2 = new Item(5, 5.0, "name2", specialOffer2, new Ingredient());
        items.add(item);
        items2.add(item2);
        specialOffer.setItems(items);
        specialOffer2.setItems(items2);
        given(specialOfferRepository.findById(specialOffer2.getId())).willReturn(Optional.of(specialOffer));
        underTest.updateSpecialOffer(specialOffer2.getId(), specialOffer2.getName(), specialOffer2.getPrice(), specialOffer2.getItems(), specialOffer2.isActive());
    }

    @Test
    void WillThrowWhenSpecialOfferDoesntExist(){
        List<Item> items = new ArrayList<>();
        List<Item> items2 = new ArrayList<>();
        //given
        Long id = 42L;
        SpecialOffer specialOffer = new SpecialOffer(
                id,
                "name",
                2.0,
                false
        );
        SpecialOffer specialOffer2 = new SpecialOffer(
                id,
                "name2",
                4.0,
                true
        );
        Item item = new Item(2, 2.0, "name1", specialOffer, new Ingredient());
        Item item2 = new Item(5, 5.0, "name2", specialOffer2, new Ingredient());
        items.add(item);
        items2.add(item2);
        specialOffer.setItems(items);
        specialOffer2.setItems(items2);
        given(specialOfferRepository.findById(specialOffer2.getId())).willReturn(Optional.empty());

        assertThatThrownBy(() ->underTest.updateSpecialOffer(
                specialOffer2.getId(), specialOffer2.getName(), specialOffer2.getPrice(), specialOffer2.getItems(), specialOffer2.isActive()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("Speciální nabídka nenalezena");

        verify(specialOfferRepository, never()).save(any());
    }


    @Test
    void updateActivitySpecialOffer() {
        List<Item> items = new ArrayList<>();
        List<Item> items2 = new ArrayList<>();
        //given
        Long id = 42L;
        SpecialOffer specialOffer = new SpecialOffer(
                id,
                "name",
                2.0,
                false
        );
        SpecialOffer specialOffer2 = new SpecialOffer(
                id,
                "name2",
                4.0,
                true
        );
        Item item = new Item(2, 2.0, "name1", specialOffer, new Ingredient());
        Item item2 = new Item(5, 5.0, "name2", specialOffer2, new Ingredient());
        items.add(item);
        items2.add(item2);
        specialOffer.setItems(items);
        specialOffer2.setItems(items2);
        given(specialOfferRepository.findById(specialOffer2.getId())).willReturn(Optional.of(specialOffer));
        underTest.updateActivitySpecialOffer(specialOffer2.getId(), specialOffer2.isActive());

    }

    @Test
    void getSpecialOffersByActive() {
        //when
        underTest.getSpecialOffersByActive(true);
        //then
        verify(specialOfferRepository).findAllByActive(true);
    }
}