package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;
import sofa.internetbankieren.service.AccountService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wendy Ellens
 */
@Controller
@SessionAttributes("rekening")
public class AccountController {

    private final PriverekeningDAO priverekeningDAO;
    private final BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private final AccountService accountService;

    public AccountController(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO,
                             AccountService accountService) {
        super();
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.accountService = accountService;
    }

    @GetMapping("/rekening/{IBAN}")
    public String accountHandler(Model model, @PathVariable("IBAN") String iban) {

        // Rekening opzoeken o.b.v. IBAN
        List<Rekening> rekeningen = new ArrayList<>();
        rekeningen.addAll(priverekeningDAO.getAllByIban(iban));
        rekeningen.addAll(bedrijfsrekeningDAO.getAllByIban(iban));
        Rekening rekening = rekeningen.get(0);

        model.addAttribute("rekening", rekening);
        model.addAttribute("transacties", accountService.toonTransacties(rekening));
        model.addAttribute("nu", LocalDateTime.now());

        return "account/account";
    }
}
