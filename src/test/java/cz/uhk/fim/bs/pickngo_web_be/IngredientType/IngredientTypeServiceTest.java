package cz.uhk.fim.bs.pickngo_web_be.IngredientType;

import cz.uhk.fim.bs.pickngo_web_be.Ingredient.Ingredient;
import cz.uhk.fim.bs.pickngo_web_be.Ingredient.IngredientRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IngredientTypeServiceTest {

    @Mock
    private IngredientTypeRepository ingredientTypeRepository;

    @Mock private IngredientRepository ingredientRepository;
    private IngredientTypeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new IngredientTypeService(ingredientTypeRepository, ingredientRepository);
    }

    @Test
    void getIngredientTypes() {
        //when
        underTest.getIngredientTypes();
        //then
        verify(ingredientTypeRepository).findAll();
    }

    @Test
    void addNewIngredientType() {
        //given
        IngredientType ingredientType = new IngredientType(
                "name"
        );
        //when
        underTest.addNewIngredientType(ingredientType);
        //then
        ArgumentCaptor<IngredientType> ingredientTypeArgumentCaptor = ArgumentCaptor.forClass(IngredientType.class);
        verify(ingredientTypeRepository).save(ingredientTypeArgumentCaptor.capture());
        IngredientType captured = ingredientTypeArgumentCaptor.getValue();

        assertThat(captured).isEqualTo(ingredientType);
    }
    @Test
    void willThrowWhenNameIsTakenInAddNewIngredientType() {
        //given
        IngredientType ingredientType = new IngredientType(
                "name"
        );
        given(ingredientTypeRepository.findIngredientTypeByName(ingredientType.getName())).willReturn(Optional.of(ingredientType));
        //then
        assertThatThrownBy(() ->underTest.addNewIngredientType(ingredientType))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("name taken");
        verify(ingredientTypeRepository, never()).save(any());

    }

    @Test
    void deleteIngredientType() {
        //given
        IngredientType ingredientType = new IngredientType(
                1L,
                "name"
        );

        given(ingredientTypeRepository.existsById(ingredientType.getId())).willReturn(true);
        given(ingredientTypeRepository.findById(ingredientType.getId())).willReturn(Optional.of(ingredientType));
        //when
        underTest.deleteIngredientType(ingredientType.getId());
        //then
        verify(ingredientTypeRepository).deleteById(ingredientType.getId());
    }
    @Test
    void WillThrowWhenDoesntExistByIdInDeleteIngredientType() {
        //given
        IngredientType ingredientType = new IngredientType(
                1L,
                "name"
        );

        given(ingredientTypeRepository.existsById(ingredientType.getId())).willReturn(false);
        //when
        assertThatThrownBy(() ->underTest.deleteIngredientType(ingredientType.getId()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("ingredient type with id"+ ingredientType.getId()+"doesnt exist");

        verify(ingredientTypeRepository, never()).delete(any());

    }
    @Test
    void WillThrowWhenUsedInDeleteIngredientType() {
        //given
        IngredientType ingredientType = new IngredientType(
                68L,
                "nameTest"
        );

        //when
        assertThatThrownBy(() ->underTest.deleteIngredientType(ingredientType.getId()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("ingredient type is used for ingredients");

        verify(ingredientTypeRepository, never()).delete(any());

    }

    @Test
    void updateIngredientType() {
        Long id = 42L;
        IngredientType ingredientType1 = new IngredientType(
                id,
                "name"
        );
        IngredientType ingredientType2 = new IngredientType(
                id,
                "name2"
        );
        given(ingredientTypeRepository.findById(ingredientType1.getId())).willReturn(Optional.of(ingredientType1));
        given(ingredientTypeRepository.findIngredientTypeByName(ingredientType2.getName())).willReturn(Optional.empty());
        underTest.updateIngredientType(ingredientType2.getId(), ingredientType2.getName());


//        verify(ingredientTypeRepository).save(ingredientType2);
    }
    @Test
    void willThrowWhenByIdDoesntExistInUpdateIngredientType() {

        Long id = 42L;
        IngredientType ingredientType1 = new IngredientType(
                id,
                "name"
        );
        IngredientType ingredientType2 = new IngredientType(
                id,
                "name2"
        );
        given(ingredientTypeRepository.findById(ingredientType1.getId())).willReturn(Optional.empty());
        assertThatThrownBy(() ->underTest.updateIngredientType(ingredientType2.getId(), ingredientType2.getName()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("ingredient type with id"+ id+"doesnt exist");

        verify(ingredientTypeRepository, never()).save(any());

    }
    @Test
    void willThrowWhenNameExistsInUpdateIngredientType() {

        Long id = 42L;
        IngredientType ingredientType1 = new IngredientType(
                id,
                "name"
        );
        IngredientType ingredientType2 = new IngredientType(
                id,
                "name2"
        );
        IngredientType ingredientType3 = new IngredientType(
                id+1,
                "name2"
        );
        given(ingredientTypeRepository.findById(ingredientType1.getId())).willReturn(Optional.of(ingredientType1));
        given(ingredientTypeRepository.findIngredientTypeByName(ingredientType2.getName())).willReturn(Optional.of(ingredientType3));
        assertThatThrownBy(() ->underTest.updateIngredientType(ingredientType2.getId(), ingredientType2.getName()))
                .isInstanceOf(ResponseStatusException.class).withFailMessage("name taken");

        verify(ingredientTypeRepository, never()).save(any());

    }
}