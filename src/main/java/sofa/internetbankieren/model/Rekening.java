package sofa.internetbankieren.model;

/**
 * @WicherTjerkstra 7 dec aangemaakt
 */

import java.util.ArrayList;

abstract class Rekening {

    private int idRekening;
    private String IBAN;
    private double saldo;
    private ArrayList<Transactie> transactiesHistorie;

    public Rekening() {
    }

    public Rekening(int idRekening, String IBAN, double saldo) {
        this.idRekening = idRekening;
        this.IBAN = IBAN;
        this.saldo = saldo;
        this.transactiesHistorie = new ArrayList<>();
    }

    // TODO 7/12 wat hebben we nodig in de toString?
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Rekening: " + IBAN);
        result.append(", Huidige saldo: " + saldo);
        return result.toString();
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

    public ArrayList<Transactie> getTransactiesHistorie() {
        return transactiesHistorie;
    }

    public void setTransactiesHistorie(ArrayList<Transactie> transactiesHistorie) {
        this.transactiesHistorie = transactiesHistorie;
    }
}
