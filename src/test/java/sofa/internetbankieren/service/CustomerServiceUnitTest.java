package sofa.internetbankieren.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import sofa.internetbankieren.model.Bedrijf;
import sofa.internetbankieren.model.Klant;
import sofa.internetbankieren.model.Particulier;
import sofa.internetbankieren.repository.BedrijfDAO;
import sofa.internetbankieren.repository.ParticulierDAO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceUnitTest {

    private final ParticulierDAO particulierDAO = Mockito.mock(ParticulierDAO.class);
    private final BedrijfDAO bedrijfDAO = Mockito.mock(BedrijfDAO.class);
    private final CustomerService customerService = new CustomerService(particulierDAO, bedrijfDAO);

    public CustomerServiceUnitTest() { super(); }

    // test een bestaande particuliere gebruikersnaam
    @Test
    void doesUsernameExist1() {
        Mockito.when(particulierDAO.getOneByGebruikersnaam("gebruikersnaam particulier"))
                .thenReturn(new ArrayList<>(List.of(new Particulier())));
        Mockito.when(bedrijfDAO.getOneByGebruikersnaam("gebruikersnaam particulier"))
                .thenReturn(new ArrayList<>());
        assertTrue(customerService.doesUsernameExist("gebruikersnaam particulier"));
    }

    // test een bestaande zakelijke gebruikersnaam
    @Test
    void doesUsernameExist2() {
        Mockito.when(particulierDAO.getOneByGebruikersnaam("gebruikersnaam bedrijf"))
                .thenReturn(new ArrayList<>());
        Mockito.when(bedrijfDAO.getOneByGebruikersnaam("gebruikersnaam bedrijf"))
                .thenReturn(new ArrayList<>(List.of(new Bedrijf())));
        assertTrue(customerService.doesUsernameExist("gebruikersnaam bedrijf"));
    }

    // test een onbestaande gebruikersnaam
    @Test
    void doesUsernameExist3() {
        Mockito.when(particulierDAO.getOneByGebruikersnaam("onbestaande gebruikersnaam"))
                .thenReturn(new ArrayList<>());
        Mockito.when(bedrijfDAO.getOneByGebruikersnaam("onbestaande gebruikersnaam"))
                .thenReturn(new ArrayList<>());
        assertFalse(customerService.doesUsernameExist("onbestaande gebruikersnaam"));
    }

    // test een bestaand BSN
    @Test
    void doesBsnExist() {
        Mockito.when(particulierDAO.getOneByBSN(123456789)).thenReturn(new ArrayList<>(List.of(new Particulier())));
        assertTrue(customerService.doesBsnExist(123456789));
    }

    // test een onbestaand BSN
    @Test
    void doesBsnExist2() {
        Mockito.when(particulierDAO.getOneByBSN(234567890)).thenReturn(new ArrayList<>());
        assertFalse(customerService.doesBsnExist(234567890));
    }

    // test of particulierDAO.storeOne(klant) wordt aangeroepen als de klant een particulier is
    @Test
    void storeCustomer1() {
        ArgumentCaptor<Particulier> argumentCaptor = ArgumentCaptor.forClass(Particulier.class);
        doNothing().when(particulierDAO).storeOne(argumentCaptor.capture());
        Klant klant = new Particulier();
        customerService.storeCustomer(klant);
        assertEquals(klant, argumentCaptor.getValue());
    }

    // test of bedrijfDAO.storeOne(klant) wordt aangeroepen als de klant een bedrijf is
    @Test
    void storeCustomer2() {
        ArgumentCaptor<Bedrijf> argumentCaptor = ArgumentCaptor.forClass(Bedrijf.class);
        doNothing().when(bedrijfDAO).storeOne(argumentCaptor.capture());
        Klant klant = new Bedrijf();
        customerService.storeCustomer(klant);
        assertEquals(klant, argumentCaptor.getValue());
    }

    // test of particulierDAO.updateOne(klant) wordt aangeroepen als de klant een particulier is
    @Test
    void changeCustomer1() {
        ArgumentCaptor<Particulier> argumentCaptor = ArgumentCaptor.forClass(Particulier.class);
        doNothing().when(particulierDAO).updateOne(argumentCaptor.capture());
        Klant klant = new Particulier();
        customerService.changeCustomer(klant);
        assertEquals(klant, argumentCaptor.getValue());
    }

    // test of bedrijfDAO.updateOne(klant) wordt aangeroepen als de klant een bedrijf is
    @Test
    void changeCustomer2() {
        ArgumentCaptor<Bedrijf> argumentCaptor = ArgumentCaptor.forClass(Bedrijf.class);
        doNothing().when(bedrijfDAO).updateOne(argumentCaptor.capture());
        Klant klant = new Bedrijf();
        customerService.changeCustomer(klant);
        assertEquals(klant, argumentCaptor.getValue());
    }

    // test een bestaande particuliere gebruikersnaam-wachtwoordcombinatie
    @Test
    void getKlantenbyGebruikersnaamWachtwoord1() {
        Mockito.when(particulierDAO.getOneByGebruikersnaamWachtwoord(
                "gebruikersnaam particulier", "wachtwoord particulier"))
                .thenReturn(new ArrayList<>(List.of(new Particulier())));
        Mockito.when(bedrijfDAO.getOneByGebruikersnaamWachtwoord(
                "gebruikersnaam particulier", "wachtwoord particulier"))
                .thenReturn(new ArrayList<>());
        assertTrue(customerService.getKlantenbyGebruikersnaamWachtwoord(
                "gebruikersnaam particulier", "wachtwoord particulier")
                .get(0) instanceof Particulier);
    }

    // test een bestaande zakelijke gebruikersnaam-wachtwoordcombinatie
    @Test
    void getKlantenbyGebruikersnaamWachtwoord2() {
        Mockito.when(particulierDAO.getOneByGebruikersnaamWachtwoord(
                "gebruikersnaam particulier", "wachtwoord bedrijf"))
                .thenReturn(new ArrayList<>());
        Mockito.when(bedrijfDAO.getOneByGebruikersnaamWachtwoord(
                "gebruikersnaam particulier", "wachtwoord bedrijf"))
                .thenReturn(new ArrayList<>(List.of(new Bedrijf())));
        assertTrue(customerService.getKlantenbyGebruikersnaamWachtwoord(
                "gebruikersnaam particulier", "wachtwoord bedrijf")
                .get(0) instanceof Bedrijf);
    }

    // test een onbestaande gebruikersnaam-wachtwoordcombinatie
    @Test
    void getKlantenbyGebruikersnaamWachtwoord3() {
        Mockito.when(particulierDAO.getOneByGebruikersnaamWachtwoord(
                "onbestaande gebruikersnaam", "onbestaand wachtwoord"))
                .thenReturn(new ArrayList<>());
        Mockito.when(bedrijfDAO.getOneByGebruikersnaamWachtwoord(
                "onbestaande gebruikersnaam", "onbestaand wachtwoord"))
                .thenReturn(new ArrayList<>());
        assertTrue(customerService.getKlantenbyGebruikersnaamWachtwoord(
                "onbestaande gebruikersnaam", "onbestaand wachtwoord")
                .isEmpty());
    }
}