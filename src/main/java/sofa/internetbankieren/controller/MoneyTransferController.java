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
//import sofa.internetbankieren.service.MoneyTransferService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
@SessionAttributes({"ingelogde", "rekening"})

public class MoneyTransferController {

    private TransactieDAO transactieDAO;
    Transactie nieuweTransactie;
    private Transactie transactie;
    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private static final int MAX_TRANSACTIES = 10;

    public MoneyTransferController(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO, TransactieDAO transactieDAO) {
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.transactieDAO = transactieDAO;
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

        //model.addAttribute("ontoereikend", validatieSaldo(mijnRekening, bedrag, tegenrekening));
        if (validatieSaldo(mijnRekening, bedrag, tegenrekening)){
            mijnRekening.setSaldo(eigenSaldo - bedrag);
            tegenrekening.setSaldo(tegenrekeningSaldo + bedrag);
            transactieDAO.storeOne(nieuweTransactie);
            priverekeningDAO.updateOne(mijnRekening);
            priverekeningDAO.updateOne(tegenrekening);
        }
        List<Transactie> transacties = mijnRekening.getTransacties();
        Collections.sort(transacties, Collections.reverseOrder());
        if (transacties.size() > 0)
            transacties = transacties.subList(0, Math.min(transacties.size(), MAX_TRANSACTIES));
        model.addAttribute("transacties", transacties);
        return "account/account";
    }

//    @PostMapping
//    public String depositHandler(@ModelAttribute MoneyTransferBackingBean backingBean, Model model) {
//
//        Rekening rekening = (Rekening) model.getAttribute("rekening");
//        Rekening tegenrekening = (Rekening) priverekeningDAO.getOneByIban(backingBean.getTegenrekening());
//        Transactie transactie = new Transactie(0, rekening, backingBean.getBedrag(), LocalDateTime.now(),
//                backingBean.getOmschrijving(), tegenrekening);
//        transactieDAO.storeOne(transactie);
//        return "account";
//    }

    public boolean validatieSaldo(Rekening mijnRekening, double bedrag, Rekening tegenrekening) {

        double eigenSaldo = mijnRekening.getSaldo();
        double tegenrekeningSaldo = tegenrekening.getSaldo();


        if (mijnRekening.getSaldo() <= bedrag) {
            System.out.println("saldo te laag");
            return false;
        } else {
            System.out.println("voldoende saldo");
            return true;

        }


    }


    //todo: nog voor bedrijf aanmaken

}

