package sofa.internetbankieren.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import sofa.internetbankieren.model.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    Priverekening priverekening = Mockito.mock(Priverekening.class);
    Bedrijfsrekening bedrijfsrekening = Mockito.mock(Bedrijfsrekening.class);
    Particulier particulier = Mockito.mock(Particulier.class);
    Bedrijf bedrijf = Mockito.mock(Bedrijf.class);


    @Test // Wichert
    void createRandomIBAN() {
        String controlenummer = "00";
        String rekeningnummer = "1234567";
        String result = "NL" + controlenummer + "SOFA000" + rekeningnummer;
        String expect = "NL00SOFA0001234567";
        assertEquals(expect, result);
    }

    @Test // Wichert
    void checkUniqueIBAN() {
        ArrayList<Priverekening> p = new ArrayList<>(0);
        ArrayList<Bedrijfsrekening> b = new ArrayList<>(0);
        assertTrue(p.isEmpty() && b.isEmpty());
        p.add(priverekening);
        assertFalse(b.isEmpty() && p.isEmpty());
        b.add(bedrijfsrekening);
        assertFalse(b.isEmpty() && p.isEmpty());
    }

    @Test // Wichert
    void saveNewAccountNumber() {
        assertTrue(particulier instanceof Particulier);
        assertTrue(bedrijf instanceof Bedrijf);
    }

    @Test
    void doesBsnExist() {
    }

    @Test
    void getRekeningbyIban() {
    }

    @Test
    void getKlantenbyGebruikersnaamWachtwoord() {
    }


    @Test
    void geefTransactieHistorie() {
    }
}