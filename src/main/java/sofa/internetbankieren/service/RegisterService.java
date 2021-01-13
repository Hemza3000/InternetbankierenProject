package sofa.internetbankieren.service;

import org.springframework.stereotype.Service;
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.repository.BedrijfDAO;
import sofa.internetbankieren.repository.ParticulierDAO;

import java.util.List;

@Service
public class RegisterService {

    private ParticulierDAO particulierDAO;
    private BedrijfDAO bedrijfDAO;

    public RegisterService(ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO) {
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
    }

    public boolean checkUniqueUsername(String username) {
        List<Particulier> particulierList = particulierDAO.getAllByGebruikersnaam(username);
        List<Bedrijf> bedrijfList = bedrijfDAO.getOneByGebruikersnaam(username);
        return particulierList.isEmpty() && bedrijfList.isEmpty();
    }
}
