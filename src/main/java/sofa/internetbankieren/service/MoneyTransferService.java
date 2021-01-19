package sofa.internetbankieren.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingBean;
import sofa.internetbankieren.model.Bedrijfsrekening;
import sofa.internetbankieren.model.Priverekening;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;

@Service
@SessionAttributes("ingelogde")

public class MoneyTransferService {

    private Priverekening priverekening;
    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private MoneyTransferBackingBean moneyTransferBackingbean;

    public MoneyTransferService(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO) {
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
    }

//todo - ook voor bedrijfsrekeningen

    public boolean validatieSaldo(Rekening mijnRekening, double bedrag) {

        if (mijnRekening.getSaldo() < bedrag || bedrag <= 0 ) {
            System.out.println("saldo te laag");
            return false;
        } else {
            System.out.println("voldoende saldo");
            return true;
        }
    }

}
