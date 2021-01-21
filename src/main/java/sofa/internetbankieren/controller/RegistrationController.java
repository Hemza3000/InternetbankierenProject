package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.backing_bean.RegisterFormPartBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;
import sofa.internetbankieren.service.RegisterService;

/**
 * @author Wichert Tjerkstra (particulier), Wendy Ellens (bedrijf)
 * aangemaakt op 9 dec
 *
 * Handelt de registratie van (particuliere/zakelijke) klanten af in 4 stappen:
 * 1. Keuze particulier/bedrijf
 * 2. Invoer klantgegevens
 * 3. Eventuele wijzigingen klantgegevens na controle door klant
 * 4. Invoer logingegevens
 */
@SessionAttributes({"klant", "ingelogde"})
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

    public RegistrationController(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO, MedewerkerDAO medewerkerDAO,
                                  BedrijfsrekeningDAO bedrijfsrekeningDAO, PriverekeningDAO priverekeningDAO,
                                  RegisterService registerService) {
        super();
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
        this.medewerkerDAO = medewerkerDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.priverekeningDAO = priverekeningDAO;
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String registerHandler() {
        return "register/particulierOfBedrijf";
    }

    // Stap 1: keuze voor doorverwijzen naar zakelijke of particuliere registratiepagina
    @PostMapping("/register_Zakelijk_Particulier")
    public String choiceHandler(@RequestParam(name = "zakelijkOfParticulier") int value, Model model) {
        if (value == 0) {
            model.addAttribute("backingBean", new RegisterFormPartBackingBean());
            return "register/particulier";
        } else if (value == 1) {
            model.addAttribute("klant", new Bedrijf());
            return "register/bedrijf";
        }
        return null;
    }


    // voor bootstrap
    @GetMapping("/particulier")
    public String particulierHandler(Model model){
        model.addAttribute("backingBean", new RegisterFormPartBackingBean());
        return "register/particulier";
    }

    // voor bootstrap
    @GetMapping("/zakelijk")
    public String zakelijkHandler(Model model){
        model.addAttribute("klant", new Bedrijf());
        return "register/bedrijf";
    }


    // Registratie particulier
    // Stap 2: verwerken ingevoerde klantgegevens
    @PostMapping("/register_particulier")
    public String newParticulierHandler(Model model, @ModelAttribute(name = "backingBean") RegisterFormPartBackingBean dummy) {
        model.addAttribute("backingBean", dummy);
        return "register/confirmationParticulier";
    }

    // Stap 3: verwerken eventuele wijzigingen in klantgegevens na controle door klant
    @PostMapping("/confirmParticulier")
    public String confirmHandler(@ModelAttribute RegisterFormPartBackingBean backingBean, Model model) {
        Particulier p = new Particulier(backingBean, bedrijfsrekeningDAO, priverekeningDAO);
        model.addAttribute("klant", p);
        LoginFormBackingBean usernameForm = new LoginFormBackingBean("","");
        model.addAttribute("doesExist", false);
        model.addAttribute("usernameForm", usernameForm);
        return "register/registerUsername";
    }

    // Stap 4: verwerken ingevoerde logingegevens
    @PostMapping("/usernameForm")
    public String confirm(Model model, @ModelAttribute LoginFormBackingBean usernameForm){
        Klant klant = (Klant) model.getAttribute("klant");
        // validatie voor unieke gebruikersnaam
        if (!registerService.checkUniqueUsername(usernameForm.getUserName())) {
            model.addAttribute("usernameForm", new LoginFormBackingBean("", ""));
            model.addAttribute("doesExist", true);
            return "register/registerUsername";
        }
        klant.setGebruikersnaam(usernameForm.getUserName());
        klant.setWachtwoord(usernameForm.getPassword());
        if (klant instanceof Particulier) {
            particulierDAO.storeOne((Particulier) klant);
        } else
            bedrijfDAO.storeOne((Bedrijf) klant);
        model.addAttribute("ingelogde", klant);
        return "register/completed";
    }

    // Registratie bedrijf

    // Stap 2: verwerken ingevoerde klantgegevens
    @PostMapping("/register_zakelijk")
    public String newBedrijfsHandler(@ModelAttribute("klant") Bedrijf bedrijf, Model model) {
        model.addAttribute("klant", bedrijf);
        return "register/confirmationBedrijf";
    }

    // Stap 3: verwerken eventuele wijzigingen in klantgegevens na controle door klant
    @PostMapping("/confirmBedrijf")
    public String confirmBedrijfHandler(@ModelAttribute("klant") Bedrijf bedrijf, Model model) {
        model.addAttribute("klant", bedrijf);
        return "register/registerLogin";
    }

    // Stap 4: verwerken ingevoerde logingegevens
    @PostMapping("/storeLogin")
    public String confirm(@ModelAttribute("klant") Bedrijf bedrijf, Model model) {
        bedrijf.setAccountmanager(medewerkerDAO.getOneByID(ID_ACCOUNTMANAGER));
        bedrijf.setBedrijfsrekeningDAO(bedrijfsrekeningDAO);
        bedrijfDAO.storeOne(bedrijf);
        model.addAttribute("ingelogde", bedrijf);
        return "register/completed";
    }
}
