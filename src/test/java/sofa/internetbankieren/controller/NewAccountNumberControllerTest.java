package sofa.internetbankieren.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;
import sofa.internetbankieren.service.AccountService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * @WichertTjerkstra
 */

@WebMvcTest(NewAccountNumberController.class)
class NewAccountNumberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    BedrijfDAO bedrijfDAO;
    @Autowired
    ParticulierDAO particulierDAO;
    @Autowired
    MedewerkerDAO medewerkerDAO;
    @Autowired
    BedrijfsrekeningDAO bedrijfsrekeningDAO;
    @Autowired
    PriverekeningDAO priverekeningDAO;
    @Autowired
    TransactieDAO transactieDAO;

    // Model test objects to store in the database
    Bedrijf bedrijf;
    Medewerker medewerker;
    Particulier particulier;
    Bedrijfsrekening bedrijfsrekening;
    Priverekening priverekening;
    Transactie overboeking_particulier_bedrijf;

    public NewAccountNumberControllerTest(ParticulierDAO particulierDAO) {
        this.particulierDAO = particulierDAO;
    }

    private void setDBEntries() {
        medewerker = new Medewerker("Voornaam", "Achternaam", bedrijfDAO);
        bedrijf = new Bedrijf(0, "", "", "", 0, "",
                "", "", 0, Sector.ICT, "", medewerker, new ArrayList<>(),
                bedrijfsrekeningDAO);
        particulier = new Particulier(0, "", "", "", 0,
                "", "", "", "", "", LocalDate.now(), 1,
                new ArrayList<>(), new ArrayList<>(), bedrijfsrekeningDAO, priverekeningDAO);
        bedrijfsrekening = new Bedrijfsrekening(0, "10", 0, new ArrayList<>(), transactieDAO,
                particulier, bedrijf);
        priverekening = new Priverekening(0, "11", 0, new ArrayList<>(), transactieDAO,
                particulier);
        overboeking_particulier_bedrijf = new Transactie(priverekening, 1.5, LocalDateTime.now(),
                "", bedrijfsrekening);
    }


   /* @Test
    void newAccountNumberPageHandler() {
        MockHttpSession session = new MockHttpSession();
        String newIban = "NL01SOFA0123456789";
        session.setAttribute("IBAN", newIban);
        session.setAttribute("ingelogde", particulier);
        session.setAttribute("isBedrijf", false);
        session.setAttribute("doesExist", true);
        MockHttpServletRequestBuilder getRequest =
                MockMvcRequestBuilders.get("/newAccountNumberPage").session(session);
        try {
            ResultActions response = mockMvc.perform(getRequest);
            response.andDo(print());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void form() {

    }*/
}