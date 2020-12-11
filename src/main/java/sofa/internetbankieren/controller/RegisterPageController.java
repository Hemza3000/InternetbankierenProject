package sofa.internetbankieren.controller;

/**
 * @Author Wichert Tjerkstra
 * aangemaakt op 9 dec
 */

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Klant;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.repository.BedrijfDAO;
import sofa.internetbankieren.repository.ParticulierDAO;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@SessionAttributes("klant")
@Controller
public class RegisterPageController {

    ParticulierDAO particulierDAO;
    BedrijfDAO bedrijfDAO;

    public RegisterPageController(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO) {
        super();
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
    }

    @GetMapping("/register")
    public String registerHandler() {
        return "register_page_1";
    }

    @PostMapping("/register_Zakelijk_Particulier")
    public String choiceHanlder(@RequestParam(name="zakelijkOfParticulier") int value, Model model){
        if (value == 0 ) {
            model.addAttribute("klant", new Particulier());
            return "register_page_2_particulier";
        }
        else if (value == 1) {
            model.addAttribute("klant", new Bedrijf());
            return "register_page_2_zakelijk";
        }
        return null;
    }

    @PostMapping("/register_particulier")
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
    }

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
                woonplaats, bedrijfsnaam, KVKNummer, sector, BTWNummer);
        model.addAttribute("klant", newBedrijf);
        return "confirmationBedrijf";
    }

    @PostMapping("/confirmParticulier")
    public String confirmHandler(@ModelAttribute(name="klant") Particulier confirmedMember, Model model)
    {
        model.addAttribute("klant", confirmedMember);
        return "register_page_3";
    }

    @PostMapping("/confirmBedrijf")
    public String confirmBedrijfHandler(@ModelAttribute(name="klant") Bedrijf confirmedMember, Model model)
    {
        model.addAttribute("klant", confirmedMember);
        return "register_page_3";
    }

    @PostMapping("/confirm")
    public String confirm(@RequestParam String user_name,
                          @RequestParam String password,
                          HttpSession httpSession){
        Klant klant = (Klant) httpSession.getAttribute("klant");
        klant.setGebruikersnaam(user_name);
        klant.setWachtwoord(password);
        if (klant instanceof Particulier)
            particulierDAO.storeOne((Particulier) klant);
        else
            bedrijfDAO.storeOne((Bedrijf) klant);
        return "register_completed";
    }

}
