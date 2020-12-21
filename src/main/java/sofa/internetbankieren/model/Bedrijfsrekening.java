package sofa.internetbankieren.model;

import org.springframework.jdbc.core.JdbcTemplate;
import sofa.internetbankieren.repository.BedrijfDAO;
import sofa.internetbankieren.repository.ParticulierDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WichertTjerkstra 7 dec aangemaakt
 * review: Wendy Ellens, 8 dec
 */

public class Bedrijfsrekening extends Rekening {

    private int contactpersoon; // is een Particulier
    private int rekeninghouder; // is een Bedrijf
    private List<Transactie> transactiesHistorie;

    private ParticulierDAO particulierDAO = new ParticulierDAO(new JdbcTemplate());
    private BedrijfDAO bedrijfDAO = new BedrijfDAO(new JdbcTemplate());

    public Bedrijfsrekening() { super(); }

    public Bedrijfsrekening(int idRekening, String IBAN, double saldo, int contactpersoon, int rekeninghouder) {
        super(idRekening, IBAN, saldo);
        this.contactpersoon = contactpersoon;
        this.rekeninghouder = rekeninghouder;
    }

    public Bedrijfsrekening(String IBAN, double saldo, int contactpersoon, int rekeninghouder) {
        this(0, IBAN, saldo, contactpersoon, rekeninghouder);
    }

    public void voegTransactieToe() {
        if (this.transactiesHistorie == null) {
            transactiesHistorie = new ArrayList<>();
        }
    } // TODO NOG VERDER AF TE MAKEN IN VOLGENDE SPRINT

    public Particulier getContactpersoon() {
        return particulierDAO.getOneByID(contactpersoon);
    }

    public void setContactpersoon(int contactpersoon) {
        this.contactpersoon = contactpersoon;
    }

    public Bedrijf getRekeninghouder() {
        return bedrijfDAO.getOneByID(rekeninghouder);
    }

    public void setRekeninghouder(int rekeninghouder) {
        this.rekeninghouder = rekeninghouder;
    }

    // TODO 7/12 wat hebben we nodig in de toString?
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        result.append(" Rekeninghouder: " + rekeninghouder);
        result.append(" Contactpersoon: " + contactpersoon);
        return result.toString();
    }
}
