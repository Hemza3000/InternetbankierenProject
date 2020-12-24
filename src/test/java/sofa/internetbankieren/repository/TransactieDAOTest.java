package sofa.internetbankieren.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sofa.internetbankieren.model.Bedrijfsrekening;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.model.Priverekening;
import sofa.internetbankieren.model.Transactie;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Wendy Ellens
 */
@SpringBootTest
class TransactieDAOTest {

    @Autowired TransactieDAO transactieDAO;

    @Test
    void transactieDAOtest() {
        // todo eerst bedrijfs-/priverekening opslaan in DB
        Transactie bijschrijving_bedrijf = new Transactie(0, new Bedrijfsrekening(0, "1",
                0, 1, 1), true, 1.5, LocalDateTime.now(), "");
        Transactie afschrijving_particulier = new Transactie(0, new Priverekening(0, "1",
                0, 1), false, 1.5, LocalDateTime.now(), "");

        // test storeOne by checking whether IdTransactie has been set by autoincrement
        transactieDAO.storeOne(bijschrijving_bedrijf);
        int generatedIdBedrijf = bijschrijving_bedrijf.getIdTransactie();
        assertNotEquals(0, generatedIdBedrijf);

        // test getOneByID by checking whether there is an entry with the generated ID
        assertNotNull(transactieDAO.getOneByID(generatedIdBedrijf));

        // test getAll by checking whether the last in the list has the generated ID
        List<Transactie> transacties = transactieDAO.getAll();
        assertEquals(generatedIdBedrijf, transacties.get(transacties.size() - 1).getIdTransactie());

        // test getAllByIDBedrijfsrekening by checking whether the last in the list has the generated ID
        List<Transactie> bedrijfstransacties = transactieDAO.getAllByIDBedrijfsrekening(
                ((Bedrijfsrekening)bijschrijving_bedrijf.getRekening()).getIdRekening());
        assertEquals(generatedIdBedrijf, bedrijfstransacties.get(bedrijfstransacties.size() - 1).getIdTransactie());

        // test getAllByIDPriverekening by checking whether the last in the list has the generated IdParticulier
        transactieDAO.storeOne(afschrijving_particulier);
        int generatedIdParticulier = afschrijving_particulier.getIdTransactie();
        List<Transactie> privetransacties = transactieDAO.getAllByIDPriverekening(
                ((Priverekening)afschrijving_particulier.getRekening()).getIdRekening());
        assertEquals(generatedIdParticulier, privetransacties.get(privetransacties.size() - 1).getIdTransactie());

        // test updateOne by altering the omschrijving of one of the stored entries
        bijschrijving_bedrijf.setOmschrijving("Bijschrijving");
        transactieDAO.updateOne(bijschrijving_bedrijf);
        assertEquals("Bijschrijving", transactieDAO.getOneByID(generatedIdBedrijf).getOmschrijving());

        // test deleteOne by checking whether the last entry does not have the generated ID anymore
        transactieDAO.deleteOne(bijschrijving_bedrijf);
        transacties = transactieDAO.getAll();
        assertNotEquals(generatedIdBedrijf, transacties.get(transacties.size() - 1).getIdTransactie());
    }

}