package com.sweng_stories.stories_manager.domain;

import java.util.ArrayList;

public class Indovinello{
    private int idScenario;
    private int idScenarioRispGiusta;
    private String testoIndovinello;
    private String risposta;
    private ArrayList<String> opzioni;
    private int idScenarioRispSbagliata;

    public Indovinello() {
    }

    public Indovinello(int idScenario, int idScenarioRispGiusta, String testoIndovinello, String risposta, ArrayList<String> opzioni, int idScenarioRispSbagliata) {
        this.idScenario = idScenario;
        this.idScenarioRispGiusta = idScenarioRispGiusta;
        this.testoIndovinello = testoIndovinello;
        this.risposta = risposta;
        this.opzioni = opzioni;
        this.idScenarioRispSbagliata = idScenarioRispSbagliata;
    }

    public void setIdScenario(int idScenario) {
        this.idScenario = idScenario;
    }

    public void setIdScenarioRispGiusta(int idScenarioRispGiusta) {
        this.idScenarioRispGiusta = idScenarioRispGiusta;
    }

    public void setTestoIndovinello(String testoIndovinello) {
        this.testoIndovinello = testoIndovinello;
    }

    public void setRisposta(String risposta) {
        this.risposta = risposta;
    }

    public void setOpzioni(ArrayList<String> opzioni) {
        this.opzioni = opzioni;
    }

    public void setIdScenarioRispSbagliata(int idScenarioRispSbagliata) {
        this.idScenarioRispSbagliata = idScenarioRispSbagliata;
    }

    public String getTestoIndovinello() {
        return testoIndovinello;
    }

    public String getRisposta() {
        return risposta;
    }

    public int getIdScenario() {
        return idScenario;
    }
    public ArrayList<String> getOpzioni() {
        return opzioni;
    }

    public int getIdScenarioRispGiusta() {
        return idScenarioRispGiusta;
    }

    public int getIdScenarioRispSbagliata() {
        return idScenarioRispSbagliata;
    }
}
