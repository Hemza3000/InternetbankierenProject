package sofa.internetbankieren.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingBean;
import sofa.internetbankieren.model.Klant;
import sofa.internetbankieren.model.Priverekening;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.model.Transactie;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;
import sofa.internetbankieren.repository.TransactieDAO;
import sofa.internetbankieren.service.MoneyTransferService;
//import sofa.internetbankieren.service.MoneyTransferService;

import java.time.LocalDateTime;

@Controller
@SessionAttributes({"ingelogde", "rekening"})

public class MoneyTransferController {

    private TransactieDAO transactieDAO;
    Transactie nieuweTransactie;
    private Transactie transactie;
    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private MoneyTransferService moneyTransferService;

    public MoneyTransferController(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO, TransactieDAO transactieDAO, MoneyTransferService moneyTransferService) {
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.transactieDAO = transactieDAO;
        this.moneyTransferService = moneyTransferService;
    }

    @GetMapping({"/moneyTransfer"})
    public String moneyTransferHandler(Model model, @ModelAttribute (name="ingelogde") Klant ingelogde, @ModelAttribute (name="rekening") Rekening rekening) {
        MoneyTransferBackingBean moneyTransferBackingbean = new MoneyTransferBackingBean(0,"", "");
        model.addAttribute("MoneyTransferBackingbean", moneyTransferBackingbean);
        return "moneyTransfer";
    }

    @PostMapping("/moneyTransfer")
    public String moneyTransferHandler2(@ModelAttribute MoneyTransferBackingBean backingbean, Model model){

        Priverekening mijnRekening = (Priverekening) model.getAttribute("rekening");
        Priverekening tegenrekening = priverekeningDAO.getOneByIban(backingbean.getTegenrekening());
        System.out.println(backingbean);

        double bedrag = backingbean.getBedrag();
        double eigenSaldo = mijnRekening.getSaldo();
        double tegenrekeningSaldo = tegenrekening.getSaldo();

        nieuweTransactie = new Transactie(0, mijnRekening, bedrag,LocalDateTime.now(), backingbean.getOmschrijving(), priverekeningDAO.getOneByIban(backingbean.getTegenrekening()));
        System.out.println(nieuweTransactie);

        if (moneyTransferService.validatieSaldo (mijnRekening, bedrag, tegenrekening)){
            mijnRekening.setSaldo(eigenSaldo - bedrag);
            tegenrekening.setSaldo(tegenrekeningSaldo+ bedrag);
            transactieDAO.storeOne(nieuweTransactie);
            priverekeningDAO.updateOne(mijnRekening);
            priverekeningDAO.updateOne(tegenrekening);
        }
        return "account/account";
    }

}

    //todo: nog voor bedrijf aanmaken



