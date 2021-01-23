package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.service.CustomerService;

import java.util.List;

/**
 * @author Hemza Lasri
 */
@Controller
@SessionAttributes("klant")
public class LoginController {
    private CustomerService customerService;

    public LoginController(CustomerService customerService) {
        super();
        this.customerService = customerService;
    }

    @GetMapping("/login")
    public String inlogHandler(Model model) {
      LoginFormBackingBean userDummy = new LoginFormBackingBean("", "");
      model.addAttribute("backingBean", userDummy);
      return "login";
    }

    @PostMapping("/overzicht")
    public String postInlogForm(Model model, @ModelAttribute LoginFormBackingBean dummy) {
        List inloggerslijst = customerService.getKlantenbyGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());
        if(inloggerslijst.isEmpty()){ // Geen klant met deze inloggegevens
            return "foutingelogd";
        } else{
        model.addAttribute("klant", inloggerslijst.get(0));
            return "overview";
        }
    }
    @GetMapping("/overzicht")
    public String getOverview() {
            return "overview";
    }
}
