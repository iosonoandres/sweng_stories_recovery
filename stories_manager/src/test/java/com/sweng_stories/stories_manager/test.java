import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

// IndovinelloNumericoTest.java
public class IndovinelloNumericoTest {

    @Test
    public void testVerificaRisultato() {
        IndovinelloNumerico indovinello = new IndovinelloNumerico(1L, "Quanto fa 2 + 2?");
        indovinello.setRispostaCorretta(4);
        
        assertTrue(indovinello.verificaRisultato(4));
        assertFalse(indovinello.verificaRisultato(5));
        assertFalse(indovinello.verificaRisultato("4"));
    }

    @Test
    public void testGetterSetter() {
        IndovinelloNumerico indovinello = new IndovinelloNumerico(1L, "Test descrizione");
        indovinello.setRispostaCorretta(42);
        
        assertEquals(1L, indovinello.getId());
        assertEquals("Test descrizione", indovinello.getDescrizione());
        assertEquals(42, indovinello.getRispostaCorretta());
    }
}

// IndovinelloTestualeTest.java
public class IndovinelloTestualeTest {

    @Test
    public void testVerificaRisultato() {
        IndovinelloTestuale indovinello = new IndovinelloTestuale(1L, "Di che colore è il cielo?");
        indovinello.setRispostaCorretta("blu");
        
        assertTrue(indovinello.verificaRisultato("blu"));
        assertTrue(indovinello.verificaRisultato("Blu"));
        assertFalse(indovinello.verificaRisultato("rosso"));
    }

    @Test
    public void testGetterSetter() {
        IndovinelloTestuale indovinello = new IndovinelloTestuale(1L, "Test descrizione");
        indovinello.setRispostaCorretta("testo");
        
        assertEquals(1L, indovinello.getId());
        assertEquals("Test descrizione", indovinello.getDescrizione());
        assertEquals("testo", indovinello.getRispostaCorretta());
    }
}

// InventarioTest.java
public class InventarioTest {

    @Test
    public void testGetterSetter() {
        Oggetto oggetto1 = new Oggetto(1L, "Spada", "Una spada affilata");
        Oggetto oggetto2 = new Oggetto(2L, "Scudo", "Uno scudo resistente");
        List<Oggetto> oggetti = new ArrayList<>();
        oggetti.add(oggetto1);
        oggetti.add(oggetto2);
        
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setOggetti(oggetti);
        
        assertEquals(1L, inventario.getId());
        assertEquals(2, inventario.getOggetti().size());
    }
}

// OggettoTest.java
public class OggettoTest {

    @Test
    public void testGetterSetter() {
        Oggetto oggetto = new Oggetto(1L, "Pozione", "Una pozione di cura");
        
        assertEquals(1L, oggetto.getId());
        assertEquals("Pozione", oggetto.getNome());
        assertEquals("Una pozione di cura", oggetto.getDescrizione());
        
        oggetto.setNome("Pozione Magica");
        assertEquals("Pozione Magica", oggetto.getNome());
    }
}

// ScenarioTest.java
public class ScenarioTest {

    @Test
    public void testGetterSetter() {
        Scenario scenario = new Scenario();
        scenario.setId(1L);
        scenario.setDescrizione("Un scenario di prova");
        
        assertEquals(1L, scenario.getId());
        assertEquals("Un scenario di prova", scenario.getDescrizione());
    }
}

// SessioneGiocoTest.java
public class SessioneGiocoTest {

    @Test
    public void testGetterSetter() {
        Utente utente = new Utente("utente1", "password");
        Storia storia = new Storia(1L, "Titolo", "Descrizione", new Scenario());
        Inventario inventario = new Inventario();
        
        SessioneGioco sessione = new SessioneGioco(utente, storia, inventario);
        
        assertEquals(utente, sessione.getUtente());
        assertEquals(storia, sessione.getStoriaCorrente());
        assertEquals(inventario, sessione.getInventario());
        
        sessione.setStoriaCorrente(new Storia(2L, "Titolo2", "Descrizione2", new Scenario()));
        assertEquals(2L, sessione.getStoriaCorrente().getId());
    }
}

// StoriaTest.java
public class StoriaTest {

    @Test
    public void testAggiungiScenario() {
        Storia storia = new Storia();
        Scenario scenario = new Scenario();
        storia.aggiungiScenario(scenario);
        
        assertEquals(1, storia.getScenari().size());
    }
    
    @Test
    public void testAggiungiFinale() {
        Storia storia = new Storia();
        Scenario finale = new Scenario();
        storia.aggiungiFinale(finale);
        
        assertEquals(1, storia.getFinali().size());
    }
}

// UtenteTest.java
public class UtenteTest {

    @Test
    public void testAggiungiStoriaScritta() {
        Utente utente = new Utente("username", "password");
        Storia storia = new Storia();
        utente.aggiungiStoriaScritta(storia);
        
        assertEquals(1, utente.getStorieScritte().size());
    }
    
    @Test
    public void testAggiungiStoriaGiocata() {
        Utente utente = new Utente("username", "password");
        Storia storia = new Storia();
        utente.aggiungiStoriaGiocata(storia);
        
        assertEquals(1, utente.getStorieGiocate().size());
    }
}
