package sofa.internetbankieren.model;

import org.springframework.jdbc.core.JdbcTemplate;
import sofa.internetbankieren.repository.BedrijfDAO;

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
    private String voornaam;
    private String tussenvoegsels;
    private String achternaam;
    private Rol rol;
    private List<Integer> bedrijfIDs; // Bedrijven waarvoor de medewerker accountmanager is
    private BedrijfDAO bedrijfDAO = new BedrijfDAO(new JdbcTemplate());

    public Medewerker() { super(); }

    // todo constructors aanpassen met bedrijfDAO?
    public Medewerker(int personeelsnummer, String voornaam, String tussenvoegsels, String achternaam, Rol rol,
                      List<Integer> bedrijfIDs) {
        super();
        this.personeelsnummer = personeelsnummer;
        this.voornaam = voornaam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.rol = rol;
        this.bedrijfIDs = bedrijfIDs;
    }

    public Medewerker(String voornaam, String tussenvoegsels, String achternaam, Rol rol) {
        this(0, voornaam, tussenvoegsels, achternaam, rol, new ArrayList<>());
    }

    public Medewerker(String voornaam, String achternaam, Rol rol) {
        this(voornaam, "", achternaam, rol);
    }

    public void voegBedrijfToe(Integer bedrijf){
        if(rol == Rol.ACCOUNT_MANAGER || rol == Rol.HOOFD_MKB) {
            if (bedrijfIDs == null)
                bedrijfIDs = new ArrayList<>();
            bedrijfIDs.add(bedrijf);
        }
        // TODO else foutafhandeling
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
        return bedrijfDAO.getAllByIdAccountmanager(personeelsnummer);
    }
    public void setBedrijfIDs(List<Integer> bedrijfIDs) {
        this.bedrijfIDs = bedrijfIDs;
    }
}
