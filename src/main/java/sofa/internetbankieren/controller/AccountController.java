package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.model.Transactie;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Wendy Ellens
 */
@Controller
@SessionAttributes("rekening")
public class AccountController {

    private static final int MAX_TRANSACTIES = 10; // lengte transactieoverzicht
    private final PriverekeningDAO priverekeningDAO;
    private final BedrijfsrekeningDAO bedrijfsrekeningDAO;

    public AccountController(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO) {
        super();
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
    }

    @GetMapping("/rekening/{IBAN}")
    public String accountHandler(Model model, @PathVariable("IBAN") String iban) {

        // Rekening opzoeken o.b.v. IBAN
        List<Rekening> rekeningen = new ArrayList<>();
        rekeningen.addAll(priverekeningDAO.getOneByIban(iban));
        rekeningen.addAll(bedrijfsrekeningDAO.getOneByIban(iban));
        Rekening rekening = rekeningen.get(0);

        // Lijst maken van laatste maximaal MAX_TRANSACTIES transacties met meeste recente vooraan
        List<Transactie> transacties = rekening.getTransacties();
        Collections.sort(transacties, Collections.reverseOrder());
        if (transacties.size() > 0)
            transacties = transacties.subList(0, Math.min(transacties.size(), MAX_TRANSACTIES));

        model.addAttribute("rekening", rekening);
        model.addAttribute("transacties", transacties);
        model.addAttribute("nu", LocalDateTime.now());

        return "account";
    }
}
