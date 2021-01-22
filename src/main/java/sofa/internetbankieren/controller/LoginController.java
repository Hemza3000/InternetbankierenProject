package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;
import sofa.internetbankieren.service.AccountService;

import java.util.List;

/**
 * @author Hemza Lasri
 */
@Controller
@SessionAttributes("ingelogde")
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
        List inloggerslijst = accountService.getKlantenbyGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());
        if(inloggerslijst.isEmpty()){ // Geen klant met deze inloggegevens
            return "foutingelogd";
        } else{
        model.addAttribute("ingelogde", inloggerslijst.get(0));
            return "overview";
        }
    }
    @GetMapping("/overzicht")
    public String getOverview() {
            return "overview";
    }
}
