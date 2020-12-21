package sofa.internetbankieren.model;

import org.springframework.jdbc.core.JdbcTemplate;
import sofa.internetbankieren.repository.BedrijfDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wendy Ellens
 *
 * Modelleert een bankmedewerker.
 * Reviewed by Wichert 11-12
 */
public class Medewerker {

    public enum Rol {
        HOOFD_PARTICULIEREN,
        HOOFD_MKB,
        ACCOUNTMANAGER
    }

    private int personeelsnummer;
    private String gebruikersnaam;
    private String wachtwoord;
    private String voornaam;
    private String tussenvoegsels;
    private String achternaam;
    private Rol rol;
    private List<Integer> bedrijfIDs; // Bedrijven waarvoor de medewerker accountmanager is

    public Medewerker() { super(); }

    public Medewerker(int personeelsnummer, String gebruikersnaam, String wachtwoord, String voornaam,
                      String tussenvoegsels, String achternaam, Rol rol, List<Integer> bedrijven) {
        super();
        this.personeelsnummer = personeelsnummer;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.rol = rol;
        this.bedrijfIDs = bedrijfIDs;
    }

    public Medewerker(String gebruikersnaam, String wachtwoord, String voornaam, String tussenvoegsels,
                      String achternaam, Rol rol, List<Integer> bedrijfIDs) {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.rol = rol;
        this.bedrijfIDs = bedrijfIDs;
    }
    // TODO : wat te doen met onderstaande 2 methoden (TacoJ)
    /*public Medewerker(String voornaam, String tussenvoegsels, String achternaam, Rol rol) {
        this(0, voornaam, tussenvoegsels, achternaam, rol, new ArrayList<>());
    }*/

    /*public Medewerker(String voornaam, String achternaam, Rol rol) {
        this(voornaam, "", achternaam, rol);
    }*/

    public void voegBedrijfToe(Integer bedrijf){
        if(rol == Rol.ACCOUNTMANAGER || rol == Rol.HOOFD_MKB) {
            if (bedrijfIDs == null)
                bedrijfIDs = new ArrayList<>();
            bedrijfIDs.add(bedrijf);
        }
        // TODO else foutafhandeling
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public int getPersoneelsnummer() {
        return personeelsnummer;
    }
    public void setPersoneelsnummer(int personeelsnummer) {
        this.personeelsnummer = personeelsnummer;
    }


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

    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Bedrijf> getBedrijven() {
        return new BedrijfDAO(new JdbcTemplate()).getAllByIdAccountmanager(personeelsnummer);
    }
    public void setBedrijfIDs(List<Integer> bedrijfIDs) {
        this.bedrijfIDs = bedrijfIDs;
    }

    @Override
    public String toString() {
        return "Medewerker{" +
                "personeelsnummer=" + personeelsnummer +
                ", gebruikersnaam='" + gebruikersnaam + '\'' +
                ", wachtwoord='" + wachtwoord + '\'' +
                ", voornaam='" + voornaam + '\'' +
                ", tussenvoegsels='" + tussenvoegsels + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", rol=" + rol +
                ", bedrijven=" + bedrijfIDs +
                '}';
    }
}
