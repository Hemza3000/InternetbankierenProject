package sofa.internetbankieren.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.model.Priverekening;
import sofa.internetbankieren.repository.TransactieDAO;
import sofa.internetbankieren.service.AccountService;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Wendy Ellens
 */
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;
    @MockBean
    private TransactieDAO transactieDAO;

    public AccountControllerTest() {
        super();
    }

    // test of de gevraagde pagina correct wordt geladen bij een get request voor "/rekening/{IBAN}"
    @Test
    public void accountHandlerTest() throws Exception {
        String iban = "NL12SOFA0001234567";
        Priverekening priverekening = new Priverekening(1, iban, transactieDAO, new Particulier());
        Mockito.when(accountService.getRekeningbyIban(iban)).thenReturn(priverekening);
        Mockito.when(accountService.geefTransactieHistorie(priverekening)).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.get("/rekening/" + iban)).andExpect(status().isOk());
    }
}