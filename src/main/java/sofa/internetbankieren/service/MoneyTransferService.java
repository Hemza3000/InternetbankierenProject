//package sofa.internetbankieren.service;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Service;
//import sofa.internetbankieren.backing_bean.MoneyTransferBackingBean;
//import sofa.internetbankieren.model.Priverekening;
//import sofa.internetbankieren.model.Rekening;
//import sofa.internetbankieren.repository.BedrijfsrekeningDAO;
//import sofa.internetbankieren.repository.PriverekeningDAO;
//
//@Service
//public class MoneyTransferService {
//
//    private Priverekening priverekening;
//    private PriverekeningDAO priverekeningDAO;
//    private BedrijfsrekeningDAO bedrijfsrekeningDAO;
//    private MoneyTransferBackingBean moneyTransferBackingBean;
//
//    public MoneyTransferService(PriverekeningDAO priverekeningDAO,
//                                BedrijfsrekeningDAO bedrijfsrekeningDAO,
//                                MoneyTransferBackingBean moneyTransferBackingBean) {
//        this.priverekeningDAO = priverekeningDAO;
//        this.bedrijfsrekeningDAO = bedrijfsrekeningDAO;
//        this.moneyTransferBackingBean = moneyTransferBackingBean;
//    }
//
//    Rekening tegenrekening;
//    Rekening rekeninghouder;
//
//    //todo tegenrekening klopt nog niet
//
////    {
////        assert moneyTransferBackingBean != null;
////        tegenrekening = (Rekening) priverekeningDAO.getOneByIban(moneyTransferBackingBean.getTegenrekening());
////    }
//
//    double tegenrekeningSaldo = tegenrekening.getSaldo();
//    double rekeninghouderSaldo = rekeninghouder.getSaldo();
//
//
////todo - ook voor bedrijfsrekeningen
//
//    public void deposit (String rekeninghouder, double bedrag, String tegenrekening, String omschrijving) {
//
//        if (bedrag <= 0) {
//            System.out.println("Bedrag moet hoger zijn dan 0 euro");
//        } else {
//            tegenrekeningSaldo = tegenrekeningSaldo + bedrag;
//            rekeninghouderSaldo = rekeninghouderSaldo - bedrag;
//        }
//        return;
//
//    }
//}
