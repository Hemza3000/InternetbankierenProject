package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.ParticulierDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;
import sofa.internetbankieren.repository.TransactieDAO;
import sofa.internetbankieren.service.AccountService;

/**
 * @author Wichert Tjekrstra
 *
 */

@Controller
@SessionAttributes("ingelogde")
public class NewAccountNumberController {
    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private ParticulierDAO particulierDAO;
    private TransactieDAO transactieDAO;
    private AccountService accountService;
    private String newIBAN;

    public NewAccountNumberController(PriverekeningDAO priverekeningDAO, TransactieDAO transactieDAO,
    BedrijfsrekeningDAO bedrijfsrekeningDAO, ParticulierDAO particulierDAO, AccountService accountService) {
        this.priverekeningDAO = priverekeningDAO;
        this.transactieDAO = transactieDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.particulierDAO = particulierDAO;
        this.accountService = accountService;
    }

    // Toevoegen van rekeningnummers van een ingelogde gebruiker
    @GetMapping("/newAccountNumberPage")
    public String newAccountNumberPageHandler(Model model, @ModelAttribute(name="ingelogde") Klant ingelogde){
        newIBAN = accountService.createRandomIBAN();
        model.addAttribute("IBAN", newIBAN);
        model.addAttribute("ingelogde", ingelogde);
        // check of ingelogde is een particulier of een bedrijf.
        boolean bedrijf = false;
        if ( ingelogde instanceof Bedrijf) {
            bedrijf = true;
        }
        model.addAttribute("isBedrijf", bedrijf);
        return "account/newAccountNumber";
    }

    @PostMapping("/formNewAccountNumber")
    public String form(Model model, @ModelAttribute(name="ingelogde") Klant ingelogde, @RequestParam String formContactpersoon){
        if (ingelogde instanceof Particulier) {
            Priverekening priverekening = new Priverekening(0, newIBAN, transactieDAO, (Particulier) ingelogde);
            priverekeningDAO.storeOne(priverekening);
            // registeren van bedrijfsrekening
        } else {
            int contactPersoonBSN = Integer.parseInt(formContactpersoon);
            Particulier contactPersoon = particulierDAO.getByBSN(contactPersoonBSN);
            //TODO controle op contactpersoon (op basis van BSN) bestaat)
            Bedrijfsrekening bedrijfsrekening = new Bedrijfsrekening(0, newIBAN, transactieDAO, contactPersoon, (Bedrijf) ingelogde);
            bedrijfsrekeningDAO.storeOne(bedrijfsrekening);
        }
        return "overview";
    }

    // Toevoegen van rekeningnummers van een gebruiker die zich aan het registeren is.

}
