package cz.uhk.fim.bs.pickngo_web_be.Ingredient;

import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientType;
import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientTypeRepository ingredientTypeRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, IngredientTypeRepository ingredientTypeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientTypeRepository = ingredientTypeRepository;
    }


    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }


    public void addNewIngredient(Ingredient ingredient) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findIngredientByName(ingredient.getName());
        if (ingredientOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "name taken");
        }
        ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long ingredientId) {
        boolean exists = ingredientRepository.existsById(ingredientId);
        if(!exists){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ingredient with id "+ ingredientId+ "doesnt exist");

        }

        ingredientRepository.deleteById(ingredientId);
    }
    @Transactional
    public void updateIngredient(Long  ingredientId, String name, double price, Long ingredientTypeId){
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(()-> new IllegalStateException("ingredient with id "+ ingredientId+ "doesnt exist"));
        if (name !=null && name.length() > 0 && !Objects.equals(ingredient.getName(), name)){
            Optional<Ingredient> ingredientOptional = ingredientRepository.findIngredientByName(name);
            if (ingredientOptional.isPresent()){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "name taken");
            }
            ingredient.setName(name);
        }

        if (price  > 0.0 && !Objects.equals(ingredient.getPrice(), price)){
            ingredient.setPrice(price);
        }
        IngredientType ingredientType = ingredientTypeRepository.findById(ingredientTypeId).orElseThrow(()->
            new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "ingredient type with id "+ ingredientTypeId+ "doesnt exist"));

        if (ingredientType !=null && !Objects.equals(ingredient.getIngredientType(), ingredientType)){
            ingredient.setIngredientType(ingredientType);
        }
        ingredientRepository.save(ingredient);

    }

    public Optional<Ingredient> getIngredientsByName(String ingredientName) {
        return ingredientRepository.findIngredientByName(ingredientName);
    }
}
