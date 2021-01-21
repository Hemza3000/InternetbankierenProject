package sofa.internetbankieren.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import sofa.internetbankieren.model.Bedrijfsrekening;
import sofa.internetbankieren.model.Priverekening;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.model.Transactie;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;
import sofa.internetbankieren.repository.TransactieDAO;

import java.util.Collections;
import java.util.List;

@Service

public class MoneyTransferService {

    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private TransactieDAO transactieDAO;
    private static final int MAX_TRANSACTIES = 10;


    public MoneyTransferService(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO, TransactieDAO transactieDAO) {
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
        this.transactieDAO = transactieDAO;
    }

    //todo --  wetten van Morgan?

    public boolean validatieSaldo(Rekening mijnRekening, Rekening tegenrekening, double bedrag, double eigenSaldo, double tegenrekeningSaldo) {

        if (mijnRekening.getSaldo() < bedrag || bedrag <= 0 ) {
            return false;
        } else {
            mijnRekening.setSaldo(eigenSaldo - bedrag);
            tegenrekening.setSaldo(tegenrekeningSaldo + bedrag);
            return true;
        }
    }

    public void updateRekeningen (Rekening mijnRekening, Rekening tegenrekening ) {

        if (mijnRekening instanceof Bedrijfsrekening) {
            bedrijfsrekeningDAO.updateOne((Bedrijfsrekening) mijnRekening);
        }
        if (mijnRekening instanceof Priverekening) {
            priverekeningDAO.updateOne((Priverekening) mijnRekening);
        }
        if (tegenrekening instanceof Priverekening) {
            priverekeningDAO.updateOne((Priverekening) tegenrekening);
        }
        if (tegenrekening instanceof Bedrijfsrekening) {
            bedrijfsrekeningDAO.updateOne((Bedrijfsrekening) tegenrekening);
        }
    }

    public void slaTransactieOp (Transactie nieuwetransactie) {
        transactieDAO.storeOne(nieuwetransactie);
    }
    public List toonTransacties(Rekening mijnRekening){
        List<Transactie> transacties = mijnRekening.getTransacties();
        Collections.sort(transacties, Collections.reverseOrder());
        if (transacties.size() > 0)
            transacties = transacties.subList(0, Math.min(transacties.size(), MAX_TRANSACTIES));
        return transacties;
    }
}
