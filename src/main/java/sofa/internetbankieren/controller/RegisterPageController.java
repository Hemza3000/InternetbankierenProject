package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterPageController {

    public RegisterPageController() {
        super();
    }

    @PostMapping("/register_Zakelijk_Particulier")
    public String choiceHanlder(){
        return "register_page_2_particulier";
    }



}
