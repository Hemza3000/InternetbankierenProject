package sofa.internetbankieren.model;

/**
 * @author WichertTjerkstra 7 dec aangemaakt
 * review: Wendy Ellens, 8 dec
 */

public class Bedrijfsrekening extends Rekening {

    private Particulier contactpersoon;
    private Bedrijf rekeninghouder;

    public Bedrijfsrekening() { super(); }

    public Bedrijfsrekening(int idRekening, String IBAN, double saldo, Particulier contactpersoon, Bedrijf rekeninghouder) {
        super(idRekening, IBAN, saldo);
        this.contactpersoon = contactpersoon;
        this.rekeninghouder = rekeninghouder;
    }

    public Bedrijfsrekening(String IBAN, double saldo, Particulier contactpersoon, Bedrijf rekeninghouder) {
        this(0, IBAN, saldo, contactpersoon, rekeninghouder);
    }

    public Particulier getContactpersoon() {
        return contactpersoon;
    }

    public void setContactpersoon(Particulier contactpersoon) {
        this.contactpersoon = contactpersoon;
    }

    public Bedrijf getRekeninghouder() {
        return rekeninghouder;
    }

    public void setRekeninghouder(Bedrijf rekeninghouder) {
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
