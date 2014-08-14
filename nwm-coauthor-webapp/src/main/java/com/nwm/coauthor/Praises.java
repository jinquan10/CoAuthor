package com.nwm.coauthor;

import com.nwm.coauthor.service.util.DisplayName;

public class Praises {
    @DisplayName("Hahaha!")
    private Praise hahaha = new Praise();

    @DisplayName("Witty")
    private Praise witty = new Praise();
    
    @DisplayName("Spontaneous")
    private Praise spontaneous = new Praise();

    @DisplayName("Outrageous")
    private Praise outrageous = new Praise();
    
    @DisplayName("Made my day")
    private Praise madeMyDay = new Praise();
    
    @DisplayName("Romantic")
    private Praise romantic = new Praise();
    
    @DisplayName("Critical Thinking")
    private Praise criticalThinking = new Praise();

    public Praise getHahaha() {
        return hahaha;
    }

    public void setHahaha(Praise hahaha) {
        this.hahaha = hahaha;
    }

    public Praise getWitty() {
        return witty;
    }

    public void setWitty(Praise witty) {
        this.witty = witty;
    }

    public Praise getSpontaneous() {
        return spontaneous;
    }

    public void setSpontaneous(Praise spontaneous) {
        this.spontaneous = spontaneous;
    }

    public Praise getOutrageous() {
        return outrageous;
    }

    public void setOutrageous(Praise outrageous) {
        this.outrageous = outrageous;
    }

    public Praise getMadeMyDay() {
        return madeMyDay;
    }

    public void setMadeMyDay(Praise madeMyDay) {
        this.madeMyDay = madeMyDay;
    }

    public Praise getRomantic() {
        return romantic;
    }

    public void setRomantic(Praise romantic) {
        this.romantic = romantic;
    }

    public Praise getCriticalThinking() {
        return criticalThinking;
    }

    public void setCriticalThinking(Praise criticalThinking) {
        this.criticalThinking = criticalThinking;
    }
}
