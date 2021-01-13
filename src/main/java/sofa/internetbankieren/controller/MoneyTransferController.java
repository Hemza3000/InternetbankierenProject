package sofa.internetbankieren.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingbean;
import sofa.internetbankieren.service.MoneyTransferService;


@Controller
@SessionAttributes("ingelogde")

public class MoneyTransferController {
    private MoneyTransferService moneyTransferService;
    private MoneyTransferBackingbean moneyTransferBackingbean;

    public MoneyTransferController() {
        this.moneyTransferService = moneyTransferService;
        this.moneyTransferBackingbean = moneyTransferBackingbean;
    }

    //todo kloppen de parameters op regel 25??

    @GetMapping({"/moneyTransfer"})
    public String moneyTransferHandler(Model model) {
        MoneyTransferBackingbean moneyTransferBackingbean = new MoneyTransferBackingbean(0,"", "");
        model.addAttribute("MoneyTransferBackingbean", moneyTransferBackingbean);
        return "moneyTransfer";
    }

    @PostMapping("/moneyTransfer")
    public String moneyTransferHandler2(@ModelAttribute MoneyTransferController backingbean, Model model){
        moneyTransferService.deposit("", moneyTransferBackingbean.getBedrag(), moneyTransferBackingbean.getTegenrekening(), moneyTransferBackingbean.getOmschrijving());
        return "overview";
    }
}







