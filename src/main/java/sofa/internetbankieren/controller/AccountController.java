package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wendy Ellens
 */
@Controller
public class AccountController {
    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;

    public AccountController(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO) {
        super();
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
    }

    @GetMapping("/account/{IBAN}")
    public String accountHandler(Model model, @PathVariable("IBAN") String iban) {
        List<Rekening> rekeningen = new ArrayList<>();
        rekeningen.addAll(priverekeningDAO.getOneByIban(iban));
        rekeningen.addAll(bedrijfsrekeningDAO.getOneByIban(iban));
        model.addAttribute("rekening", rekeningen.get(0));
        return "account";
    }
}
