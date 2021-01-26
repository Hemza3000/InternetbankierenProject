package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;
import sofa.internetbankieren.service.AccountService;
import sofa.internetbankieren.service.CustomerService;

/**
 * @author Wichert Tjekrstra
 *
 */

@Controller
@SessionAttributes({"klant", "IBAN"})
public class NewAccountNumberController {
    private ParticulierDAO particulierDAO;
    private TransactieDAO transactieDAO;
    private AccountService accountService;
    private CustomerService customerService;

    public NewAccountNumberController(ParticulierDAO particulierDAO, TransactieDAO transactieDAO,
                                      AccountService accountService, CustomerService customerService) {
        super();
        this.particulierDAO = particulierDAO;
        this.transactieDAO = transactieDAO;
        this.accountService = accountService;
        this.customerService = customerService;
    }

    // Toevoegen van rekeningnummers van een ingelogde gebruiker
    @GetMapping("/newAccountNumberPage")
    public String newAccountNumberPageHandler(Model model, @ModelAttribute(name="klant") Klant ingelogde){
        String newIBAN = accountService.createRandomIBAN();
        model.addAttribute("IBAN", newIBAN);
        model.addAttribute("klant", ingelogde);
        // check of ingelogde is een particulier of een bedrijf.
        boolean bedrijf = false;
        if ( ingelogde instanceof Bedrijf) {
            bedrijf = true;
        }
        model.addAttribute("isBedrijf", bedrijf);
        model.addAttribute("doesExist", true);
        return "account/newAccountNumber-bootstrap";
    }

    @PostMapping("/formNewAccountNumber")
    public String form(Model model, @ModelAttribute(name="klant") Klant ingelogde, @RequestParam String bsnContactpersoon) {
        String newIBAN = (String) model.getAttribute("IBAN");
        // registeren van een Priverekening
        if (ingelogde instanceof Particulier) {
            Priverekening priverekening = new Priverekening(0, newIBAN, transactieDAO, (Particulier) ingelogde);
            accountService.saveNewAccountNumber(priverekening, ingelogde);
            // registeren van bedrijfsrekening
        } else {
            int bsn = Integer.parseInt(bsnContactpersoon);
            // validatie op uniek BSN nummer
            if (!customerService.doesBsnExist(bsn)) {
                model.addAttribute("IBAN", newIBAN);
                model.addAttribute("isBedrijf", true);
                model.addAttribute("doesExist", false);
                return "account/newAccountNumber";
            }
            Bedrijfsrekening bedrijfsrekening = new Bedrijfsrekening(0, newIBAN, transactieDAO,
                    particulierDAO.getByBSN(bsn), (Bedrijf) ingelogde);
            accountService.saveNewAccountNumber(bedrijfsrekening, ingelogde);
        }
        return "overview";
    }


}

