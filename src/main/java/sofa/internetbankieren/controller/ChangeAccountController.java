package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import sofa.internetbankieren.model.Klant;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.service.CustomerService;

/**
 * @author Wendy Ellens
 */
@Controller
@SessionAttributes("klant")
public class ChangeAccountController {
    private final CustomerService customerService;

    public ChangeAccountController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/changeAccount")
    public String getOverview(Model model) {
        Klant klant = (Klant) model.getAttribute("klant");
        if (klant instanceof Particulier) {
            return "account/changeParticulier";
        }
        else
            return "account/changeBedrijf";
    }

    @PostMapping("/confirmChanges")
    public String confirmNewClient(@ModelAttribute("klant") Klant klant, Model model) {
        model.addAttribute("klant", klant);
        if (klant instanceof Particulier) {
            return "account/changeParticulier";
        }
        customerService.changeCustomer(klant);
        return "overview";
    }

    //todo alert gegevens gewijzigd - stylen
    //todo wachtwoord wijzigbaar maken (gebruikersnaam niet)
}
