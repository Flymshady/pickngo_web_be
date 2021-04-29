package cz.uhk.fim.bs.pickngo_web_be.Ingredient;

import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findIngredientByName(String name);
    Optional<List<Ingredient>> findAllByIngredientType(Optional<IngredientType> ingredientType);
    boolean existsByIngredientType(IngredientType ingredientType);
}
