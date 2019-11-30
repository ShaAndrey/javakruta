package com.example.gogot.model.entity;

public class InHandCard extends PlayCard {
    private int amount = 0;
    private boolean dominatedState;

    public InHandCard(int index) {
        super(index);
    }

    public InHandCard(State state) {
        super(state);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addToAmount(int add) {
        this.amount += add;
    }

    public void setDominatedStates(boolean dominatedState) {
        this.dominatedState = dominatedState;
    }

    public boolean getDominatedStates() {
        return dominatedState;
    }


}
