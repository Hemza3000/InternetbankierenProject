package sofa.internetbankieren.model;

/**
 * @author Taco Jongkind & Hemza Lasri, 07-12-2020
 *
 * */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Particulier extends Klant {

    private String voornaam;
    private String tussenvoegsels;
    private String achternaam;
    private Date geboortedatum;
    private int BSN;

    private List<Priverekening> priverekeningen = new ArrayList<>();
    private List<Bedrijfsrekening> bedrijfsrekeningen = new ArrayList<>();


    public Particulier(int idKlant, String straatnaam, int huisnummer, String postcode, String woonplaats, String voornaam, String tussenvoegsels, String achternaam, Date geboortedatum, int BSN) {
        super(idKlant, straatnaam, huisnummer, postcode, woonplaats);
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.BSN = BSN;
    }

    public Particulier() {
            }



    public Particulier(int idKlant, String straatnaam, int huisnummer, String postcode, String woonplaats, String voornaam,
                       String tussenvoegsels, String achternaam, Date geboortedatum,
                       int BSN, List<Priverekening> priverekeningen, List<Bedrijfsrekening> bedrijfsrekeningen) {
        super(idKlant, straatnaam, huisnummer, postcode, woonplaats);
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.BSN = BSN;
        this.priverekeningen = priverekeningen;
        this.bedrijfsrekeningen = bedrijfsrekeningen;
    }
    public void addParticulier(){

    }

    //Begin getters/setters
    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsels() {
        return tussenvoegsels;
    }

    public void setTussenvoegsels(String tussenvoegsels) {
        this.tussenvoegsels = tussenvoegsels;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public int getBSN() {
        return BSN;
    }

    public void setBSN(int BSN) {
        this.BSN = BSN;
    }

    public List<Priverekening> getPriverekeningen() {
        return priverekeningen;
    }

    public void setPriverekeningen(List<Priverekening> priverekeningen) {
        this.priverekeningen = priverekeningen;
    }

    public List<Bedrijfsrekening> getBedrijfsrekeningen() {
        return bedrijfsrekeningen;
    }

    public void setBedrijfsrekeningen(List<Bedrijfsrekening> bedrijfsrekeningen) {
        this.bedrijfsrekeningen = bedrijfsrekeningen;
    }
}
