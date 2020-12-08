package sofa.internetbankieren.model;

/**
 * @author WichertTjerkstra 7 dec aangemaakt
 */

public class Bedrijfsrekening extends Rekening {

    private Particulier contactpersoon;
    private Bedrijf rekeninghouder;

    public Bedrijfsrekening(Particulier contactpersoon, Bedrijf rekeninghouder) {
        this.contactpersoon = contactpersoon;
        this.rekeninghouder = rekeninghouder;
    }

    public Bedrijfsrekening(int idRekening, String IBAN, double saldo, Particulier contactpersoon, Bedrijf rekeninghouder) {
        super(idRekening, IBAN, saldo);
        this.contactpersoon = contactpersoon;
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
