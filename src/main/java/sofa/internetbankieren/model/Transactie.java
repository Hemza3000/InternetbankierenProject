package sofa.internetbankieren.model;

import java.time.LocalDateTime;

/**
 * @author Wendy Ellens
 * <p>
 * Modelleert een banktransactie (bij- of afschrijving)
 */
public class Transactie implements Comparable<Transactie> {

    private int idTransactie;
    private Rekening rekening;
    private boolean bijschrijving; // false voor afschrijvingen
    private double bedrag;
    private LocalDateTime datum;
    private String omschrijving;

    public Transactie() {
        super();
    }

    public Transactie(int idTransactie, Rekening rekening, boolean bijschrijving,
                      double bedrag, LocalDateTime datum, String omschrijving) {
        super();
        this.idTransactie = idTransactie;
        this.rekening = rekening;
        this.bijschrijving = bijschrijving;
        this.bedrag = bedrag;
        this.datum = datum;
        this.omschrijving = omschrijving;
    }

    public Transactie(Rekening rekening, boolean bijschrijving, double bedrag,
                      LocalDateTime datum, String omschrijving) {
        this(0, rekening, bijschrijving, bedrag, datum, omschrijving);
    }

    public int getIdTransactie() {
        return idTransactie;
    }

    public void setIdTransactie(int idTransactie) {
        this.idTransactie = idTransactie;
    }

    public Rekening getRekening() {
        return rekening;
    }

    public void setRekening(Rekening rekening) {
        this.rekening = rekening;
    }

    public boolean isBijschrijving() {
        return bijschrijving;
    }

    public void setBijschrijving(boolean bijschrijving) {
        this.bijschrijving = bijschrijving;
    }

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public int compareTo(Transactie other) {
        return this.datum.compareTo(other.datum);
    }
}
