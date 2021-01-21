package sofa.internetbankieren.service;

import org.springframework.stereotype.Service;
import sofa.internetbankieren.model.Bedrijfsrekening;
import sofa.internetbankieren.model.Priverekening;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.model.Transactie;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;
import sofa.internetbankieren.repository.TransactieDAO;

@Service
public class MoneyTransferService {

    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private TransactieDAO transactieDAO;

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
}
