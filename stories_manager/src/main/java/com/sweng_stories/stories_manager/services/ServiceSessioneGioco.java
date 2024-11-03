package com.sweng_stories.stories_manager.services;

import com.sweng_stories.stories_manager.dao.OpSessioneGiocoDao;
import com.sweng_stories.stories_manager.dao.OpStoriaDao;
import com.sweng_stories.stories_manager.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ServiceSessioneGioco implements OpSessioneGioco {
    @Autowired
    OpSessioneGiocoDao sessioneGiocoDao;

    @Autowired
    OpStoriaDao sessioneStoriaDao;

    @Override
    public Scenario elaboraIndovinello(int idScenario, String risposta, String idSessione) {
        SessioneGioco partita = sessioneGiocoDao.getSessioneConID(idSessione);

        if(partita == null){
            return null;
        }

        int idStoria = partita.getIdStoria();

        Scenario scenario = sessioneStoriaDao.getScenario(idScenario,idStoria);
        Indovinello indovinello = scenario.getIndovinello();

        if(indovinello == null)
            return null;

        boolean esito = indovinello.getRisposta().equals(risposta);

        if(esito){
            int idScenarioSuccessivo = indovinello.getIdScenarioRispGiusta(); //dipende da getRispostaCorretta.scenarioId

            System.out.println("RISPOSTA INDOVINELLo" + indovinello.getRisposta());
            System.out.println("RISPOSTA INDOVINELLo" + indovinello.getRisposta());
            System.out.println("RISPOSTA INDOVINELLo" + indovinello.getRisposta());

            Scenario scenarioSuccessivo = sessioneStoriaDao.getScenario(idScenarioSuccessivo,idStoria);
            partita.setIdScenarioCorrente(scenarioSuccessivo.getIdScenario());
            sessioneGiocoDao.aggiornaSessione(partita);

            return scenarioSuccessivo;
        }else{
            int idScenarioErrato = indovinello.getIdScenarioRispSbagliata(); //dipende da getRispostaSbagliata.scenarioId da correggere

            Scenario scenarioSuccessivo = sessioneStoriaDao.getScenario(idScenarioErrato,idStoria);
            partita.setIdScenarioCorrente(scenarioSuccessivo.getIdScenario());
            sessioneGiocoDao.aggiornaSessione(partita);

            return scenarioSuccessivo;
        }
    }

    @Override
    public Scenario elaboraAlternativa(int idScenarioDiPartenza, int idScenario,
                                       String testoAlternativa, String idSessione) {
        SessioneGioco partita = sessioneGiocoDao.getSessioneConID(idSessione);

       

        if(partita == null)
            return null;

        int idStoria = partita.getIdStoria();

        Scenario scenario = sessioneStoriaDao.getScenario(idScenarioDiPartenza,idStoria);


        Alternativa alternativa = null;

        System.out.println(scenario);

        for(Alternativa alt : scenario.getAlternative()){
            System.out.println(alt.getTestoAlternativa());
            if(alt.getTestoAlternativa().equals(testoAlternativa)){
                alternativa=alt;
                System.out.println("ALTTTTT BACKKK" + alternativa);
                System.out.println("ALTTTTT BACKKK" + alternativa);
                System.out.println("ALTTTTT BACKKK" + alternativa);

            }
        }

        System.out.println("ALTTTTT BACKKK" + alternativa);
        

        String oggettoNecessario = alternativa.getOggettoRichiesto();

        if(!oggettoNecessario.isEmpty()){
            System.out.println("CIAOOOOO" );
            System.out.println("CIAOOOOO");
            System.out.println("CIAOOOOO");
    
            Inventario inventario = partita.getInventario();
            if(!inventario.getOggetti().contains(oggettoNecessario))
                return null;
        }

        int idScenarioSuccessivo = alternativa.getIdScenarioSuccessivo();

        Scenario scenarioSuccessivo = sessioneStoriaDao.getScenario(idScenarioSuccessivo,idStoria);
        System.out.println("SCENARIO SUCC" + scenarioSuccessivo);
        System.out.println(scenarioSuccessivo);
        System.out.println(scenarioSuccessivo);

        
        partita.setIdScenarioCorrente(scenarioSuccessivo.getIdScenario());
        sessioneGiocoDao.aggiornaSessione(partita);

        return scenarioSuccessivo;
    }

    @Override
    public Inventario raccogliOggetto(String idSessione, String oggetto) {
        SessioneGioco partita = sessioneGiocoDao.getSessioneConID(idSessione);

        if(partita == null)
            return null;

        partita.getInventario().raccogliOggetto(oggetto);

        boolean aggiornamento = sessioneGiocoDao.aggiornaSessione(partita);

        if(!aggiornamento)
            return null;

        return partita.getInventario();

    }

    @Override
    public SessioneGioco creaSessione(SessioneGioco partita) {
        return sessioneGiocoDao.creaSessione(partita);
    }

    @Override
    public boolean eliminaSessione(String idSessione) {
        return sessioneGiocoDao.eliminaSessione(idSessione);
    }

    @Override
    public ArrayList<SessioneGioco> getSessioniUtente(String username) {
        return sessioneGiocoDao.getSessioniUtente(username);
    }

    @Override
    public SessioneGioco getSessioneConID(String idSessione) {
        return sessioneGiocoDao.getSessioneConID(idSessione);
    }
}
