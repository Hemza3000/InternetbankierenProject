package sofa.internetbankieren.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.service.AccountService;
import sofa.internetbankieren.service.MoneyTransferService;
import java.time.LocalDateTime;


/**
 * @author Taco Jongkind & Hemza Lasri
 */
@Controller
@SessionAttributes({"klant", "rekening"})

public class MoneyTransferController {

    private MoneyTransferService moneyTransferService;
    private AccountService accountService;

    public MoneyTransferController(MoneyTransferService moneyTransferService, AccountService accountService) {
        this.moneyTransferService = moneyTransferService;
        this.accountService = accountService;
    }

    @GetMapping({"/moneyTransfer"})
    public String moneyTransferHandler(Model model, @ModelAttribute(name = "klant") Klant ingelogde, @ModelAttribute(name = "rekening") Rekening rekening) {
        MoneyTransferBackingBean moneyTransferBackingbean = new MoneyTransferBackingBean(0, "", "");
        model.addAttribute("MoneyTransferBackingbean", moneyTransferBackingbean);
        model.addAttribute("saldoOntoereikend", false);
        model.addAttribute("bedragValidatie", false);
        return "moneyTransfer";
    }

    @PostMapping("/moneyTransfer")
    public String moneyTransferHandler2(@ModelAttribute MoneyTransferBackingBean backingbean, Model model) {

        Rekening tegenrekening = accountService.getRekeningbyIban(backingbean.getTegenrekening());
        Rekening mijnRekening = (Rekening) model.getAttribute("rekening");
        Transactie nieuweTransactie = new Transactie(0, mijnRekening, backingbean.getBedrag(), LocalDateTime.now(), backingbean.getOmschrijving(), tegenrekening);

        //todo veel dubbele code, hoe op te lossen??@#$@$!

        if (!moneyTransferService.validatieBedrag(backingbean.getBedrag())){ // toont melding 'bedrag moet hoger zijn dan 0'
            model.addAttribute("bedragValidatie", true);
            MoneyTransferBackingBean moneyTransferBackingbean = new MoneyTransferBackingBean(0, "", "");
            model.addAttribute("MoneyTransferBackingbean", moneyTransferBackingbean);
            return "moneyTransfer";
        }

        if (!moneyTransferService.validatieSaldo(mijnRekening, tegenrekening, backingbean.getBedrag())) {// toont melding saldo te laag
            model.addAttribute("saldoOntoereikend", true);
            MoneyTransferBackingBean moneyTransferBackingbean = new MoneyTransferBackingBean(0, "", "");
            model.addAttribute("MoneyTransferBackingbean", moneyTransferBackingbean);
            return "moneyTransfer";
        }

        moneyTransferService.slaTransactieOp(nieuweTransactie);
        moneyTransferService.updateRekeningen(mijnRekening, tegenrekening);
        model.addAttribute("transacties", accountService.geefTransactieHistorie(mijnRekening));
        return "account/account";
    }
}




