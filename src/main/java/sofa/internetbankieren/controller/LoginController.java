package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sofa.internetbankieren.model.Klant;
import sofa.internetbankieren.repository.ParticulierDAO;

@Controller
public class LoginController {
    private ParticulierDAO particulierDAO;

    public LoginController(ParticulierDAO particulierDAO) {
        this.particulierDAO = particulierDAO;
    }

    @GetMapping("/login")
    public String inlogHandler() {
        return "login";
    }

    @PostMapping("/login")
    public String postInlogForm(Model model, @RequestParam String gebruikersnaam, @RequestParam String wachtwoord){
        Klant ingelogde = particulierDAO.getOneByOneGebruikersnaamWachtwoord(gebruikersnaam, wachtwoord);
        if(ingelogde == null){
            return "foutmelding";
        }
        else{
        model.addAttribute("ingelogde", ingelogde);
            return "rekeningoverzicht";
        }

    }


}
