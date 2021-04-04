package cz.uhk.fim.bs.pickngo_web_be.IngredientType;

import cz.uhk.fim.bs.pickngo_web_be.Ingredient.Ingredient;
import cz.uhk.fim.bs.pickngo_web_be.Ingredient.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IngredientTypeService {

    private final IngredientTypeRepository ingredientTypeRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientTypeService(IngredientTypeRepository ingredientTypeRepository, IngredientRepository ingredientRepository) {
        this.ingredientTypeRepository = ingredientTypeRepository;
        this.ingredientRepository = ingredientRepository;
    }


    public List<IngredientType> getIngredientTypes() {
        return ingredientTypeRepository.findAll();
    }


    public void addNewIngredientType(IngredientType ingredientType) {
        Optional<IngredientType> ingredientTypeOptional = ingredientTypeRepository.findIngredientTypeByName(ingredientType.getName());
        if (ingredientTypeOptional.isPresent()) {
            throw new IllegalStateException("name taken");
        }
        ingredientTypeRepository.save(ingredientType);
    }

    public void deleteIngredientType(Long ingredientTypeId) {
        boolean exists = ingredientTypeRepository.existsById(ingredientTypeId);
        if(!exists){
            throw new IllegalStateException("ingredient type with id "+ ingredientTypeId + " doesnt exist");
        }
        Optional<IngredientType> ingredientType = ingredientTypeRepository.findById(ingredientTypeId);
        Optional<List<Ingredient>> ingredients = ingredientRepository.findAllByIngredientType(ingredientType);
        if(ingredients.isPresent()) {
            throw new IllegalStateException("ingredient type is used for ingredients");
        }
        ingredientTypeRepository.deleteById(ingredientTypeId);
    }
    @Transactional
    public void updateIngredientType(Long ingredientTypeId, String name){
        IngredientType ingredientType = ingredientTypeRepository.findById(ingredientTypeId).orElseThrow(()-> new IllegalStateException("ingredient type with id "+ ingredientTypeId+ "doesnt exist"));
        if (name !=null && name.length() > 0 && !Objects.equals(ingredientType.getName(), name)){
            Optional<IngredientType> ingredientTypeOptional = ingredientTypeRepository.findIngredientTypeByName(name);
            if (ingredientTypeOptional.isPresent()){
                throw new IllegalStateException("name taken");
            }
            ingredientType.setName(name);
        }

    }
}
