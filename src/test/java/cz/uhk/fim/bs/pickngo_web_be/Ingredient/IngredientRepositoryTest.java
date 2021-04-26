package cz.uhk.fim.bs.pickngo_web_be.Ingredient;

import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientType;
import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientTypeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository underTest;
    @Autowired
    private IngredientTypeRepository ingredientTypeRepository;

    @AfterEach
    void tearDown(){
        ingredientTypeRepository.deleteAll();
        underTest.deleteAll();
    }


    @Test
    void findIngredientByName() {

        IngredientType ingredientType = new IngredientType("ingredientTypeName");
        ingredientTypeRepository.save(ingredientType);

        //given
        String name = "nazev";
        Ingredient ingredient = new Ingredient(name, 1.0, ingredientType );

        underTest.save(ingredient);
        //when
        Optional<Ingredient> ingredientOptional = underTest.findIngredientByName(name);
        //then
        assertThat(ingredientOptional).isPresent();
    }

    @Test
    void findAllByIngredientType() {
        IngredientType ingredientType = new IngredientType("ingredientTypeName");
        ingredientTypeRepository.save(ingredientType);

        //given
        String name = "nazev";
        Ingredient ingredient = new Ingredient(name, 1.0, ingredientType );

        underTest.save(ingredient);
        //when
        Optional<List<Ingredient>> ingredientOptional = underTest.findAllByIngredientType(Optional.of(ingredientType));
        //then
        assertThat(ingredientOptional).isPresent();
    }
    @Test
    void existsByIngredientType() {

        IngredientType ingredientType = new IngredientType("ingredientTypeName");
        ingredientTypeRepository.save(ingredientType);

        //given
        String name = "nazev";
        Ingredient ingredient = new Ingredient(name, 1.0, ingredientType );

        underTest.save(ingredient);
        //when
        boolean existsByIngredientType = underTest.existsByIngredientType(ingredientType);
        //then
        assertThat(existsByIngredientType).isTrue();
    }

    @Test
    void DoesntExistByIngredientType() {

        IngredientType ingredientType = new IngredientType("ingredientTypeName");
        ingredientTypeRepository.save(ingredientType);

        //given
        String name = "nazev";
        Ingredient ingredient = new Ingredient(name, 1.0, ingredientType );
        //when
        boolean existsByIngredientType = underTest.existsByIngredientType(ingredientType);
        //then
        assertThat(existsByIngredientType).isFalse();
    }
}