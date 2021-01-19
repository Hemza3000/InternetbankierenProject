package sofa.internetbankieren.service;

import org.springframework.stereotype.Service;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.ParticulierDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;

import java.util.List;

@Service
public class AccountService {

    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private ParticulierDAO particulierDAO;

    public AccountService(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO,
                          ParticulierDAO particulierDAO) {
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.particulierDAO = particulierDAO;
    }

    public String createRandomIBAN() {
        String controlenummer = String.format("%02d", (int)(Math.random() * 100));
        String rekeningnummer = String.format("%07d", (int)(Math.random() * 10000000));
        String result = "NL" + controlenummer + "SOFA000" + rekeningnummer;
        // controle of IBAN al bestaat
        while (!checkUniqueIBAN(result)) {
            result = "NL" + controlenummer + "SOFA000" + rekeningnummer;
        }
        return result;
    }

    public boolean checkUniqueIBAN(String IBAN){
        List<Priverekening> priverekeningList = priverekeningDAO.getAllByIban(IBAN);
        List<Bedrijfsrekening> bedrijfsrekeningList = bedrijfsrekeningDAO.getAllByIban(IBAN);
            return priverekeningList.isEmpty() && bedrijfsrekeningList.isEmpty();
    }

    // controle in BSN in database bestaat.
    public boolean doesBsnExist(int bsn) {
        return particulierDAO.getAllByBSN(bsn).size() == 1;
    }


    // methode om rekening op te slaan. Met check of het om een bedrijf of particulier gaat.
    public void saveNewAccountNumber(Rekening rekening, Klant ingelogde) {
        if ( ingelogde instanceof Particulier) {
            priverekeningDAO.storeOne((Priverekening) rekening);
        } else if ( ingelogde instanceof  Bedrijf) {
            bedrijfsrekeningDAO.storeOne((Bedrijfsrekening) rekening);
        }
    }
}
