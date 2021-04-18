package cz.uhk.fim.bs.pickngo_web_be.Ingredient;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path="/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Ingredient> getIngredients() {
        return ingredientService.getIngredients();
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/detail/{ingredientName}", method = RequestMethod.GET)
    public Optional<Ingredient> getIngredientByName(@PathVariable("ingredientName") String ingredientName) {
        return ingredientService.getIngredientsByName(ingredientName);
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createNewIngredient(@RequestBody Ingredient ingredient){
        ingredientService.addNewIngredient(ingredient);
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/remove/{ingredientId}", method = RequestMethod.DELETE)
    public void deleteIngredient(@PathVariable("ingredientId") Long ingredientId){
        ingredientService.deleteIngredient(ingredientId);
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/update/{ingredientId}", method = RequestMethod.PUT)
    public void updateUser(
            @PathVariable("ingredientId") Long ingredientId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) double price,
            @RequestParam(required = false) Long ingredientTypeId) {
        ingredientService.updateIngredient(ingredientId, name, price, ingredientTypeId);
    }
}
