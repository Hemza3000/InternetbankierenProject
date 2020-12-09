package sofa.internetbankieren.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.context.web.WebDelegatingSmartContextLoader;
import sofa.internetbankieren.model.Medewerker;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedewerkerDAOTest {

    @Autowired
    MedewerkerDAO medewerkerDAO;

    @Test
    void medewerkerDAOtest() {
        // test storeOne
        Medewerker Wendy = new Medewerker("Wendy", "Ellens", Medewerker.Rol.ACCOUNT_MANAGER);
        medewerkerDAO.storeOne(Wendy);
        assertNotEquals(0, Wendy.getPersooneelsnummer());

        // test getAll
        List<Medewerker> medewerkers = medewerkerDAO.getAll();
        assertEquals(Wendy.getPersooneelsnummer(), medewerkers.get(medewerkers.size() - 1).getPersooneelsnummer());

        // test getOneByID
        assertNotNull(medewerkerDAO.getOneByID(Wendy.getPersooneelsnummer()));

        // test updateOne
        Wendy.setRol(Medewerker.Rol.HOOFD_MKB);
        medewerkerDAO.updateOne(Wendy);
        assertEquals(Medewerker.Rol.HOOFD_MKB, medewerkerDAO.getOneByID(Wendy.getPersooneelsnummer()).getRol());

        // test deleteOne
        medewerkerDAO.deleteOne(Wendy);
        medewerkers = medewerkerDAO.getAll();
        assertNotEquals(Wendy.getPersooneelsnummer(), medewerkers.get(medewerkers.size() - 1).getPersooneelsnummer());
    }
}