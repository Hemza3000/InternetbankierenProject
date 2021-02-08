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
 *
 * Geeft de pagina's om klantgegevens te wijzigen en verwerkt de wijzigingen.
 */
@Controller
@SessionAttributes("klant")
public class ChangeAccountController {
    private final CustomerService customerService;

    public ChangeAccountController(CustomerService customerService) {
        super();
        this.customerService = customerService;
    }

    @GetMapping("/changeAccount")
    public String getChangeAccountForm(Model model) {
        Klant klant = (Klant) model.getAttribute("klant");
        if (klant instanceof Particulier)
            return "account/changeParticulier";
        else
            return "account/changeBedrijf";
    }

    @PostMapping("/confirmChanges")
    public String postChangeAccountForm(@ModelAttribute("klant") Klant klant, Model model) {
        model.addAttribute("klant", klant);
        customerService.changeCustomer(klant);
        return "overview";
    }
}
