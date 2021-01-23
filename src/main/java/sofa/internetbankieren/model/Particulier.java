package sofa.internetbankieren.model;

/**
 * @author Taco Jongkind & Hemza Lasri, 07-12-2020
 *
 * */

import org.springframework.format.annotation.DateTimeFormat;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Particulier extends Klant {

    private String voornaam;
    private String tussenvoegsels;
    private String achternaam;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // toegevoegd door Wendy om geboortedatum te vullen vanaf html-pagina
    private LocalDate geboortedatum;
    private int BSN;
    private List<Integer> bedrijfsrekeningIDs = new ArrayList<>();
    private PriverekeningDAO priverekeningDAO;

    public Particulier(int idKlant, String gebruikersnaam, String wachtwoord, String straat, int huisnummer,
                       String postcode, String woonplaats, String voornaam, String tussenvoegsels, String achternaam,
                       LocalDate geboortedatum, int BSN, List<Integer> priverekeningIDs,
                       List<Integer> bedrijfsrekeningIDs, BedrijfsrekeningDAO bedrijfsrekeningDAO,
                       PriverekeningDAO priverekeningDAO) {
        super(idKlant, gebruikersnaam, wachtwoord, straat, huisnummer, postcode, woonplaats, priverekeningIDs, bedrijfsrekeningDAO);
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.BSN = BSN;
        this.bedrijfsrekeningIDs = bedrijfsrekeningIDs;
        this.priverekeningDAO = priverekeningDAO;
    }

    public Particulier(BedrijfsrekeningDAO bedrijfsrekeningDAO, PriverekeningDAO priverekeningDAO) {
        super(bedrijfsrekeningDAO);
        this.priverekeningDAO = priverekeningDAO;
    }

    public Particulier(String voornaam, String voorvoegsels, String achternaam, LocalDate geboortedatum,
                       int bsn, String straat, int huisnummer, String postcode, String woonplaats,
                       BedrijfsrekeningDAO bedrijfsrekeningDAO, PriverekeningDAO priverekeningDAO) {
     this(0,"", "", straat, huisnummer, postcode, woonplaats, voornaam, voorvoegsels,
             achternaam, geboortedatum, bsn, new ArrayList<>(), new ArrayList<>(), bedrijfsrekeningDAO,
             priverekeningDAO);
    }

    // toegevoegd door Wendy
    @Override
    public String getNaam() {
        return voornaam + " "
                + ((tussenvoegsels == null) ? "" : tussenvoegsels) + " "
                + achternaam;
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

    @Override
    public List<Priverekening> getRekeningen() {
        return priverekeningDAO.getAllByRekeninghouder(super.getIdKlant());
    }

    public List<Bedrijfsrekening> getBedrijfsrekeningen() {
        return super.getBedrijfsrekeningDAO().getAllByContactpersoon(super.getIdKlant());
    }

    public void setBedrijfsrekeningIDs(List<Integer> bedrijfsrekeningIDs) {
        this.bedrijfsrekeningIDs = bedrijfsrekeningIDs;
    }

    @Override
    public String toString() {
        return super.toString() + " " + "Particulier{" +
                "voornaam='" + voornaam + '\'' +
                ", tussenvoegsels='" + tussenvoegsels + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum +
                ", BSN=" + BSN +
                ", bedrijfsrekeningen=" + bedrijfsrekeningIDs +
                '}';
    }
}
