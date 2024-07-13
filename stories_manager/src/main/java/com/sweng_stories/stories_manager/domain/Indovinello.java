package com.sweng_stories.stories_manager.domain;

public abstract class Indovinello {
    private Long id;
    private String descrizione;

    public abstract boolean verificaRisultato(Object risposta);
    
    // Costruttori e getter/setter
    public Indovinello(Long id, String descrizione) {
        this.id = id;
        this.descrizione = descrizione;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
}