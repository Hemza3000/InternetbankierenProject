package sofa.internetbankieren.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import sofa.internetbankieren.model.Medewerker;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedewerkerDAOTest {

    @Autowired
    MedewerkerDAO medewerkerDAO;

    @Test
    void storeOne() {
        Medewerker Wendy = new Medewerker("Wendy", "Ellens", Medewerker.Rol.ACCOUNT_MANAGER);
        medewerkerDAO.storeOne(Wendy);
        assertNotEquals(0, Wendy.getPersooneelsnummer());
    }

    @Test
    void getAll() {
    }

    @Test
    void getOneByID() {
    }

    @Test
    void updateOne() {
    }

    @Test
    void deleteOne() {
    }
}