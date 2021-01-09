package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Wichert Tjekrstra
 *
 */

@Controller
@SessionAttributes({"klant", "particulier"})
public class NewAccountNumberController {

    public NewAccountNumberController() {
    }

    @GetMapping("/newAccountNumberPage")
    public String newAccountNumberPageHandler(){
        return "account/newAccountNumber";
    }
}
