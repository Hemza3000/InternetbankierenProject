package sofa.internetbankieren.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sofa.internetbankieren.backing_bean.LoginFormBackingBean;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.BedrijfDAO;
import sofa.internetbankieren.repository.ParticulierDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;
import sofa.internetbankieren.repository.TransactieDAO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
@SessionAttributes("ingelogde")
public class LoginController {
    private final ParticulierDAO particulierDAO;
    private final BedrijfDAO bedrijfDAO;
    private final PriverekeningDAO priverekeningDAO;
    private final TransactieDAO transactieDAO;
    private String newIBAN;

    public LoginController(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO, PriverekeningDAO priverekeningDAO, TransactieDAO transactieDAO) {
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
        this.priverekeningDAO = priverekeningDAO;
        this.transactieDAO = transactieDAO;
    }

    @GetMapping("/login")
    public String inlogHandler(Model model) {
      LoginFormBackingBean userDummy = new LoginFormBackingBean("", "");
      model.addAttribute("backingBean", userDummy);
      return "login";
    }

    @PostMapping("/overzicht")
    public String postInlogForm(Model model, @ModelAttribute LoginFormBackingBean dummy) {
        List<Particulier> particuliereklanten =
            particulierDAO.getOneByGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());
        List<Bedrijf> bedrijfsklanten =
                bedrijfDAO.getOneByGebruikersnaamWachtwoord(dummy.getUserName(), dummy.getPassword());
        List<Klant> alleklanten = new ArrayList<>();
        alleklanten.addAll(particuliereklanten);
        alleklanten.addAll(bedrijfsklanten);
        if(alleklanten.size() == 0){ // Geen klant met deze inloggegevens
            return "foutingelogd";
        }
        else{
        model.addAttribute("ingelogde", alleklanten.get(0));
            return "overview";
        }
    }

    @GetMapping("/overzicht")
    public String getInlogForm() {
            return "overview";
    }

    @GetMapping("/newAccountNumberPage")
    public String newAccountNumberPageHandler(Model model, @ModelAttribute(name="ingelogde") Klant ingelogde){
        newIBAN = createRandomIBAN();
        model.addAttribute("IBAN", newIBAN);
        model.addAttribute("ingelogde", ingelogde);
        return "account/newAccountNumber";
    }

    @PostMapping("/formNewAccountNumber")
    public String form(Model model, @ModelAttribute(name="ingelogde") Klant ingelogde){
        if (ingelogde instanceof Particulier) {
            Priverekening priverekening = new Priverekening(0, newIBAN, transactieDAO, (Particulier) ingelogde);
            priverekeningDAO.storeOne(priverekening);
        }
        return "overview";
    }

    public String createRandomIBAN() {
        int controlenummer = (int) (Math.random() * 100);
        int rekeningnummer = (int) (Math.random() * 10000000);
        String result = "NL" + controlenummer + "SOFA000" + rekeningnummer;
        // controle of IBAN al bestaat
        while (!checkUniqueIBAN(result)) {
            result = "NL" + controlenummer + "SOFA000" + rekeningnummer;
        }
        return result;
    }

    public boolean checkUniqueIBAN(String IBAN){
        List<Priverekening> priverekeningList = priverekeningDAO.getOneByIban(IBAN);
        if (priverekeningList.isEmpty()){
            return true;
        } else
            return false;
    }
}
