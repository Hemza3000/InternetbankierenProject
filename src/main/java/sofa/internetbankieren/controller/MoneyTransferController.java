package sofa.internetbankieren.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.service.AccountService;
import sofa.internetbankieren.service.MoneyTransferService;
import java.time.LocalDateTime;


@Controller
@SessionAttributes({"ingelogde", "rekening"})

public class MoneyTransferController {

    private MoneyTransferService moneyTransferService;
    private AccountService accountService;
    private static final int MAX_TRANSACTIES = 10;

    public MoneyTransferController(MoneyTransferService moneyTransferService, AccountService accountService) {
        this.moneyTransferService = moneyTransferService;
        this.accountService = accountService;
    }

    @GetMapping({"/moneyTransfer"})
    public String moneyTransferHandler(Model model, @ModelAttribute(name = "ingelogde") Klant ingelogde, @ModelAttribute(name = "rekening") Rekening rekening) {
        MoneyTransferBackingBean moneyTransferBackingbean = new MoneyTransferBackingBean(0, "", "");
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

        if (moneyTransferService.validatieSaldo(mijnRekening, tegenrekening, bedrag, eigenSaldo, tegenrekeningSaldo)) {
            moneyTransferService.slaTransactieOp(nieuweTransactie);
            moneyTransferService.updateRekeningen(mijnRekening, tegenrekening); }

        //todo deze transactiemethode in een service zetten
//        List<Transactie> transacties = mijnRekening.getTransacties();
//        Collections.sort(transacties, Collections.reverseOrder());
//        if (transacties.size() > 0)
//            transacties = transacties.subList(0, Math.min(transacties.size(), MAX_TRANSACTIES));
//        model.addAttribute("transacties", transacties);
        return "account/account";
    }
}




