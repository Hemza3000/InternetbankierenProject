package sofa.internetbankieren.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sofa.internetbankieren.model.Medewerker;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedewerkerDAOTest {

    @Autowired MedewerkerDAO medewerkerDAO;
    @Autowired Medewerker Wendy;

    @Test
    void medewerkerDAOtest() {
        Wendy.setGebruikersnaam("W");
        Wendy.setWachtwoord("W");
        Wendy.setVoornaam("Wendy");
        Wendy.setAchternaam("Ellens");
        Wendy.setRol(Medewerker.Rol.ACCOUNTMANAGER);

        // test storeOne
//        Medewerker Wendy = new Medewerker(0, "W", "E", "Wendy", "", "Ellens", Medewerker.Rol.ACCOUNTMANAGER, new ArrayList<>(), new BedrijfDAO(new JdbcTemplate()));
        medewerkerDAO.storeOne(Wendy);
        assertNotEquals(0, Wendy.getPersoneelsnummer());

        // todo werkt wel!
        // test getOneByID
        assertNotNull(medewerkerDAO.getOneByID(Wendy.getPersoneelsnummer()));

//        // todo werkt niet! (later verwijderen, alleen als tussenstap voor volgende test)
//        // test getOneByID
//        assertNotNull(medewerkerDAO.getOneByID(2));


//        // test getAll todo werkt niet, want zie boven
//        List<Medewerker> medewerkers = medewerkerDAO.getAll();
//        assertEquals(Wendy.getPersoneelsnummer(), medewerkers.get(medewerkers.size() - 1).getPersoneelsnummer());

        // test updateOne
        Wendy.setRol(Medewerker.Rol.HOOFD_MKB);
        medewerkerDAO.updateOne(Wendy);
        assertEquals(Medewerker.Rol.HOOFD_MKB, medewerkerDAO.getOneByID(Wendy.getPersoneelsnummer()).getRol());

//        // test deleteOne todo werkt niet
//        medewerkerDAO.deleteOne(Wendy);
//        medewerkers = medewerkerDAO.getAll();
//        assertNotEquals(Wendy.getPersoneelsnummer(), medewerkers.get(medewerkers.size() - 1).getPersoneelsnummer());
    }
}
