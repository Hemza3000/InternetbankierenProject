package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;
import sofa.internetbankieren.service.AccountService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
@SessionAttributes("ingelogde")
public class LoginController {
    private final ParticulierDAO particulierDAO;
    private final BedrijfDAO bedrijfDAO;


    public LoginController(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO) {
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
    }

    @GetMapping("/login")
    public String inlogHandler(Model model) {
      LoginFormBackingBean userDummy = new LoginFormBackingBean("", "");
      model.addAttribute("backingBean", userDummy);
      return "login";
    }

    @PostMapping("/overzicht")
    public String postInlogForm(Model model, @ModelAttribute LoginFormBackingBean dummy) {
        List<Particulier> particuliereklanten =
            particulierDAO.getOneByGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());
        List<Bedrijf> bedrijfsklanten =
                bedrijfDAO.getOneByGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());
        List<Klant> alleklanten = new ArrayList<>();
        alleklanten.addAll(particuliereklanten);
        alleklanten.addAll(bedrijfsklanten);
        if(alleklanten.size() == 0){ // Geen klant met deze inloggegevens
            return "foutingelogd";
        }
        else{
        model.addAttribute("ingelogde", alleklanten.get(0));
            return "overview";
        }
    }

    @GetMapping("/overzicht")
    public String getInlogForm() {
            return "overview";
    }

}
