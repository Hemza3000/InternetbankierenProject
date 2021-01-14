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
import sofa.internetbankieren.service.MoneyTransferService;
//import sofa.internetbankieren.service.MoneyTransferService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public String moneyTransferHandler2(@ModelAttribute MoneyTransferBackingBean backingbean, Model model) {

        List<Rekening> rekeningen = new ArrayList<>();
        rekeningen.addAll(priverekeningDAO.getAllByIban(backingbean.getTegenrekening()));
        rekeningen.addAll(bedrijfsrekeningDAO.getAllByIban(backingbean.getTegenrekening()));
        Rekening tegenrekening = rekeningen.get(0);
        Rekening mijnRekening = (Rekening) model.getAttribute("rekening");
        double bedrag = backingbean.getBedrag();
        double eigenSaldo = mijnRekening.getSaldo();
        double tegenrekeningSaldo = tegenrekening.getSaldo();
        nieuweTransactie = new Transactie(0, mijnRekening, bedrag, LocalDateTime.now(), backingbean.getOmschrijving(), tegenrekening);

        if (moneyTransferService.validatieSaldo(mijnRekening, bedrag, tegenrekening)) {
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
        return "account/account";
    }
}

    //todo: nog voor bedrijf aanmaken



