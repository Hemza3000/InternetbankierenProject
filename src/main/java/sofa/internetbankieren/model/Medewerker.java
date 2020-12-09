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

    private int persooneelsnummer;
    private String voornaam;
    private String tussenvoegsels;
    private String achternaam;
    private Rol rol;
    private List<Bedrijf> bedrijven; // Bedrijven waarvoor de medewerker accountmanager is

    public Medewerker() { super(); }

    public Medewerker(int persooneelsnummer, String voornaam, String tussenvoegsels, String achternaam, Rol rol) {
        super();
        this.persooneelsnummer = persooneelsnummer;
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.rol = rol;
    }

    public Medewerker(String voornaam, String tussenvoegsels, String achternaam, Rol rol) {
        this(0, voornaam, tussenvoegsels, achternaam, rol);
    }

    public Medewerker(String voornaam, String achternaam, Rol rol) {
        new Medewerker(voornaam, "", achternaam, rol);
    }

    public void voegBedrijfToe(Bedrijf bedrijf){
        if(rol == Rol.ACCOUNT_MANAGER) {
            if (bedrijven == null)
                bedrijven = new ArrayList<>();
            bedrijven.add(bedrijf);
        }
        // TODO else foutafhandeling
    }

    public int getPersooneelsnummer() {
        return persooneelsnummer;
    }
    public void setPersooneelsnummer(int persooneelsnummer) {
        this.persooneelsnummer = persooneelsnummer;
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
}
