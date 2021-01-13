package sofa.internetbankieren.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingBean;
import sofa.internetbankieren.model.Klant;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.model.Transactie;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;
import sofa.internetbankieren.repository.TransactieDAO;
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

    public MoneyTransferController(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO) {
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
    public String moneyTransferHandler2(@ModelAttribute MoneyTransferBackingBean backingbean, Model model, @RequestParam String tegenrekeningIban, @RequestParam double bedrag, @RequestParam String omschrijving){

        Rekening mijnRekening = (Rekening) model.getAttribute("rekening");
        Rekening tegenrekening = priverekeningDAO.getOneByIban(tegenrekeningIban);
        double eigenSaldo = mijnRekening.getSaldo();
        double tegenrekeningSaldo = tegenrekening.getSaldo();

        nieuweTransactie = new Transactie(0, mijnRekening, bedrag,LocalDateTime.now(), omschrijving, tegenrekening);
        System.out.println(nieuweTransactie);

        if (validatieSaldo(mijnRekening, bedrag, tegenrekening)){
            double overtemaken = eigenSaldo - bedrag;

            mijnRekening.setSaldo(overtemaken);
            System.out.println(overtemaken);
            System.out.println(eigenSaldo);
            tegenrekening.setSaldo(tegenrekeningSaldo+ nieuweTransactie.getBedrag());
            transactieDAO.storeOne(nieuweTransactie);
            System.out.println(eigenSaldo);
            System.out.println(tegenrekeningSaldo);
        } else {
            System.out.println("test");
        }
        System.out.println(nieuweTransactie + "hallo ik ben hemza");
        return "account";
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
