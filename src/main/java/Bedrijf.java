import java.util.ArrayList;
import java.util.List;

public class Bedrijf extends Klant {

    private String bedrijfsnaam;
    private int KVKNummer;
    private String sector;
    private String BTWNummer;
    private Medewerker accountmanager;

    //moet dit niet bedrijfsrekeningen zijn?
    private List<Bedrijfsrekening> rekeningen = new ArrayList<>();

    public Bedrijf(int idKlant, String straatnaam, String huisnummer, String postcode, String woonplaats, String bedrijfsnaam,
                   int KVKNummer, String sector, String BTWNummer, Medewerker accountmanager, List<Bedrijfsrekening> rekeningen) {
        super(idKlant, straatnaam, huisnummer, postcode, woonplaats);
        this.bedrijfsnaam = bedrijfsnaam;
        this.KVKNummer = KVKNummer;
        this.sector = sector;
        this.BTWNummer = BTWNummer;
        this.accountmanager = accountmanager;
        this.rekeningen = rekeningen;
    }
    public Bedrijf(){

    }
    //Vraag: moet deze er nog bij?
    public void addBedrijf(){

    }
}
