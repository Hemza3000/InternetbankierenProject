package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/") //http://localhost:8080/
    public String loginHandler(){
        return "login";
    }
}
