package com.sweng_stories.stories_manager.restcontrollers;

import com.sweng_stories.stories_manager.domain.Inventario;
import com.sweng_stories.stories_manager.domain.Scenario;
import com.sweng_stories.stories_manager.domain.SessioneGioco;
import com.sweng_stories.stories_manager.services.OpSessioneGioco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/sessioni")
public class SessioneGiocoController {

    @Autowired
    private OpSessioneGioco serviceSessioneGioco;

    @PutMapping("/SessioneGioco/{idSessione}/scenari/{idScenario}/indovinello")
    public Scenario elaboraIndovinello(
            @PathVariable int idScenario,
            @RequestParam String risposta,
            @PathVariable String idSessione
            ) {
        return serviceSessioneGioco.elaboraIndovinello(idScenario, risposta, idSessione);
    }

    @PutMapping("/SessioneGioco/{idSessione}/scenari/{idScenario}/alternativa/{idScenarioDiPartenza}")
    public Scenario elaboraAlternativa(
        @PathVariable int idScenario,
        @RequestParam String testoAlternativa,
        @PathVariable String idSessione,
        @PathVariable int idScenarioDiPartenza) {
                System.out.println("\n\n\n\nID SCEEENARIOOOOO "+idScenario);
                Scenario scenario = serviceSessioneGioco.elaboraAlternativa(idScenarioDiPartenza, idScenario, testoAlternativa, idSessione);
        return scenario;
    }

    @PostMapping("/SessioneGioco/{idSessione}/inventario")
    public Inventario raccogliOggetto(
            @PathVariable String idSessione,
            @RequestParam String oggetto) {
        return serviceSessioneGioco.raccogliOggetto(idSessione, oggetto);
    }

    @PostMapping("/SessioneGioco/")
    public SessioneGioco creaSessione(@RequestBody SessioneGioco partita) {
        return serviceSessioneGioco.creaSessione(partita);
    }

    @DeleteMapping("/SessioneGioco/{idSessione}")
    public boolean eliminaSessione(@PathVariable String idSessione) {
        return serviceSessioneGioco.eliminaSessione(idSessione);
    }

    @GetMapping("/SessioneGioco/utente/{username}")
    public ArrayList<SessioneGioco> getSessioniUtente(@PathVariable String username) {
        return serviceSessioneGioco.getSessioniUtente(username);
    }

    @GetMapping("/SessioneGioco/{idSessione}")
    public SessioneGioco getSessioneConID(@PathVariable String idSessione) {
        return serviceSessioneGioco.getSessioneConID(idSessione);
    }
}
