package sofa.internetbankieren.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wendy Ellens
 *
 * Modelleert een bankmedewerker.
 */
public class Medewerker {

    public enum Rol {
        HOOFD_PARTICULIEREN,
        HOOFD_MKB,
        ACCOUNT_MANAGER
    }

    private int personeelsnummer;
    private String gebruikersnaam;
    private String wachtwoord;
    private String voornaam;
    private String tussenvoegsels;
    private String achternaam;
    private Rol rol;
    private List<Bedrijf> bedrijven; // Bedrijven waarvoor de medewerker accountmanager is

    public Medewerker() { super(); }

    public Medewerker(int personeelsnummer, String gebruikersnaam, String wachtwoord, String voornaam,
                      String tussenvoegsels, String achternaam, Rol rol, List<Bedrijf> bedrijven) {
        super();
        this.personeelsnummer = personeelsnummer;
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.rol = rol;
        this.bedrijven = bedrijven;
    }

    public Medewerker(String gebruikersnaam, String wachtwoord, String voornaam, String tussenvoegsels,
                      String achternaam, Rol rol, List<Bedrijf> bedrijven) {
        this.gebruikersnaam = gebruikersnaam;
        this.wachtwoord = wachtwoord;
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.rol = rol;
        this.bedrijven = bedrijven;
    }
    // TODO : wat te doen met onderstaande 2 methoden (TacoJ)
    /*public Medewerker(String voornaam, String tussenvoegsels, String achternaam, Rol rol) {
        this(0, voornaam, tussenvoegsels, achternaam, rol, new ArrayList<>());
    }*/

    /*public Medewerker(String voornaam, String achternaam, Rol rol) {
        this(voornaam, "", achternaam, rol);
    }*/

    public void voegBedrijfToe(Bedrijf bedrijf){
        if(rol == Rol.ACCOUNT_MANAGER || rol == Rol.HOOFD_MKB) {
            if (bedrijven == null)
                bedrijven = new ArrayList<>();
            bedrijven.add(bedrijf);
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
        return bedrijven;
    }
    public void setBedrijven(List<Bedrijf> bedrijven) {
        this.bedrijven = bedrijven;
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
                ", bedrijven=" + bedrijven +
                '}';
    }
}
