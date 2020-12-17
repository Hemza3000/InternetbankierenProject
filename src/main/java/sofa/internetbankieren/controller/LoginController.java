package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Klant;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.repository.BedrijfDAO;
import sofa.internetbankieren.model.Priverekening;
import sofa.internetbankieren.repository.ParticulierDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 */
@SessionAttributes("user")
@Controller
public class LoginController {
    private ParticulierDAO particulierDAO;
    private BedrijfDAO bedrijfDAO;

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

    @PostMapping("/inloggen")
    public String postInlogForm(Model model, @ModelAttribute LoginFormBackingBean dummy) {
        System.out.println("inloggen");

        List<Particulier> particuliereklanten =
            particulierDAO.getOneByGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());

        List<Bedrijf> bedrijfsklanten =
                bedrijfDAO.getOneByOneGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());

        List<Klant> alleklanten = new ArrayList<>();
        alleklanten.addAll(particuliereklanten);
        alleklanten.addAll(bedrijfsklanten);

        if(alleklanten.size() == 0){ // Geen klant met deze inloggegevens
            System.out.println("onbestaande logingegevens");
            return "foutingelogd";
        }
        else{
        model.addAttribute("ingelogde", alleklanten.get(0));
            System.out.println("ingelogd!");
            model.addAttribute("rekeningoverzicht", alleklanten.get(0));
            model.addAttribute("name", "Welkom " + alleklanten.get(0));
            model.addAttribute("user", alleklanten.get(0));
            return "overview";
        }
    }

    @GetMapping("/error")
    public String loginErrorHandler() {
        return "foutingelogd";
    }

}
