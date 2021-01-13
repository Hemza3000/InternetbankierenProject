package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;
import sofa.internetbankieren.service.AccountService;

import java.util.List;

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
        model.addAttribute("doesExist", true);
        return "account/newAccountNumber";
    }

    @PostMapping("/formNewAccountNumber")
    public String form(Model model, @ModelAttribute(name="ingelogde") Klant ingelogde, @RequestParam int bsnContactpersoon) {
        if (ingelogde instanceof Particulier) {
            Priverekening priverekening = new Priverekening(0, newIBAN, transactieDAO, (Particulier) ingelogde);
            priverekeningDAO.storeOne(priverekening);
            // registeren van bedrijfsrekening
        } else {
            // validatie op uniek BSN nummer
            if (!checkUniqueBSN(bsnContactpersoon)) {
                model.addAttribute("IBAN", newIBAN);
                model.addAttribute("isBedrijf", true);
                model.addAttribute("doesExist", false);
                return "account/newAccountNumber";
            }
            Bedrijfsrekening bedrijfsrekening = new Bedrijfsrekening(0, newIBAN, transactieDAO,
                    particulierDAO.getByBSN(bsnContactpersoon), (Bedrijf) ingelogde);
            bedrijfsrekeningDAO.storeOne(bedrijfsrekening);
        }
        return "overview";
    }

    public boolean checkUniqueBSN(int bsn) {
        return particulierDAO.getAllByBSN(bsn).size() == 1;
    }

    // TODO Toevoegen van rekeningnummers van een gebruiker die zich aan het registeren is.
}

