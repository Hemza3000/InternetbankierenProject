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
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.repository.ParticulierDAO;

import javax.servlet.http.HttpSession;
import java.util.Date;

@SessionAttributes("particulier")
@Controller
public class RegisterPageController {

    ParticulierDAO particulierDAO;

    public RegisterPageController(ParticulierDAO particulierDAO) {
        super();
        this.particulierDAO = particulierDAO;
    }

    @GetMapping("/register")
    public String registerHandler() {
        return "register_page_1";
    }

    @PostMapping("/register_Zakelijk_Particulier")
    public String choiceHanlder(@RequestParam(name="zakelijkOfParticulier") int value, Model model){
        if (value == 0 ) {
            model.addAttribute("particulier", new Particulier());
            return "register_page_2_particulier";
        }
        else if (value == 1) {
            model.addAttribute("bedrijf", new Bedrijf());
            return "register_page_2_zakelijk";
        }
        return null;
    }

    @PostMapping("/register_particulier")
    public String newParticulierHandler(
            @RequestParam(name="First_name") String voornaam,
            @RequestParam(name="Prefix", required = false) String voorvoegsels,
            @RequestParam(name="Last_name") String achternaam,
            @RequestParam(name="Birthday") @DateTimeFormat(pattern="yyyy-MM-dd") Date geboortedatum,
            @RequestParam(name="BSN") int BSN,
            @RequestParam(name="Street") String straatnaam,
            @RequestParam(name="House_number") int huisnummer,
            @RequestParam(name="Postal_code") String postcode,
            @RequestParam(name="City") String woonplaats,
            Model model) {
        Particulier newParticulier = new Particulier(voornaam, voorvoegsels, achternaam,
                geboortedatum, BSN, straatnaam, huisnummer, postcode, woonplaats);
        model.addAttribute("particulier", newParticulier);
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
        model.addAttribute("bedrijf", newBedrijf);
        return "confirmationBedrijf";
    }

    @PostMapping("/confirmParticulier")
    public String confirmHandler(@ModelAttribute(name="particulier") Particulier confirmedMember, Model model)
    {
        model.addAttribute("particulier", confirmedMember);
        return "register_page_3";
    }

    @PostMapping("/confirmBedrijf")
    public String confirmBedrijfHandler(@ModelAttribute(name="bedrijf") Bedrijf confirmedMember, Model model)
    {
        model.addAttribute("bedrijf", confirmedMember);
        return "register_page_3";
    }

    // todo Ook laten werken voor een Bedrijf
    @PostMapping("/confirm")
    public String confirm(@RequestParam String user_name,
                          @RequestParam String password,
                          HttpSession httpSession){
        Particulier particulier = (Particulier) httpSession.getAttribute("particulier");
        particulier.setGebruikersnaam(user_name);
        particulier.setWachtwoord(password);
        System.out.println(particulier);  // TODO <- onnodig
        particulierDAO.storeOne(particulier);
        return "home";
    }

}
