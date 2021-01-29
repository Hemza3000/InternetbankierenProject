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
        model.addAttribute("fieldExists", false);
        if (klant instanceof Particulier) {
            return "account/changeParticulier";
        }
        else
            return "account/changeBedrijf";
    }

    @PostMapping("/confirmChanges")
    public String confirmNewClient(@ModelAttribute("klant") Klant klant, Model model) {
        model.addAttribute("klant", klant);

        // Controleert of er in de database al een particuliere klant met dit BSN is
        if (klant instanceof Particulier && customerService.doesBsnExist(((Particulier) klant).getBSN())) {
            model.addAttribute("fieldExists", true);
            return "account/changeParticulier";
        }

        customerService.changeCustomer(klant);
        return "overview";
    }

    //done todo knop terug naar rekeningoverzicht - wijzigingen niet bewaren in sessie
    //done todo alert gegevens gewijzigd
    //todo gebruikersnaam & wachtwoord wijzigbaar maken
    //todo controle uniciteit gebruikersnaam
    //belangrijk! todo controle uniciteit BSN - mag wel gelijk aan deze klant
    //done Particulier: straat + woonplaats automatisch (Bedrijf werkt wel)
    //done BSN-regex (Regexen Bedrijf werken wel)
    //done leeftijdscontrole
    //todo bevestingspagina ipv alert, anders ook bij uniciteitsfoutmelding
}
