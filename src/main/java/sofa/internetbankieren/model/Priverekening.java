package sofa.internetbankieren.model;

import sofa.internetbankieren.repository.TransactieDAO;

import java.util.List;

/**
 * @WichertTjerkstra 7 dec aangemaakt
 * review: Wendy Ellens, 8 dec
 */

public class Priverekening extends Rekening{

    private Particulier rekeninghouder;

    public Priverekening() { super(); }

    public Priverekening(int idRekening, String IBAN, double saldo, List<Integer> transactieIDs,
                         TransactieDAO transactieDAO, Particulier rekeninghouder) {
        super(idRekening, IBAN, saldo, transactieIDs, transactieDAO);
        this.rekeninghouder = rekeninghouder;
    }

    public Particulier getRekeninghouder() {
        return rekeninghouder;
    }

    public void setRekeninghouder(Particulier rekeninghouder) {
        this.rekeninghouder = rekeninghouder;
    }

    @Override
    public List<Transactie> getTransacties() {
        return super.getTransactieDAO().getAllByIDPriverekening(super.getIdRekening());
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
