package com.kazakovproduction.gogot.model.game.entity;

public class InHandCard extends PlayCard {
    private int amount = 0;
    private boolean dominatesState;

    public InHandCard(int index) {
        super(index);
    }

    public InHandCard(State state) {
        super(state);
    }

    public InHandCard(InHandCard card) {
        super(card);
        amount = card.amount;
        dominatesState = card.dominatesState;
    }

    public void setInHandCard(InHandCard card) {
        amount = card.amount;
        dominatesState = card.dominatesState;
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

    public void setDominatesState(boolean dominatedState) {
        this.dominatesState = dominatedState;
    }

    public boolean getDominatesState() {
        return dominatesState;
    }


}
