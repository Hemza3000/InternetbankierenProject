package sofa.internetbankieren.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import sofa.internetbankieren.model.Klant;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.model.Priverekening;
import sofa.internetbankieren.repository.PriverekeningDAO;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@SessionAttributes("gebruiker")
@Controller
public class OverviewController {

    private JdbcTemplate jdbcTemplate;

    public OverviewController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/overview")
    public String overviewHandler(){
        return "overview";
    }

    @PostMapping("/overview")
    public String postOverview(@ModelAttribute(name="gebruiker")Klant gebruiker) {
        System.out.println("test");
        System.out.println(gebruiker.getIdKlant());
        System.out.println("testtest");
        return "overview";
    }





//
//    @GetMapping("/overviewList")
//    public String overviewHandler(HttpSession httpSession){
//        Klant klant = (Klant) httpSession.getAttribute("klant");
//        PriverekeningDAO priverekeningDAO = new PriverekeningDAO(jdbcTemplate);
//        List<Priverekening> overviewList = priverekeningDAO.getAllByRekeninghouder(klant.getIdKlant());
//        klant.getGebruikersnaam();
//        System.out.println("test test");
//        System.out.println(klant.getGebruikersnaam());
//        System.out.println(overviewList);
//        return "overview";
//    }

    // Hoe werkt session; MAW hoe kan ik iets aan de session doorgeven.

    // arraylist met rekeningnummers doorgeven

    // DAO:


//    @GetMapping("/overviewList")
//    public String overviewListHandler(HttpSession httpSession, Model model){
//        Klant klant = (Klant) httpSession.getAttribute("klant");
//        PriverekeningDAO priverekeningDAO = new PriverekeningDAO(jdbcTemplate);
//        List<Priverekening> overviewList = priverekeningDAO.getAllByRekeninghouder(klant.getIdKlant());
//        model.addAttribute("overviewList", overviewList);
//        klant.getGebruikersnaam();
//        System.out.println(klant);
//        return "overview";
//    }
}
