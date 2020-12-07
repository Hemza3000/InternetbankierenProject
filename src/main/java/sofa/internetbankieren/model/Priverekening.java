package sofa.internetbankieren.model;

/**
 * @WichertTjerkstra 7 dec aangemaakt
 */

public class Priverekening extends Rekening{

    private Particulier rekeninghouder;

    public Priverekening() {
    }

    public Priverekening(int idRekening, String IBAN, double saldo, Particulier rekeninghouder) {
        super(idRekening, IBAN, saldo);
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

    public Particulier getRekeninghouder() {
        return rekeninghouder;
    }

    public void setRekeninghouder(Particulier rekeninghouder) {
        this.rekeninghouder = rekeninghouder;
    }
}
