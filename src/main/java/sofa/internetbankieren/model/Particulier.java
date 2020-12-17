package sofa.internetbankieren.model;

/**
 * @author Taco Jongkind & Hemza Lasri, 07-12-2020
 *
 * */

import sofa.internetbankieren.backing_bean.RegisterFormPartBackingBean;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
//import java.sql.Date;

public class Particulier extends Klant {

    private String voornaam;
    private String tussenvoegsels;
    private String achternaam;
    private LocalDate geboortedatum;
    private int BSN;

    private List<Priverekening> priverekeningen = new ArrayList<>();
    private List<Bedrijfsrekening> bedrijfsrekeningen = new ArrayList<>();


    public Particulier(int idKlant, String gebruikersnaam, String wachtwoord, String straat, int huisnummer,
                       String postcode, String woonplaats, String voornaam, String tussenvoegsels, String achternaam,
                       LocalDate geboortedatum, int BSN, List<Priverekening> priverekeningen, List<Bedrijfsrekening> bedrijfsrekeningen) {
        super(idKlant, gebruikersnaam, wachtwoord, straat, huisnummer, postcode, woonplaats);
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.BSN = BSN;
        this.priverekeningen = priverekeningen;
        this.bedrijfsrekeningen = bedrijfsrekeningen;
    }


    public Particulier() {
            }

    public Particulier(String voornaam, String voorvoegsels, String achternaam, LocalDate geboortedatum, int bsn, String straat,
                       int huisnummer, String postcode, String woonplaats) {
     this(0,"", "", straat, huisnummer, postcode, woonplaats, voornaam,
             voorvoegsels, achternaam, geboortedatum, bsn, new ArrayList<>(), new ArrayList<>());
    }

    public Particulier(RegisterFormPartBackingBean registerFormPartBackingBean) {
        super(0, "", "", registerFormPartBackingBean.getStraat(),
                registerFormPartBackingBean.getHuisnummer(), registerFormPartBackingBean.getPostcode(),
                registerFormPartBackingBean.getWoonplaats());
        this.voornaam = registerFormPartBackingBean.getVoornaam();
        this.tussenvoegsels = registerFormPartBackingBean.getTussenvoegsels();
        this.achternaam = registerFormPartBackingBean.getAchternaam();
        this.geboortedatum = LocalDate.parse(registerFormPartBackingBean.getGeboortedatum());
        this.BSN = registerFormPartBackingBean.getBSN();
        this.priverekeningen = null;
        this.bedrijfsrekeningen = null;
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

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
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

    @Override
    public String toString() {
        return super.toString() + " " + "Particulier{" +
                "voornaam='" + voornaam + '\'' +
                ", tussenvoegsels='" + tussenvoegsels + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum +
                ", BSN=" + BSN +
                ", priverekeningen=" + priverekeningen +
                ", bedrijfsrekeningen=" + bedrijfsrekeningen +
                '}';
    }
}
