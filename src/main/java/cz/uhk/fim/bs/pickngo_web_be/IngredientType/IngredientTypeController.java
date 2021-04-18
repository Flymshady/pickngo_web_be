package cz.uhk.fim.bs.pickngo_web_be.IngredientType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path="/ingredientType")
public class IngredientTypeController {

    private final IngredientTypeService ingredientTypeService;

    @Autowired
    public IngredientTypeController(IngredientTypeService ingredientTypeService) {
        this.ingredientTypeService = ingredientTypeService;
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<IngredientType> getIngredientTypes() {
        return ingredientTypeService.getIngredientTypes();
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createNewIngredientType(@RequestBody IngredientType ingredientType){
        ingredientTypeService.addNewIngredientType(ingredientType);
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/remove/{ingredientTypeId}", method = RequestMethod.DELETE)
    public void deleteIngredientType(@PathVariable("ingredientTypeId") Long ingredientTypeId){
        ingredientTypeService.deleteIngredientType(ingredientTypeId);
    }

    @Secured({ "ROLE_Admin", "ROLE_Employee"})
    @RequestMapping(value = "/update/{ingredientTypeId}", method = RequestMethod.PUT)
    public void updateIngredientType(
            @PathVariable("ingredientTypeId") Long ingredientTypeId,
            @RequestParam(required = false) String name) {
        ingredientTypeService.updateIngredientType(ingredientTypeId, name);
    }

}
