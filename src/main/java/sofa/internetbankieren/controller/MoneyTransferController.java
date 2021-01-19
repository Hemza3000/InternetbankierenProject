package sofa.internetbankieren.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;
import sofa.internetbankieren.repository.TransactieDAO;
import sofa.internetbankieren.service.AccountService;
import sofa.internetbankieren.service.MoneyTransferService;
//import sofa.internetbankieren.service.MoneyTransferService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.List;

@Controller
@SessionAttributes({"ingelogde", "rekening"})

public class MoneyTransferController {

    private TransactieDAO transactieDAO;
    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private MoneyTransferService moneyTransferService;
    private AccountService accountService;
    private static final int MAX_TRANSACTIES = 10;

    public MoneyTransferController(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO, TransactieDAO transactieDAO, MoneyTransferService moneyTransferService, AccountService accountService) {
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.transactieDAO = transactieDAO;
        this.moneyTransferService = moneyTransferService;
        this.accountService = accountService;
    }

    @GetMapping({"/moneyTransfer"})
    public String moneyTransferHandler(Model model, @ModelAttribute (name="ingelogde") Klant ingelogde, @ModelAttribute (name="rekening") Rekening rekening) {
        MoneyTransferBackingBean moneyTransferBackingbean = new MoneyTransferBackingBean(0,"", "");
        model.addAttribute("MoneyTransferBackingbean", moneyTransferBackingbean);
        return "moneyTransfer";
    }

    @PostMapping("/moneyTransfer")
    public String moneyTransferHandler2(@ModelAttribute MoneyTransferBackingBean backingbean, Model model) {

        Rekening tegenrekening = accountService.getRekeningbyIban(backingbean.getTegenrekening());
        Rekening mijnRekening = (Rekening) model.getAttribute("rekening");
        double bedrag = backingbean.getBedrag();
        double eigenSaldo = mijnRekening.getSaldo();
        double tegenrekeningSaldo = tegenrekening.getSaldo();
        Transactie nieuweTransactie = new Transactie(0, mijnRekening, bedrag, LocalDateTime.now(), backingbean.getOmschrijving(), tegenrekening);

        if (moneyTransferService.validatieSaldo(mijnRekening, bedrag)) {
            mijnRekening.setSaldo(eigenSaldo - bedrag);
            tegenrekening.setSaldo(tegenrekeningSaldo + bedrag);

            if (mijnRekening instanceof Bedrijfsrekening) {
                bedrijfsrekeningDAO.updateOne((Bedrijfsrekening) mijnRekening); }
            if (mijnRekening instanceof Priverekening) {
                priverekeningDAO.updateOne((Priverekening) mijnRekening); }
            if (tegenrekening instanceof Priverekening) {
                priverekeningDAO.updateOne((Priverekening) tegenrekening); }
            if (tegenrekening instanceof Bedrijfsrekening) {
                bedrijfsrekeningDAO.updateOne((Bedrijfsrekening) tegenrekening); }

            transactieDAO.storeOne(nieuweTransactie);
        }
        List<Transactie> transacties = mijnRekening.getTransacties();
        Collections.sort(transacties, Collections.reverseOrder());
        if (transacties.size() > 0)
            transacties = transacties.subList(0, Math.min(transacties.size(), MAX_TRANSACTIES));
        model.addAttribute("transacties", transacties);
        return "account/account";
    }
}
    //todo: nog voor bedrijf aanmaken



