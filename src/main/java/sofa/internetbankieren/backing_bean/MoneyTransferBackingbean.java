package sofa.internetbankieren.backing_bean;


public class MoneyTransferBackingbean {

    private double bedrag;
    private String tegenrekening;
    private String omschrijving;

    public MoneyTransferBackingbean(double bedrag, String tegenrekening, String omschrijving) {
        this.bedrag = bedrag;
        this.tegenrekening = tegenrekening;
        this.omschrijving = omschrijving;
    }

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public String getTegenrekening() {
        return tegenrekening;
    }

    public void setTegenrekening(String tegenrekening) {
        this.tegenrekening = tegenrekening;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    //deze comment mag weg
}

