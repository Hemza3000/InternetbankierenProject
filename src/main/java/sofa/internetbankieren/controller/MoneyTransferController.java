package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingBean;
import sofa.internetbankieren.backing_bean.RegisterFormPartBackingBean;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.model.Transactie;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;
import sofa.internetbankieren.repository.TransactieDAO;

import java.time.LocalDateTime;

import static javax.swing.text.html.CSS.getAttribute;

@Controller
public class MoneyTransferController {
    TransactieDAO transactieDAO;
    PriverekeningDAO priverekeningDAO;
    BedrijfsrekeningDAO bedrijfsrekeningDAO;

    public MoneyTransferController() {
        super();
    }

    @GetMapping({"/moneyTransfer"})
    public String moneyTransferHandler(Model model) {
        MoneyTransferBackingBean userDummy = new MoneyTransferBackingBean("",0.0,"");
        model.addAttribute("backingBean", userDummy);
        return "moneyTransfer";
    }
    @PostMapping
    public String depositHandler(@ModelAttribute MoneyTransferBackingBean backingBean, Model model) {
        Rekening rekening = (Rekening) model.getAttribute("rekening");
        Rekening tegenrekening = (Rekening) priverekeningDAO.getOneByIban(backingBean.getIBAN());
        Transactie transactie = new Transactie(0, rekening, backingBean.getBedrag(), LocalDateTime.now(),
                backingBean.getOmschrijving(), tegenrekening);
        transactieDAO.storeOne(transactie);
        return "account";
    }
    //todo: nog voor bedrijf aanmaken

}
