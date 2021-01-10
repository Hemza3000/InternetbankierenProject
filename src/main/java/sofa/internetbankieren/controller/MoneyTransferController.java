package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MoneyTransferController {
    public MoneyTransferController() {
        super();
    }

    @GetMapping({"/moneyTransfer"})
    public String startHandler() {
        return "moneyTransfer";
    }
}
