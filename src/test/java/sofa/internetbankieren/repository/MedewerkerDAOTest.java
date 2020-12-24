package sofa.internetbankieren.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sofa.internetbankieren.model.Medewerker;

import java.util.ArrayList;
import java.util.List;

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
        Wendy.setBedrijfIDs(new ArrayList<>());

        // test storeOne by checking whether personeelsnummer has been set by autoincrement
//        Medewerker Wendy = new Medewerker(0, "W", "E", "Wendy", "", "Ellens", Medewerker.Rol.ACCOUNTMANAGER,
//        new ArrayList<>(), new BedrijfDAO(new JdbcTemplate()));
        medewerkerDAO.storeOne(Wendy);
        int generated_personeelsnr = Wendy.getPersoneelsnummer();
        System.out.println(generated_personeelsnr);
        assertNotEquals(0, generated_personeelsnr);

        // test getOneByID by checking whether there is an entry with the generated personeelsnummer
        System.out.println(medewerkerDAO.getOneByID(generated_personeelsnr));
        assertNotNull(medewerkerDAO.getOneByID(generated_personeelsnr));

        // test getAll by checking whether the last in the list has the generated personeelsnummer
        List<Medewerker> medewerkers = medewerkerDAO.getAll();
        System.out.println(medewerkers.get(medewerkers.size() - 1).getPersoneelsnummer());
        assertEquals(generated_personeelsnr, medewerkers.get(medewerkers.size() - 1).getPersoneelsnummer());

        // test updateOne by altering the rol of the newly stored entry
        Wendy.setRol(Medewerker.Rol.HOOFD_MKB);
        medewerkerDAO.updateOne(Wendy);
        assertEquals(Medewerker.Rol.HOOFD_MKB, medewerkerDAO.getOneByID(generated_personeelsnr).getRol());

//        // test deleteOne
        medewerkerDAO.deleteOne(Wendy);
        medewerkers = medewerkerDAO.getAll();
        assertNotEquals(Wendy.getPersoneelsnummer(), medewerkers.get(medewerkers.size() - 1).getPersoneelsnummer());
    }
}
