package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.model.Medewerker;
import sofa.internetbankieren.repository.MedewerkerDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @TacoJongkind 15-12-2020
 *
 * */
@Controller
public class MedewerkerLoginController {
    private MedewerkerDAO medewerkerDAO;
    private final Map<Medewerker.Rol, String> volgendePagina = new HashMap<>(); // toegevoegd door Wendy i.h.k.v. Maintenance

    public MedewerkerLoginController(MedewerkerDAO medewerkerDAO) {
        this.medewerkerDAO = medewerkerDAO;
        volgendePagina.put(Medewerker.Rol.HOOFD_PARTICULIEREN, "/medewerker/overviewHoofdParticulierenDummy");
        volgendePagina.put(Medewerker.Rol.HOOFD_MKB, "/medewerker/overviewHoofdMkbDummy");
        volgendePagina.put(Medewerker.Rol.ACCOUNTMANAGER, "/medewerker/overviewAccountmanagerDummy");
    }

    @GetMapping("/login_medewerker")
    public String inlogMedewerkerHandler(Model model) {
        LoginFormBackingBean userDummy = new LoginFormBackingBean("", "");
        model.addAttribute("backingBean", userDummy);
        return "login_medewerker";
    }

    @PostMapping("/login_medewerker")
    public String postInlogForm(Model model, @ModelAttribute LoginFormBackingBean dummy) {
        List<Medewerker> medewerkers =
                medewerkerDAO.getOneByGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());
        if (medewerkers.size() == 0) { // Geen medewerker met deze inloggegevens
            System.out.println("onbestaande logingegevens");
            return "foutingelogd";
        } else {
            Medewerker ingelogde = medewerkers.get(0);
            model.addAttribute("ingelogde", ingelogde);
            // switch vervangen door map volgendePagina door Wendy i.h.k.v. Maintenance
            return volgendePagina.getOrDefault(ingelogde.getRol(), "error");
        }
    }
}
