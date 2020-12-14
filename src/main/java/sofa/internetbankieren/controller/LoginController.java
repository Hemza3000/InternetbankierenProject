package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.repository.ParticulierDAO;

import java.util.List;

/**
 * @author
 */
@Controller
public class LoginController {
    private ParticulierDAO particulierDAO;

    public LoginController(ParticulierDAO particulierDAO) {
        this.particulierDAO = particulierDAO;
    }

    @GetMapping("/login")
    public String inlogHandler(Model model) {
      LoginFormBackingBean userDummy = new LoginFormBackingBean("Username a.u.b.", "en password please");
      model.addAttribute("backingBean", userDummy);
      return "login";
    }

    @PostMapping("/inloggen")
    public String postInlogForm(Model model, @ModelAttribute LoginFormBackingBean dummy) {
        // todo ook bedrijven doorzoeken
        System.out.println("inloggen");
        List<Particulier> klanten =
            particulierDAO.getOneByOneGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());
        if(klanten.size() == 0){ // Geen klant met deze inloggegevens
            System.out.println("onbestaande logingegevens");
            return "foutingelogd";
        }
        else{
        model.addAttribute("ingelogde", klanten.get(0));
            System.out.println("ingelogd!");
            return "overview";
        }
    }

    @GetMapping("/error")
    public String loginErrorHandler() {
        return "foutingelogd";
    }

    @GetMapping("/overview")
    public String overviewHandler() {
        return "overview";
    }
}
