package cz.uhk.fim.bs.pickngo_web_be.Ingredient;

import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientType;
import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientTypeRepository;
import cz.uhk.fim.bs.pickngo_web_be.Item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.will;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock private IngredientTypeRepository ingredientTypeRepository;
    @Mock private IngredientRepository ingredientRepository;
    @Mock private ItemRepository itemRepository;
    private IngredientService underTest;

    @BeforeEach
    void setUp() {
        underTest = new IngredientService(ingredientRepository, ingredientTypeRepository , itemRepository);
    }

    @Test
    void getIngredients() {
        //when
        underTest.getIngredients();
        //then
        verify(ingredientRepository).findAll();
    }

    @Test
    void addNewIngredient() {
        //given
        Ingredient ingredient = new Ingredient(
                "name",
                2.0,
                new IngredientType()
        );
        //when
        underTest.addNewIngredient(ingredient);
        //then
        ArgumentCaptor<Ingredient> ingredientArgumentCaptor = ArgumentCaptor.forClass(Ingredient.class);
        verify(ingredientRepository).save(ingredientArgumentCaptor.capture());
        Ingredient captured = ingredientArgumentCaptor.getValue();

        assertThat(captured).isEqualTo(ingredient);
    }
    @Test
    void willThrowWhenNameIsTakenInAddNewIngredient() {
        //given
        Ingredient ingredient = new Ingredient(
                "name",
                2.0,
                new IngredientType()
        );
        given(ingredientRepository.findIngredientByName(ingredient.getName())).willReturn(Optional.of(ingredient));
        //then
        assertThatThrownBy(() ->underTest.addNewIngredient(ingredient))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("name taken");
        verify(ingredientRepository, never()).save(any());

    }

    @Test
    void deleteIngredient() {
        //given
        Ingredient ingredient = new Ingredient(
                "name",
                2.0,
                new IngredientType()
        );

        given(ingredientRepository.existsById(ingredient.getId())).willReturn(true);
        //when
        underTest.deleteIngredient(ingredient.getId());
        //then
        verify(ingredientRepository).deleteById(ingredient.getId());
    }
    @Test
    void WillThrowWhenDoesntExistByIdInDeleteIngredient() {
        //given
        Ingredient ingredient = new Ingredient(
                "name",
                2.0,
                new IngredientType()
        );

        given(ingredientRepository.existsById(ingredient.getId())).willReturn(false);
        //when
        assertThatThrownBy(() ->underTest.deleteIngredient(ingredient.getId()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("ingredient with id"+ ingredient.getId()+"doesnt exist");

        verify(ingredientRepository, never()).delete(any());

    }
    @Test
    void WillThrowWhenUsedInDeleteIngredient() {
        //given
        Ingredient ingredient = new Ingredient(
                "name",
                2.0,
                new IngredientType()
        );
      //  given(itemRepository.existsByIngredient(ingredient)).willReturn(true);
        //when
        assertThatThrownBy(() ->underTest.deleteIngredient(ingredient.getId()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("ingredient is used");

        verify(ingredientTypeRepository, never()).delete(any());

    }

    @Test
    void updateIngredient() {
        //given
        Long id = 42L;
        IngredientType ingredientType = new IngredientType(88L,"type");
        Ingredient ingredient1 = new Ingredient(
                id,
                "name2",
                2.0,
                ingredientType
        );
        Ingredient ingredient2 = new Ingredient(
                id,
                "name",
                4.0,
                ingredientType
        );

        given(ingredientRepository.findById(ingredient1.getId())).willReturn(Optional.of(ingredient1));
        given(ingredientRepository.findIngredientByName(ingredient2.getName())).willReturn(Optional.empty());
        given(ingredientTypeRepository.findById(ingredientType.getId())).willReturn(Optional.of(ingredientType));
        underTest.updateIngredient(ingredient2.getId(), ingredient2.getName(), ingredient2.getPrice(), ingredient2.getIngredientType().getId());
    }
    @Test
    void willThrowWhenByIdDoesntExistInUpdateIngredientType() {

        //given
        Long id = 42L;
        IngredientType ingredientType = new IngredientType(88L,"type");
        Ingredient ingredient1 = new Ingredient(
                id,
                "name2",
                2.0,
                ingredientType
        );
        Ingredient ingredient2 = new Ingredient(
                id,
                "name",
                4.0,
                ingredientType
        );
        given(ingredientRepository.findById(ingredient1.getId())).willReturn(Optional.empty());
        assertThatThrownBy(() ->underTest.updateIngredient(ingredient2.getId(), ingredient2.getName(), ingredient2.getPrice(), ingredient2.getIngredientType().getId()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("ingredient with id"+ id+"doesnt exist");

        verify(ingredientRepository, never()).save(any());

    }
    @Test
    void willThrowWhenNameExistsInUpdateIngredientType() {

        //given
        Long id = 42L;
        IngredientType ingredientType = new IngredientType(88L,"type");
        Ingredient ingredient1 = new Ingredient(
                id,
                "name2",
                2.0,
                ingredientType
        );
        Ingredient ingredient2 = new Ingredient(
                id,
                "name",
                4.0,
                ingredientType
        );
        Ingredient ingredient3 = new Ingredient(
                id,
                "name",
                5.0,
                ingredientType
        );
        given(ingredientRepository.findById(ingredient1.getId())).willReturn(Optional.of(ingredient1));
        given(ingredientRepository.findIngredientByName(ingredient2.getName())).willReturn(Optional.of(ingredient3));
        assertThatThrownBy(() ->underTest.updateIngredient(ingredient2.getId(), ingredient2.getName(), ingredient2.getPrice(), ingredient2.getIngredientType().getId()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("name taken");

        verify(ingredientRepository, never()).save(any());

    }

    @Test
    void getIngredientsByName() {
        String name = "name";
        //when
        underTest.getIngredientsByName(name);
        //then
        verify(ingredientRepository).findIngredientByName(name);
    }
}