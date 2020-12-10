package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/home")
    public String homeHandler(){
        return "home";
    }

    @PostMapping("/login")
    public String loginHandler(

            @RequestParam(name="Gebruikersnaam") String gebruikersnaam,
            @RequestParam(name="Wachtwoord") String wachtwoord,
            Model model){
        return "home";
    }

}
