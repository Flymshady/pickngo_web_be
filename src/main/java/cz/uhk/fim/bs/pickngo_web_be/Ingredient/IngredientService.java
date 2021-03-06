package cz.uhk.fim.bs.pickngo_web_be.Ingredient;

import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientType;
import cz.uhk.fim.bs.pickngo_web_be.IngredientType.IngredientTypeRepository;
import cz.uhk.fim.bs.pickngo_web_be.Item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientTypeRepository ingredientTypeRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, IngredientTypeRepository ingredientTypeRepository, ItemRepository itemRepository) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientTypeRepository = ingredientTypeRepository;
        this.itemRepository = itemRepository;
    }


    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }


    @Transactional
    public void addNewIngredient(Ingredient ingredient, Long ingredientTypeId) {
        Optional<IngredientType> ingredientTypeOptional = ingredientTypeRepository.findById(ingredientTypeId);
        if (!ingredientTypeOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ingredient type could not be found");
        }
        Optional<Ingredient> ingredientOptional = ingredientRepository.findIngredientByName(ingredient.getName());
        if (ingredientOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "name taken");
        }
        if(ingredientTypeOptional.get().getIngredients()!=null){
            ingredientTypeOptional.get().getIngredients().add(ingredient);
        }else
        {
            List<Ingredient> ingredients = new ArrayList<>();
            ingredients.add(ingredient);
            ingredientTypeOptional.get().setIngredients(ingredients);
        }
        ingredientTypeRepository.save(ingredientTypeOptional.get());
        ingredient.setIngredientType(ingredientTypeOptional.get());
        ingredientRepository.save(ingredient);
    }

    @Transactional
    public void deleteIngredient(Long ingredientId) {
        boolean exists = ingredientRepository.existsById(ingredientId);
        if(!exists){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ingredient with id "+ ingredientId+ "doesnt exist");

        }
        Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientId);
        if(!ingredient.isPresent()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ingredient with id "+ ingredientId+ "doesnt exist");

        }
        boolean used = itemRepository.existsByIngredient(ingredient.get());
        if(used){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ingredient is used");
        }
        Optional<IngredientType> ingredientTypeOptional = ingredientTypeRepository.findById(ingredient.get().getIngredientType().getId());
        if (!ingredientTypeOptional.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ingredient type could not be found");
        }
        if(ingredientTypeOptional.get().getIngredients()!=null) {
            ingredientTypeOptional.get().getIngredients().remove(ingredient);
            ingredientTypeRepository.save(ingredientTypeOptional.get());
        }
        ingredientRepository.deleteById(ingredientId);
    }
    @Transactional
    public void updateIngredient(Long  ingredientId, String name, double price, Long ingredientTypeId){
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(()->
                new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "ingredient with id "+ ingredientId+ "doesnt exist"));
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
