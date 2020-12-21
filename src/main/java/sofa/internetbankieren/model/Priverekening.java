package sofa.internetbankieren.model;

import org.springframework.jdbc.core.JdbcTemplate;
import sofa.internetbankieren.repository.ParticulierDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @WichertTjerkstra 7 dec aangemaakt
 * review: Wendy Ellens, 8 dec
 */

public class Priverekening extends Rekening{

    private int rekeninghouder;
    private List<Transactie> transactiesHistorie;
    private ParticulierDAO particulierDAO = new ParticulierDAO(new JdbcTemplate());

    public Priverekening() { super(); }

    public Priverekening(int idRekening, String IBAN, double saldo, int rekeninghouder) {
        super(idRekening, IBAN, saldo);
        this.rekeninghouder = rekeninghouder;
    }

    public Priverekening(String IBAN, double saldo, int rekeninghouder) {
        this(0, IBAN, saldo, rekeninghouder);
    }

    public void voegTransactieToe() {
        if (this.transactiesHistorie == null) {
            transactiesHistorie = new ArrayList<>();
        }
    } // TODO NOG VERDER AF TE MAKEN IN VOLGENDE SPRINT


    public Particulier getRekeninghouder() {
        return particulierDAO.getOneByID(rekeninghouder);
    }

    public void setRekeninghouder(int rekeninghouder) {
        this.rekeninghouder = rekeninghouder;
    }

    // TODO 7/12 bepalen wat de toString nodig heeft
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(super.toString());
        result.append(" Rekeninghouder: " + rekeninghouder);
        return result.toString();
    }

    @Override
    public List<Transactie> getTransactiesHistorie() {
        return transactiesHistorie;
    }
}
