package cz.uhk.fim.bs.pickngo_web_be.Ingredient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public List<Ingredient> getIngredients() {
        return ingredientService.getIngredients();
    }

    @GetMapping(path = "{ingredientName}")
    public Optional<Ingredient> getIngredientByName(@PathVariable("ingredientName") String ingredientName) {
        return ingredientService.getIngredientsByName(ingredientName);
    }

    @PostMapping
    public void createNewIngredient(@RequestBody Ingredient ingredient){
        ingredientService.addNewIngredient(ingredient);
    }

    @DeleteMapping(path = "{ingredientId}")
    public void deleteIngredient(@PathVariable("ingredientId") Long ingredientId){
        ingredientService.deleteIngredient(ingredientId);
    }

    @PutMapping(path = "{ingredientId}")
    public void updateUser(
            @PathVariable("ingredientId") Long ingredientId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) double price,
            @RequestParam(required = false) Long ingredientTypeId) {
        ingredientService.updateIngredient(ingredientId, name, price, ingredientTypeId);
    }
}
