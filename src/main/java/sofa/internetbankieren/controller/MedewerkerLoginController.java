package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.repository.MedewerkerDAO;

/**
 * @TacoJongkind 15-12-2020
 *
 * */
@Controller
public class MedewerkerLoginController {
    private MedewerkerDAO medewerkerDAO;

    public MedewerkerLoginController(MedewerkerDAO medewerkerDAO) {
        this.medewerkerDAO = medewerkerDAO;
    }

    @GetMapping("/login_medewerker")
        public String inlogMedewerkerHandler(Model model) {
        LoginFormBackingBean userDummy = new LoginFormBackingBean("Gebruikersnaam", "Wachtwoord");
        model.addAttribute("backingBean", userDummy);
        return "login_medewerker";
    }


}
