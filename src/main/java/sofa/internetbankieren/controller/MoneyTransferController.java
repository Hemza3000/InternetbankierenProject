package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SessionAttributes("ingelogde")

public class MoneyTransferController {
    private MoneyTransferService moneyTransferService;
    private MoneyTransferBackingbean moneyTransferBackingbean;

    TransactieDAO transactieDAO;
    PriverekeningDAO priverekeningDAO;
    BedrijfsrekeningDAO bedrijfsrekeningDAO;

    public MoneyTransferController() {
        this.moneyTransferService = moneyTransferService;
        this.moneyTransferBackingbean = moneyTransferBackingbean;
    }

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
