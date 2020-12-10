package sofa.internetbankieren.controller;

/**
 * @Author Wichert Tjerkstra
 * aangemaakt op 9 dec
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Particulier;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

@Controller
public class RegisterPageController {

    public RegisterPageController() {
        super();
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
            @RequestParam(name="Prefix") String voorvoegsels,
            @RequestParam(name="Last_name") String achternaam,
            @RequestParam(name="Birthday") Date geboortedatum, // TODO parse?!
            @RequestParam(name="BSN") int BSN,
            @RequestParam(name="Street") String straatnaam,
            @RequestParam(name="House_number") int huisnummer,
            @RequestParam(name="Postal_code") String postcode,
            @RequestParam(name="City") String woonplaats,
            Model model) {
        Particulier newParticulier = new Particulier(voornaam, voorvoegsels, achternaam,
                geboortedatum, BSN, straatnaam, huisnummer, postcode, woonplaats);
        model.addAttribute("particulier", newParticulier);
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
    public String confirmHandler(@ModelAttribute(name="particulier") Particulier confirmedMember)
    {
        return "register_page_3";
    }

    @PostMapping("/confirmBedrijf")
    public String confirmBedrijfHandler(@ModelAttribute(name="bedrijf") Bedrijf confirmedMember)
    {
        return "register_page_3";
    }
}
