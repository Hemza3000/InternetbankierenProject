package sofa.internetbankieren.service;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import sofa.internetbankieren.backing_bean.MoneyTransferBackingbean;
import sofa.internetbankieren.model.Klant;
import sofa.internetbankieren.model.Priverekening;
import sofa.internetbankieren.model.Rekening;
import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
import sofa.internetbankieren.repository.PriverekeningDAO;


@SessionAttributes("ingelogde")

public class MoneyTransferService {

    private Priverekening priverekening;
    private PriverekeningDAO priverekeningDAO;
    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
    private MoneyTransferBackingbean moneyTransferBackingbean;

    public MoneyTransferService(PriverekeningDAO priverekeningDAO, BedrijfsrekeningDAO bedrijfsrekeningDAO) {
        this.priverekeningDAO = priverekeningDAO;
        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
    }

    Rekening tegenrekening;
    Rekening rekeninghouder;

    //todo tegenrekening klopt nog niet

    {
        assert moneyTransferBackingbean != null;
        tegenrekening = (Rekening) priverekeningDAO.getOneByIban(moneyTransferBackingbean.getTegenrekening());
    }

    double tegenrekeningSaldo = tegenrekening.getSaldo();
    double rekeninghouderSaldo = rekeninghouder.getSaldo();
    

//todo - ook voor bedrijfsrekeningen

    public void deposit (String rekeninghouder, double bedrag, String tegenrekening, String omschrijving) {

        if (bedrag <= 0) {
            System.out.println("Bedrag moet hoger zijn dan 0 euro");
        } else {
            tegenrekeningSaldo = tegenrekeningSaldo + bedrag;
            rekeninghouderSaldo = rekeninghouderSaldo - bedrag;
        }
        return;

    }
}
