package sofa.internetbankieren.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wendy Ellens
 *
 * Modelleert een bankmedewerker.
 */
public class Medewerker {

    private enum Rol {
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

    public Medewerker(String voornaam, String tussenvoegsels, String achternaam, Rol rol) {
        super();
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.rol = rol;
    }

    public Medewerker(String voornaam, String achternaam, Rol rol) {
        new Medewerker(voornaam, "", achternaam, rol);
    }

    public void voegBedrijfToe(Bedrijf bedrijf){
        if(rol == Rol.ACCOUNT_MANAGER) {
            if (bedrijven == null)
                bedrijven = new ArrayList<>(); // TODO Hier of in attribuutdeclaratie of in constructor? Alleen voor accountmanagers
            bedrijven.add(bedrijf);
        }
        // TODO else foutafhandeling
    }
}
