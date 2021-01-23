package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;
import sofa.internetbankieren.service.AccountService;
import sofa.internetbankieren.service.RegisterService;

/**
 * @author Wichert Tjerkstra (particulier), Wendy Ellens (bedrijf)
 *
 * Handelt de registratie van (particuliere/zakelijke) klanten af in 4 stappen:
 * 1. Ophalen registratiepagina
 * 2. Verwerken ingevoerde klantgegevens
 * 3. Verwerken eventuele wijzigingen klantgegevens na controle door klant
 * 4. Verwerken ingevoerde logingegevens
 */
@SessionAttributes("klant")
@Controller
public class RegistrationController {

    // Zoals aangegeven door de PO, is het hoofd MKB (medewerker 2) altijd de accountmanager.
    public final static int ID_ACCOUNTMANAGER = 2;
    private final ParticulierDAO particulierDAO;
    private final BedrijfDAO bedrijfDAO;
    private final MedewerkerDAO medewerkerDAO;
    private final BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private final PriverekeningDAO priverekeningDAO;
    private final RegisterService registerService;
    private final AccountService accountService;

    public RegistrationController(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO, MedewerkerDAO medewerkerDAO,
                                  BedrijfsrekeningDAO bedrijfsrekeningDAO, PriverekeningDAO priverekeningDAO,
                                  RegisterService registerService, AccountService accountService) {
        super();
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
        this.medewerkerDAO = medewerkerDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.priverekeningDAO = priverekeningDAO;
        this.registerService = registerService;
        this.accountService = accountService;
    }

    // Stap 1 particulier: ophalen van registratiepagina
    @GetMapping("/particulier")
    public String getParticulierForm(Model model){
        model.addAttribute("klant", new Particulier(bedrijfsrekeningDAO, priverekeningDAO));
        model.addAttribute("fieldExists", false);
        return "register/particulier";
    }

    // Stap 1 bedrijf: ophalen van registratiepagina
    @GetMapping("/zakelijk")
    public String getZakelijkForm(Model model){
        model.addAttribute("klant", new Bedrijf(bedrijfsrekeningDAO, medewerkerDAO.getOneByID(ID_ACCOUNTMANAGER)));
        return "register/bedrijf";
    }

    // Stap 2: verwerken ingevoerde klantgegevens
    @PostMapping("/register")
    public String postNewClientForm(@ModelAttribute("klant") Klant klant, Model model) {
        model.addAttribute("klant", klant);
        if (klant instanceof Bedrijf) {
            return "register/confirmationBedrijf";
        }
        else {
            if(doesBsnExist(((Particulier) klant), model)) {
                return  "register/particulier";
            }
            return "register/confirmationParticulier";
        }
    }

    // Stap 3: verwerken eventuele wijzigingen in klantgegevens na controle door klant
    @PostMapping("/confirm")
    public String confirmNewClient(@ModelAttribute("klant") Klant klant, Model model) {
        model.addAttribute("klant", klant);
        if (klant instanceof Particulier && doesBsnExist(((Particulier) klant), model)) {
            return  "register/confirmationParticulier";
            }
        return "register/registerLogin";
    }

    // Stap 4: verwerken ingevoerde logingegevens
    @PostMapping("/storeLogin")
    public String confirmLogin(@ModelAttribute("klant") Klant klant, Model model){

        // Controleert of er in de database al klant met deze gebruikersnaam is
        if (!registerService.checkUniqueUsername(klant.getGebruikersnaam())) {
            model.addAttribute("klant", klant);
            model.addAttribute("fieldExists", true);
            return "register/registerLogin";
        }

        if (klant instanceof Particulier) {
            particulierDAO.storeOne((Particulier) klant);
        }
        else if (klant instanceof Bedrijf) {
            bedrijfDAO.storeOne((Bedrijf) klant);
        }
        model.addAttribute("klant", klant);
        return "register/completed";
    }

    // Controleert of er in de database al particuliere klant met dit BSN is
    private boolean doesBsnExist(Particulier particulier, Model model) {
        if (accountService.doesBsnExist(particulier.getBSN())) {
            model.addAttribute("fieldExists", true);
            return true;
        }
        model.addAttribute("fieldExists", false);
        return false;
    }
}

// TODO opslaan met DAO's naar Service