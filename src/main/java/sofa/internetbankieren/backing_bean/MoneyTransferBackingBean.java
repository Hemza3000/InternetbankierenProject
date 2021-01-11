package sofa.internetbankieren.backing_bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoneyTransferBackingBean {

    private Logger logger = LoggerFactory.getLogger(MoneyTransferBackingBean.class);

    private String IBAN;
    private double bedrag;
    private String omschrijving;

    public MoneyTransferBackingBean(String IBAN, double bedrag, String omschrijving) {
        super();
        this.IBAN = IBAN;
        this.bedrag = bedrag;
        this.omschrijving = omschrijving;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }
}
