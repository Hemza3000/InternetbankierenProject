package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingBean;

@Controller
public class MoneyTransferController {
    public MoneyTransferController() {
        super();
    }

    @GetMapping({"/moneyTransfer"})
    public String startHandler(Model model) {
        MoneyTransferBackingBean userDummy = new MoneyTransferBackingBean("",0.0,"");
        model.addAttribute("backingBean", userDummy);
        return "moneyTransfer";
    }
}
