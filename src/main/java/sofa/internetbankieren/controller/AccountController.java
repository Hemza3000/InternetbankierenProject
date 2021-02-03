package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.service.AccountService;

import java.time.LocalDateTime;

/**
 * @author Wendy Ellens
 *
 * Geeft de pagina met transacties van de gevraagde rekening.
 */
@Controller
@SessionAttributes("rekening")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        super();
        this.accountService = accountService;
    }

    @GetMapping("/rekening/{IBAN}")
    public String accountHandler(Model model, @PathVariable("IBAN") String iban) {
        Rekening rekening = accountService.getRekeningbyIban(iban);
        model.addAttribute("rekening", rekening);
        model.addAttribute("transacties", accountService.geefTransactieHistorie(rekening));
        model.addAttribute("nu", LocalDateTime.now());
        return "account/account";
    }
}
