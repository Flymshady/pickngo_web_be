package cz.uhk.fim.bs.pickngo_web_be.IngredientType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/ingredientType")
public class IngredientTypeController {

    private final IngredientTypeService ingredientTypeService;

    @Autowired
    public IngredientTypeController(IngredientTypeService ingredientTypeService) {
        this.ingredientTypeService = ingredientTypeService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<IngredientType> getIngredientTypes() {
        return ingredientTypeService.getIngredientTypes();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createNewIngredientType(@RequestBody IngredientType ingredientType){
        ingredientTypeService.addNewIngredientType(ingredientType);
    }

    @RequestMapping(value = "/remove/{ingredientTypeId}", method = RequestMethod.DELETE)
    public void deleteIngredientType(@PathVariable("ingredientTypeId") Long ingredientTypeId){
        ingredientTypeService.deleteIngredientType(ingredientTypeId);
    }

    @RequestMapping(value = "/update/{ingredientTypeId}", method = RequestMethod.PUT)
    public void updateIngredientType(
            @PathVariable("ingredientTypeId") Long ingredientTypeId,
            @RequestParam(required = false) String name) {
        ingredientTypeService.updateIngredientType(ingredientTypeId, name);
    }

}
