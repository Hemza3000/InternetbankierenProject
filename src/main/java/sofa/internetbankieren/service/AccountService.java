package sofa.internetbankieren.service;

import org.springframework.stereotype.Service;
import sofa.internetbankieren.model.*;
import sofa.internetbankieren.repository.BedrijfDAO;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.ParticulierDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Hemza Lasri, Wichert Tjerkstra
 */
@Service
public class AccountService {

    private static final int MAX_TRANSACTIES = 10; // lengte transactieoverzicht
    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private ParticulierDAO particulierDAO;
    private BedrijfDAO bedrijfDAO;

    public AccountService(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO,
                          ParticulierDAO particulierDAO, BedrijfDAO bedrijfDAO) {
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.particulierDAO = particulierDAO;
        this.bedrijfDAO = bedrijfDAO;
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

    public Rekening getRekeningbyIban(String iban){
        List<Rekening> rekeningen = new ArrayList<>();
        rekeningen.addAll(priverekeningDAO.getAllByIban(iban));
        rekeningen.addAll(bedrijfsrekeningDAO.getAllByIban(iban));
        return rekeningen.get(0);
    }

    // methode om rekening op te slaan. Met check of het om een bedrijf of particulier gaat.
    public void saveNewAccountNumber(Rekening rekening, Klant ingelogde) {
        if ( ingelogde instanceof Particulier) {
            priverekeningDAO.storeOne((Priverekening) rekening);
        } else if ( ingelogde instanceof  Bedrijf) {
            bedrijfsrekeningDAO.storeOne((Bedrijfsrekening) rekening);
        }
    }

    // Geschreven door Wendy
    // Geeft een lijst van de laatste maximaal MAX_TRANSACTIES transacties met de meest recente vooraan
    public List<Transactie> geefTransactieHistorie(Rekening mijnRekening){
        List<Transactie> transacties = mijnRekening.getTransacties();
        Collections.sort(transacties, Collections.reverseOrder());
        if (transacties.size() > 0)
            transacties = transacties.subList(0, Math.min(transacties.size(), MAX_TRANSACTIES));
        return transacties;
    }
}
