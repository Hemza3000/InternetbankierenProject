package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;
import sofa.internetbankieren.service.AccountService;

/**
 * @author Hemza Lasri
 */
@Controller
@SessionAttributes("klant")
public class LoginController {
    private final ParticulierDAO particulierDAO;
    private final BedrijfDAO bedrijfDAO;
    private AccountService accountService;


    public LoginController(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO, AccountService accountService) {
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
        this.accountService = accountService;
    }

    @GetMapping("/login")
    public String inlogHandler(Model model) {
      LoginFormBackingBean userDummy = new LoginFormBackingBean("", "");
      model.addAttribute("backingBean", userDummy);
      return "login";
    }

    @PostMapping("/overzicht")
    public String postInlogForm(Model model, @ModelAttribute LoginFormBackingBean dummy) {
        Klant inlogger = accountService.getKlantbyGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());
        System.out.println(inlogger);
        if(inlogger == null){ // Geen klant met deze inloggegevens
            return "foutingelogd";
        } else{
        model.addAttribute("klant", inlogger);
            return "overview";
        }
    }
    @GetMapping("/overzicht")
    public String getOverview() {
            return "overview";
    }
}
