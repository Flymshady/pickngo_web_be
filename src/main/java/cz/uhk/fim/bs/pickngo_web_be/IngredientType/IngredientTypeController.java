package cz.uhk.fim.bs.pickngo_web_be.IngredientType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/ingredienttype")
public class IngredientTypeController {

    private final IngredientTypeService ingredientTypeService;

    @Autowired
    public IngredientTypeController(IngredientTypeService ingredientTypeService) {
        this.ingredientTypeService = ingredientTypeService;
    }

    @GetMapping
    public List<IngredientType> getIngredientTypes() {
        return ingredientTypeService.getIngredientTypes();
    }

    @PostMapping
    public void createNewIngredientType(@RequestBody IngredientType ingredientType){
        ingredientTypeService.addNewIngredientType(ingredientType);
    }

    @DeleteMapping(path = "{ingredientTypeId}")
    public void deleteUser(@PathVariable("ingredientTypeId") Long ingredientTypeId){
        ingredientTypeService.deleteIngredientType(ingredientTypeId);
    }

    @PutMapping(path = "{ingredientTypeId}")
    public void updateUser(
            @PathVariable("ingredientTypeId") Long ingredientTypeId,
            @RequestParam(required = false) String name) {
        ingredientTypeService.updateIngredientType(ingredientTypeId, name);
    }

}
