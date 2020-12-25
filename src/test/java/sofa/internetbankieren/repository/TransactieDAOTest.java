package sofa.internetbankieren.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sofa.internetbankieren.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Wendy Ellens
 */
@SpringBootTest
class TransactieDAOTest {

    @Autowired Medewerker medewerker;
    @Autowired MedewerkerDAO medewerkerDAO;
    @Autowired BedrijfDAO bedrijfDAO;
    @Autowired ParticulierDAO particulierDAO;
    @Autowired BedrijfsrekeningDAO bedrijfsrekeningDAO;
    @Autowired PriverekeningDAO priverekeningDAO;
    @Autowired TransactieDAO transactieDAO;

    // Model test objects to store in the database
    Bedrijf bedrijf;
    Particulier particulier;
    Bedrijfsrekening bedrijfsrekening;
    Priverekening priverekening;
    Transactie bijschrijving_bedrijf;
    Transactie afschrijving_particulier;

    // TODO werkt nog niet. Werkte wel met bestaande DB entries (id van bedrijfs-/priverekening vervangen door 1)

    @Test
    void transactieDAOtest() {
        setDBEntries();
        storeDbEntries();

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

        deleteDbEntries();
    }

    private void setDBEntries() {
        MedewerkerDAOTest.setMedewerker(medewerker);
        bedrijf = new Bedrijf(0, "", "", "", 0, "",
                "", "", 0, "", "", medewerker, new ArrayList<>());
        particulier = new Particulier(0, "", "", "", 0,
                "", "", "", "", "", LocalDate.now(), 1,
                new ArrayList<>(), new ArrayList<>());
        bedrijfsrekening = new Bedrijfsrekening(1, "1",
                0, 1, 1);
        priverekening = new Priverekening(1, "1",
                0, 1);
        bijschrijving_bedrijf = new Transactie(0, bedrijfsrekening, true, 1.5, LocalDateTime.now(), "");
        afschrijving_particulier = new Transactie(0, priverekening, false, 1.5, LocalDateTime.now(), "");
    }

    private void storeDbEntries() {
        medewerkerDAO.storeOne(medewerker);
        bedrijfDAO.storeOne(bedrijf);
        particulierDAO.storeOne(particulier);
        bedrijfsrekeningDAO.storeOne(bedrijfsrekening);
        priverekeningDAO.storeOne(priverekening);
        transactieDAO.storeOne(afschrijving_particulier);
    }

    private void deleteDbEntries() {
        medewerkerDAO.deleteOne(medewerker);
        bedrijfDAO.deleteOne(bedrijf);
        particulierDAO.deleteOne(particulier);
        bedrijfsrekeningDAO.deleteOne(bedrijfsrekening);
        priverekeningDAO.deleteOne(priverekening);
        transactieDAO.deleteOne(afschrijving_particulier);
    }

}