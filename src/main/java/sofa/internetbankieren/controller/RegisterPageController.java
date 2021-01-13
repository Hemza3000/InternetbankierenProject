package sofa.internetbankieren.controller;

/**
 * @Author Wichert Tjerkstra
 * aangemaakt op 9 dec
 *
 * Aangevuld door Wendy om de zakelijke registratie mogelijk te maken
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.backing_bean.RegisterFormPartBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;
import sofa.internetbankieren.service.AccountService;
import sofa.internetbankieren.service.RegisterService;

import java.util.List;

@Controller
@SessionAttributes({"klant", "particulier"})
public class RegisterPageController {

    // Zoals aangegeven door de PO, is het hoofd MKB (medewerker 2) altijd de accountmanager.
    public final static int ID_ACCOUNTMANAGER = 2;
    private String newIBAN;
    private AccountService accountService;
    private ParticulierDAO particulierDAO;
    private BedrijfDAO bedrijfDAO;
    private MedewerkerDAO medewerkerDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private PriverekeningDAO priverekeningDAO;
    private TransactieDAO transactieDAO;
    private RegisterService registerService;

    public RegisterPageController(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO, MedewerkerDAO medewerkerDAO,
                                  BedrijfsrekeningDAO bedrijfsrekeningDAO, PriverekeningDAO priverekeningDAO,
                                  AccountService accountService, TransactieDAO transactieDAO, RegisterService registerService) {
        super();
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
        this.medewerkerDAO = medewerkerDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.priverekeningDAO = priverekeningDAO;
        this.accountService = accountService;
        this.transactieDAO = transactieDAO;
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String registerHandler() {
        return "register_page_1";
    }

    // keuze voor doorverwijzen naar zakelijke of particuliere registratiepagina
    @PostMapping("/register_Zakelijk_Particulier")
    public String choiceHandler(@RequestParam(name = "zakelijkOfParticulier") int value, Model model) {
        if (value == 0) {
            model.addAttribute("backingBean", new RegisterFormPartBackingBean());
            return "register/particulier";
        } else if (value == 1) {
            model.addAttribute("klant", new Bedrijf());
            return "register_page_2_zakelijk";
        }
        return null;
    }

    // registratie particulier
    @PostMapping("/register_particulier")
    public String newParticulierHandler(Model model, @ModelAttribute(name = "backingBean") RegisterFormPartBackingBean dummy) {
        model.addAttribute("backingBean", dummy);
        return "confirmationParticulier";
    }

    @PostMapping("/confirmParticulier")
    public String confirmHandler(@ModelAttribute RegisterFormPartBackingBean backingBean, Model model) {
        Particulier p = new Particulier(backingBean, bedrijfsrekeningDAO, priverekeningDAO);
        model.addAttribute("particulier", p);
        LoginFormBackingBean usernameForm = new LoginFormBackingBean("", "");
        model.addAttribute("doesExist", false);
        model.addAttribute("usernameForm", usernameForm);
        return "register/registerUsername";
    }

    @PostMapping("/usernameForm")
    public String confirm(Model model, @ModelAttribute LoginFormBackingBean usernameForm,
                          @ModelAttribute(name = "particulier") Particulier p) {
        Klant klant = (Klant) model.getAttribute("particulier");
        // validatie voor unieke gebruikersnaam
        if (!registerService.checkUniqueUsername(usernameForm.getUserName())) {
            LoginFormBackingBean usernameExist = new LoginFormBackingBean("", "");
            model.addAttribute("usernameForm", usernameExist);
            model.addAttribute("doesExist", true);
            return "register/registerUsername";
        }
        klant.setGebruikersnaam(usernameForm.getUserName());
        klant.setWachtwoord(usernameForm.getPassword());
        if (klant instanceof Particulier) {
            particulierDAO.storeOne((Particulier) klant);
        } else
            bedrijfDAO.storeOne((Bedrijf) klant);
        return "register/register_completed";
    }

    // registratie bedrijf
    @PostMapping("/register_zakelijk")
    public String newBedrijfsHandler(
            @RequestParam(name = "Company_name") String bedrijfsnaam,
            @RequestParam(name = "Branch") String sector,
            @RequestParam(name = "KVK_number") int KVKNummer,
            @RequestParam(name = "BTW_number") String BTWNummer,
            @RequestParam(name = "Street") String straatnaam,
            @RequestParam(name = "House_number") int huisnummer,
            @RequestParam(name = "Postal_code") String postcode,
            @RequestParam(name = "City") String woonplaats,
            Model model) {
        Bedrijf newBedrijf = new Bedrijf(straatnaam, huisnummer, postcode, woonplaats, bedrijfsnaam, KVKNummer, sector,
                BTWNummer, medewerkerDAO.getOneByID(ID_ACCOUNTMANAGER), bedrijfsrekeningDAO);
        model.addAttribute("klant", newBedrijf);
        return "confirmationBedrijf";
    }

    @PostMapping("/confirmBedrijf")
    public String confirmBedrijfHandler(@ModelAttribute(name = "klant") Bedrijf confirmedMember, Model model) {
        model.addAttribute("klant", confirmedMember);
        return "register_page_3";
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam String user_name,
                          @RequestParam String password, Model model) {
        Klant klant = (Klant) model.getAttribute("klant");
        klant.setGebruikersnaam(user_name);
        klant.setWachtwoord(password);
        if (klant instanceof Particulier)
            particulierDAO.storeOne((Particulier) klant);
        else
            bedrijfDAO.storeOne((Bedrijf) klant);
        return "register/register_completed";
    }

    @GetMapping("/RegisterAccountNumber")
    public String RegisterAccountNumberHandlder(Model model, @ModelAttribute(name = "klant") Klant klant) {
        newIBAN = accountService.createRandomIBAN();
        model.addAttribute("klant", klant);
        model.addAttribute("IBAN", newIBAN);
        return "account/RegisterAccountNumber";
    }

    @PostMapping("/formAccountNumber")
    public String formAccountNumberHandlder(Model model, @ModelAttribute(name = "klant") Klant klant, @RequestParam String formContactpersoon) {
        if (klant instanceof Particulier) {
            Priverekening priverekening = new Priverekening(0, newIBAN, transactieDAO, (Particulier) klant);
            priverekeningDAO.storeOne(priverekening);
            // registeren van rekeningnummer
        } else {
            int contactPersoonBSN = Integer.parseInt(formContactpersoon);
            Particulier contactPersoon = particulierDAO.getByBSN(contactPersoonBSN);
            //TODO controle op contactpersoon (op basis van BSN) bestaat)
            Bedrijfsrekening bedrijfsrekening = new Bedrijfsrekening(0, newIBAN, transactieDAO, contactPersoon, (Bedrijf) klant);
            bedrijfsrekeningDAO.storeOne(bedrijfsrekening);
        }
        return "home";
    }
}
