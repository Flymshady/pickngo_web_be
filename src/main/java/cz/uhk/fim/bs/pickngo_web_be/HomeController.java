package cz.uhk.fim.bs.pickngo_web_be;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path="/")
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHello(){
        return "hello there";
    }
}
