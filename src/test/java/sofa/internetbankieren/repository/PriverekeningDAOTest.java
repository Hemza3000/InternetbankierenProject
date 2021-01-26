package sofa.internetbankieren.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import sofa.internetbankieren.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class PriverekeningDAOTest {

    private JdbcTemplate jdbcTemplate;

    @Autowired MedewerkerDAO medewerkerDAO;
    @Autowired BedrijfDAO bedrijfDAO;
    @Autowired ParticulierDAO particulierDAO;
    @Autowired BedrijfsrekeningDAO bedrijfsrekeningDAO;
    @Autowired PriverekeningDAO priverekeningDAO;
    @Autowired TransactieDAO transactieDAO;

    // Model test objects to store in the database
    Medewerker medewerker;
    Bedrijf bedrijf;
    Particulier particulier;
    Bedrijfsrekening bedrijfsrekening;
    Priverekening priverekening;
    Transactie overboeking_particulier_bedrijf;

    @Autowired
    public PriverekeningDAOTest(JdbcTemplate jdbcTemplate, ParticulierDAO particulierDAO, @Lazy TransactieDAO transactieDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.particulierDAO = particulierDAO;
        this.transactieDAO = transactieDAO;
    }

    @Test
    void getOneByID() {

    }

    @Test
    void getAllByIban() {
    }

    @Test
    void getOneByIban() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getAllByRekeninghouder() {
    }

    @Test
    void getAllIDsByRekeninghouder() {
    }

    @Test
    void updateOne() {
    }

    @Test
    void storeOne() {
    }

    @Test
    void deleteOne() {
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

    private void storeDbEntries() {
        medewerkerDAO.storeOne(medewerker);
        System.out.println("Personeelsnummer" + medewerker.getPersoneelsnummer());
        bedrijfDAO.storeOne(bedrijf);
        System.out.println("IdBedrijf" + bedrijf.getIdKlant());
        particulierDAO.storeOne(particulier);
        System.out.println("IdParticulier" + particulier.getIdKlant());
        bedrijfsrekeningDAO.storeOne(bedrijfsrekening);
        System.out.println("IdBedrijfsekening" + bedrijfsrekening.getIdRekening());
        priverekeningDAO.storeOne(priverekening);
        System.out.println("IdPriverekening" + priverekening.getIdRekening());
    }

    private void deleteDbEntries() {
        bedrijfsrekeningDAO.deleteOne(bedrijfsrekening);
        priverekeningDAO.deleteOne(priverekening);
        bedrijfDAO.deleteOne(bedrijf);
        particulierDAO.deleteOne(particulier);
        medewerkerDAO.deleteOne(medewerker);
    }

}