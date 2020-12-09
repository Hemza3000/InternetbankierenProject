package sofa.internetbankieren.model;

/**
 * @WicherTjerkstra 7 dec aangemaakt
 * review: Wendy Ellens, 8 dec
 */

import java.util.ArrayList;
import java.util.List;

abstract class Rekening {

    private int idRekening;
    private String IBAN;
    private double saldo;
    private List<Transactie> transactiesHistorie;

    public Rekening() { super(); }

    public Rekening(int idRekening, String IBAN, double saldo) {
        this.idRekening = idRekening;
        this.IBAN = IBAN;
        this.saldo = saldo;
    }

    public Rekening(String IBAN, double saldo) {
        this( 0, IBAN, saldo);
    }

    public int getIdRekening() {
        return idRekening;
    }

    public void setIdRekening(int idRekening) {
        this.idRekening = idRekening;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Transactie> getTransactiesHistorie() {
        return transactiesHistorie;
    }

    public void setTransactiesHistorie(List<Transactie> transactiesHistorie) {
        this.transactiesHistorie = transactiesHistorie;
    }

    // TODO 7/12 wat hebben we nodig in de toString?
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Rekening: " + IBAN);
        result.append(", Huidige saldo: " + saldo);
        return result.toString();
    }
}
