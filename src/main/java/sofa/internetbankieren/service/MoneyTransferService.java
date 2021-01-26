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

    public boolean validatieSaldo(Rekening mijnRekening, Rekening tegenrekening, double bedrag) {

        if (mijnRekening.getSaldo() < bedrag) {
            return false;
        } else {
            mijnRekening.setSaldo(mijnRekening.getSaldo() - bedrag);
            tegenrekening.setSaldo(tegenrekening.getSaldo() + bedrag);
            return true;
        }
    }

    //todo één regel van maken
    public boolean validatieBedrag(double bedrag) {
        return bedrag > 0;
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
