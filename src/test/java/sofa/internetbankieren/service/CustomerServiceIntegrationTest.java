package sofa.internetbankieren.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Wendy Ellens
 */
@SpringBootTest
class CustomerServiceIntegrationTest {

    private final CustomerService customerService;

    @Autowired MedewerkerDAO medewerkerDAO;
    @Autowired ParticulierDAO particulierDAO;
    @Autowired BedrijfDAO bedrijfDAO;

    // Model test objects to store in the database
    Medewerker medewerker;
    Bedrijf bedrijf;
    Particulier particulier;

    @Autowired
    public CustomerServiceIntegrationTest(CustomerService customerService) {
        super();
        this.customerService = customerService;
    }

    @Test
    void doesUsernameExist1() {
        setDBEntries();
        storeDbEntries();
        assertTrue(customerService.doesUsernameExist("gebruikersnaam particulier"));
        deleteDbEntries();
    }

    @Test
    void doesUsernameExist2() {
        setDBEntries();
        storeDbEntries();
        assertTrue(customerService.doesUsernameExist("gebruikersnaam bedrijf"));
        deleteDbEntries();
    }

    @Test
    void doesUsernameExist3() {
        setDBEntries();
        storeDbEntries();
        assertFalse(customerService.doesUsernameExist("onbestaande gebruikersnaam"));
        deleteDbEntries();
    }

    @Test
    void doesBsnExist() {
    }

    @Test
    void storeCustomer() {
    }

    @Test
    void changeCustomer() {
    }

    @Test
    void getKlantenbyGebruikersnaamWachtwoord() {
    }

    private void setDBEntries() {
        medewerker = new Medewerker("Voornaam", "Achternaam", null);
        particulier = new Particulier(0, "gebruikersnaam particulier", "", "", 0,
                "", "", "", "", "", LocalDate.now(), 1,
                new ArrayList<>(), new ArrayList<>(), null, null);
        bedrijf = new Bedrijf(0, "gebruikersnaam bedrijf", "", "", 0, "",
                "", "", 0, Sector.ICT, "", medewerker, new ArrayList<>(),
                null);
    }

    private void storeDbEntries() {
        medewerkerDAO.storeOne(medewerker);
        particulierDAO.storeOne(particulier);
        bedrijfDAO.storeOne(bedrijf);
    }

    private void deleteDbEntries() {
        bedrijfDAO.deleteOne(bedrijf);
        particulierDAO.deleteOne(particulier);
        medewerkerDAO.deleteOne(medewerker);
    }
}