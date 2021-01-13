package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingBean;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.model.Transactie;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;
import sofa.internetbankieren.repository.TransactieDAO;
import sofa.internetbankieren.service.MoneyTransferService;

import java.time.LocalDateTime;

@Controller
@SessionAttributes("ingelogde")

public class MoneyTransferController {
    private MoneyTransferService moneyTransferService;
    private MoneyTransferBackingBean moneyTransferBackingbean;

    TransactieDAO transactieDAO;
    PriverekeningDAO priverekeningDAO;
    BedrijfsrekeningDAO bedrijfsrekeningDAO;

    public MoneyTransferController() {
        this.moneyTransferService = moneyTransferService;
        this.moneyTransferBackingbean = moneyTransferBackingbean;
    }

    @GetMapping({"/moneyTransfer"})
    public String moneyTransferHandler(Model model) {
        MoneyTransferBackingBean moneyTransferBackingbean = new MoneyTransferBackingBean(0,"", "");
        model.addAttribute("MoneyTransferBackingbean", moneyTransferBackingbean);
        return "moneyTransfer";
    }

    @PostMapping("/moneyTransfer")
    public String moneyTransferHandler2(@ModelAttribute MoneyTransferController backingbean, Model model){
        moneyTransferService.deposit("", moneyTransferBackingbean.getBedrag(), moneyTransferBackingbean.getTegenrekening(), moneyTransferBackingbean.getOmschrijving());
        return "overview";
    }
    @PostMapping
    public String depositHandler(@ModelAttribute MoneyTransferBackingBean backingBean, Model model) {
        Rekening rekening = (Rekening) model.getAttribute("rekening");
        Rekening tegenrekening = (Rekening) priverekeningDAO.getOneByIban(backingBean.getTegenrekening());
        Transactie transactie = new Transactie(0, rekening, backingBean.getBedrag(), LocalDateTime.now(),
                backingBean.getOmschrijving(), tegenrekening);
        transactieDAO.storeOne(transactie);
        return "account";
    }
    //todo: nog voor bedrijf aanmaken

}
