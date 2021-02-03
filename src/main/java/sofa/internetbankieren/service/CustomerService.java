package sofa.internetbankieren.service;

import org.springframework.stereotype.Service;
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Klant;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.repository.BedrijfDAO;
import sofa.internetbankieren.repository.ParticulierDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wichert Tjerkstra, Wendy Ellens
 *
 * Biedt services aan de controllers m.b.t. particuliere en zakelijke klanten.
 * Communiceert met de desbetreffende DAO's om toegang tot de database te krijgen.
 */
@Service
public class CustomerService {

    private final ParticulierDAO particulierDAO;
    private final BedrijfDAO bedrijfDAO;

    public CustomerService(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO) {
        super();
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
    }

    public boolean doesUsernameExist(String username) {
        return !particulierDAO.getOneByGebruikersnaam(username).isEmpty() ||
                !bedrijfDAO.getOneByGebruikersnaam(username).isEmpty();
    }

    public boolean doesBsnExist(int bsn) {
        return !particulierDAO.getOneByBSN(bsn).isEmpty();
    }

    public void storeCustomer(Klant klant) {
        if (klant instanceof Particulier) {
            particulierDAO.storeOne((Particulier) klant);
        }
        else if (klant instanceof Bedrijf) {
            bedrijfDAO.storeOne((Bedrijf) klant);
        }
    }

    public void changeCustomer(Klant klant) {
        if (klant instanceof Particulier) {
            particulierDAO.updateOne((Particulier) klant);
        }
        else if (klant instanceof Bedrijf) {
            bedrijfDAO.updateOne((Bedrijf) klant);
        }
    }

    // gemaakt door Hemza
    public List<Klant> getKlantenbyGebruikersnaamWachtwoord (String gebruikersnaam, String wachtwoord){
        List<Klant> alleklanten = new ArrayList<>();
        alleklanten.addAll(particulierDAO.getOneByGebruikersnaamWachtwoord(gebruikersnaam, wachtwoord));
        alleklanten.addAll(bedrijfDAO.getOneByGebruikersnaamWachtwoord(gebruikersnaam, wachtwoord));
        return alleklanten;
    }
}