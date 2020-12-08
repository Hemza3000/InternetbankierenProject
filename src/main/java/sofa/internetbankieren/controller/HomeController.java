package sofa.internetbankieren.controller;

/**
 * @author Wichert Tjerkstra 7 dec aangemaakt
 *
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homeHandler() {
        return "home";
    }

    @GetMapping("/login")
    public String inlogHandler() {
        return "login";
    }

    @GetMapping("/registeren")
    public String registerHandler() {
        return "register";
    }
}