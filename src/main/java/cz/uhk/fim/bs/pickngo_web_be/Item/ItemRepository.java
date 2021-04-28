package cz.uhk.fim.bs.pickngo_web_be.Item;

import cz.uhk.fim.bs.pickngo_web_be.Ingredient.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<List<Item>> findAllByBaguetteItem_Id(Long baguetteItemId);
    Optional<List<Item>> findAllBySpecialOffer_Id(Long specialOfferId);
    boolean existsByIngredient(Ingredient ingredient);
}
