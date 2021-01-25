package sofa.internetbankieren.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;
import sofa.internetbankieren.service.CustomerService;

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
    private final MedewerkerDAO medewerkerDAO;
    private final BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private final PriverekeningDAO priverekeningDAO;
    private final CustomerService customerService;

    public RegistrationController(MedewerkerDAO medewerkerDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO,
                                  PriverekeningDAO priverekeningDAO, CustomerService customerService) {
        super();
        this.medewerkerDAO = medewerkerDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.priverekeningDAO = priverekeningDAO;
        this.customerService = customerService;
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
        if (!customerService.checkUniqueUsername(klant.getGebruikersnaam())) {
            model.addAttribute("klant", klant);
            model.addAttribute("fieldExists", true);
            return "register/registerLogin";
        }

        customerService.storeNewCustomer(klant);
        model.addAttribute("klant", klant);
        return "register/completed";
    }

    // Controleert of er in de database al een particuliere klant met dit BSN is
    private boolean doesBsnExist(Particulier particulier, Model model) {
        if (customerService.doesBsnExist(particulier.getBSN())) {
            model.addAttribute("fieldExists", true);
            return true;
        }
        model.addAttribute("fieldExists", false);
        return false;
    }
}
