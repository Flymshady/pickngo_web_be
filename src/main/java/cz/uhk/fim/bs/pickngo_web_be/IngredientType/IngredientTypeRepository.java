package cz.uhk.fim.bs.pickngo_web_be.IngredientType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientTypeRepository extends JpaRepository<IngredientType, Long> {
}
