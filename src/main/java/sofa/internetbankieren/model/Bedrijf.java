package sofa.internetbankieren.model;


/**
 * @author Taco Jongkind & Hemza Lasri, 07-12-2020
 *
 * */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sofa.internetbankieren.repository.BedrijfDAO;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;

import java.util.ArrayList;
import java.util.List;

public class Bedrijf extends Klant {

    private String bedrijfsnaam;
    private int KVKNummer;
    private String sector;
    private String BTWNummer;
    private Medewerker accountmanager;
    private List<Integer> rekeningIDs = new ArrayList<>();

    public Bedrijf(int idKlant, String gebruikersnaam, String wachtwoord, String straat, int huisnummer,
                   String postcode, String woonplaats, String bedrijfsnaam, int KVKNummer, String sector,
                   String BTWNummer, Medewerker accountmanager, List<Integer> rekeningIDs,
                   BedrijfsrekeningDAO bedrijfsrekeningDAO) {
        super(idKlant, gebruikersnaam, wachtwoord, straat, huisnummer, postcode, woonplaats, bedrijfsrekeningDAO);
        this.bedrijfsnaam = bedrijfsnaam;
        this.KVKNummer = KVKNummer;
        this.sector = sector;
        this.BTWNummer = BTWNummer;
        this.accountmanager = accountmanager;
        this.rekeningIDs = rekeningIDs;
    }

    public Bedrijf(String straat, int huisnummer, String postcode, String woonplaats, String bedrijfsnaam,
                   int KVKNummer, String sector, String BTWNummer, Medewerker accountmanager,
                   BedrijfsrekeningDAO bedrijfsrekeningDAO) {
        this(0, "", "", straat, huisnummer, postcode, woonplaats, bedrijfsnaam,
                KVKNummer, sector, BTWNummer, accountmanager, new ArrayList<>(), bedrijfsrekeningDAO);
    }

    public Bedrijf(){

    }

    //getters and setters


    public String getBedrijfsnaam() {
        return bedrijfsnaam;
    }

    public void setBedrijfsnaam(String bedrijfsnaam) {
        this.bedrijfsnaam = bedrijfsnaam;
    }

    public int getKVKNummer() {
        return KVKNummer;
    }

    public void setKVKNummer(int KVKNummer) {
        this.KVKNummer = KVKNummer;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getBTWNummer() {
        return BTWNummer;
    }

    public void setBTWNummer(String BTWNummer) {
        this.BTWNummer = BTWNummer;
    }

    public Medewerker getAccountmanager() {
        return accountmanager;
    }

    public void setAccountmanager(Medewerker accountmanager) {
        this.accountmanager = accountmanager;
    }

    public List<Bedrijfsrekening> getRekeningen() {
        return super.getBedrijfsrekeningDAO().getAllByBedrijf(super.getIdKlant()) ;
    }

    public void setRekeningIDs(List<Integer> rekeningIDs) {
        this.rekeningIDs = rekeningIDs;
    }
}
