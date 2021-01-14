package sofa.internetbankieren.service;

import org.springframework.stereotype.Service;
import sofa.internetbankieren.model.Bedrijfsrekening;
import sofa.internetbankieren.model.Priverekening;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;

import java.util.List;

@Service
public class AccountService {

    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;

    public AccountService(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO) {
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
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
        List<Bedrijfsrekening> bedrijfsrekeningList = bedrijfsrekeningDAO.getOneByIban(IBAN);
            return priverekeningList.isEmpty() && bedrijfsrekeningList.isEmpty();
    }
}
