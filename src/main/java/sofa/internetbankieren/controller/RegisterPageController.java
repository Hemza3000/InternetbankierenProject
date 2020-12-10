package sofa.internetbankieren.controller;

/**
 * @Author Wichert Tjerkstra
 * aangemaakt op 9 dec
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Particulier;

import java.util.Date;

@Controller
public class RegisterPageController {

    public RegisterPageController() {
        super();
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

    /*@PostMapping("/register_particulier")
    public String newParticulierHandler(
            @RequestParam(name="First_name") String voornaam,
            @RequestParam(name="Prefix") String voorvoegsels,
            @RequestParam(name="Last_name") String achternaam,
            @RequestParam(name="Birtday") Date geboortedatum, // TODO parse?!
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
    }*/




}
