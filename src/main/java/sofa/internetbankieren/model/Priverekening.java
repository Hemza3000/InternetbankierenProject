package sofa.internetbankieren.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @WichertTjerkstra 7 dec aangemaakt
 * review: Wendy Ellens, 8 dec
 */

public class Priverekening extends Rekening{

    private Particulier rekeninghouder;
    private List<Transactie> transactiesHistorie;

    public Priverekening() { super(); }

    public Priverekening(int idRekening, String IBAN, double saldo, Particulier rekeninghouder) {
        super(idRekening, IBAN, saldo);
        this.rekeninghouder = rekeninghouder;
    }

    public Priverekening(String IBAN, double saldo, Particulier rekeninghouder) {
        this(0, IBAN, saldo, rekeninghouder);
    }

    public void voegTransactieToe() {
        if (this.transactiesHistorie == null) {
            transactiesHistorie = new ArrayList<>();
        }
    } // TODO NOG VERDER AF TE MAKEN IN VOLGENDE SPRINT


    public Particulier getRekeninghouder() {
        return rekeninghouder;
    }

    public void setRekeninghouder(Particulier rekeninghouder) {
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

}
