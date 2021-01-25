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
 */
@Service
public class CustomerService {

    private final ParticulierDAO particulierDAO;
    private final BedrijfDAO bedrijfDAO;

    public CustomerService(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO) {
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
    }

    public boolean checkUniqueUsername(String username) {
        List<Particulier> particulierList = particulierDAO.getOneByGebruikersnaam(username);
        List<Bedrijf> bedrijfList = bedrijfDAO.getOneByGebruikersnaam(username);
        return particulierList.isEmpty() && bedrijfList.isEmpty();
    }

    public boolean doesBsnExist(int bsn) {
        return !particulierDAO.getOneByBSN(bsn).isEmpty();
    }

    public void storeNewCustomer(Klant klant) {
        if (klant instanceof Particulier) {
            particulierDAO.storeOne((Particulier) klant);
        }
        else if (klant instanceof Bedrijf) {
            bedrijfDAO.storeOne((Bedrijf) klant);
        }
    }

    // gemaakt door Hemza
    public List<Klant> getKlantenbyGebruikersnaamWachtwoord (String gebruikersnaam, String wachtwoord){
        List<Particulier> particuliereklanten =
                particulierDAO.getOneByGebruikersnaamWachtwoord(gebruikersnaam, wachtwoord);
        List<Bedrijf> bedrijfsklanten =
                bedrijfDAO.getOneByGebruikersnaamWachtwoord(gebruikersnaam, wachtwoord);
        List<Klant> alleklanten = new ArrayList<>();
        alleklanten.addAll(particuliereklanten);
        alleklanten.addAll(bedrijfsklanten);
        return alleklanten;
    }
}