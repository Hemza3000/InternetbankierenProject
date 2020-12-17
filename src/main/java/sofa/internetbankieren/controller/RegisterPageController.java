package sofa.internetbankieren.controller;

/**
 * @Author Wichert Tjerkstra
 * aangemaakt op 9 dec
 *
 * Aangevuld door Wendy om de zakelijke registratie mogelijk te maken
 */

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.RegisterFormPartBackingBean;
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Klant;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.repository.BedrijfDAO;
import sofa.internetbankieren.repository.MedewerkerDAO;
import sofa.internetbankieren.repository.ParticulierDAO;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.time.LocalDate;

@SessionAttributes("klant")
@Controller
public class RegisterPageController {

    // Zoals aangegeven door de PO, is het hoofd MKB (medewerker 2) altijd de accountmanager.
    public final static int ID_ACCOUNTMANAGER = 2;

    ParticulierDAO particulierDAO;
    BedrijfDAO bedrijfDAO;
    MedewerkerDAO medewerkerDAO;

    public RegisterPageController(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO, MedewerkerDAO medewerkerDAO) {
        super();
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
        this.medewerkerDAO = medewerkerDAO;
    }

    @GetMapping("/register")
    public String registerHandler() {
        return "register_page_1";
    }

    @PostMapping("/register_Zakelijk_Particulier")
    public String choiceHandler(@RequestParam(name="zakelijkOfParticulier") int value, Model model){
        if (value == 0 ) {
            //model.addAttribute("klant", new Particulier());
            //return "register_page_2_particulier";
            model.addAttribute("backingBean", new RegisterFormPartBackingBean());
            return "register/particulier";
        }
        else if (value == 1) {
            model.addAttribute("klant", new Bedrijf());
            return "register_page_2_zakelijk";
        }
        return null;
    }

    @PostMapping("/register_particulier")
    public String newParticulierHandler(Model model, @ModelAttribute(name="backingBean") RegisterFormPartBackingBean dummy) {
        model.addAttribute("backingBean", dummy);
        return "confirmationParticulier";
    }

    /*@PostMapping("/register_particulier")
    public String newParticulierHandler(
            @RequestParam(name="First_name") String voornaam,
            @RequestParam(name="Prefix", required = false) String voorvoegsels,
            @RequestParam(name="Last_name") String achternaam,
            @RequestParam(name="Birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate geboortedatum,
            @RequestParam(name="BSN") int BSN,
            @RequestParam(name="Street") String straatnaam,
            @RequestParam(name="House_number") int huisnummer,
            @RequestParam(name="Postal_code") String postcode,
            @RequestParam(name="City") String woonplaats,
            Model model) {
        Particulier newParticulier = new Particulier(voornaam, voorvoegsels, achternaam,
                geboortedatum, BSN, straatnaam, huisnummer, postcode, woonplaats);
        model.addAttribute("klant", newParticulier);
        System.out.println(newParticulier);
        return "confirmationParticulier";
    }*/

    @PostMapping("/register_zakelijk")
    public String newBedrijfsHandler(
            @RequestParam(name="Company_name") String bedrijfsnaam,
            @RequestParam(name="Branch") String sector,
            @RequestParam(name="KVK_number") int KVKNummer,
            @RequestParam(name="BTW_number") String BTWNummer,
            @RequestParam(name="Street") String straatnaam,
            @RequestParam(name="House_number") int huisnummer,
            @RequestParam(name="Postal_code") String postcode,
            @RequestParam(name="City") String woonplaats,
            Model model) {
        Bedrijf newBedrijf = new Bedrijf(straatnaam, huisnummer, postcode,
                woonplaats, bedrijfsnaam, KVKNummer, sector, BTWNummer, medewerkerDAO.getOneByID(ID_ACCOUNTMANAGER));
        model.addAttribute("klant", newBedrijf);
        return "confirmationBedrijf";
    }

    @PostMapping("/confirmParticulier")
    public String confirmHandler(@ModelAttribute RegisterFormPartBackingBean backingBean) {
        Particulier p = new Particulier(backingBean);
        System.out.println(p);
        return "register_page_3";
    }


/*    @PostMapping("/confirmParticulier")
    public String confirmHandler(@ModelAttribute(name="klant") Particulier confirmedMember, Model model)
    {
        model.addAttribute("klant", confirmedMember);
        return "register_page_3";
    }*/

    @PostMapping("/confirmBedrijf")
    public String confirmBedrijfHandler(@ModelAttribute(name="klant") Bedrijf confirmedMember, Model model)
    {
        model.addAttribute("klant", confirmedMember);
        return "register_page_3";
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam String user_name,
                          @RequestParam String password,
                          Model model){
        Klant klant = (Klant) model.getAttribute("klant");
        klant.setGebruikersnaam(user_name);
        klant.setWachtwoord(password);
        if (klant instanceof Particulier)
            particulierDAO.storeOne((Particulier) klant);
        else
            bedrijfDAO.storeOne((Bedrijf) klant);
        return "register_completed";
    }

}
