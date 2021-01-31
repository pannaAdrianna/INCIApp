package edu.ib.inciapp;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    String nameDeck;
    List<Flashcard> deck=new ArrayList<>();
    boolean deckRepeated=false;


    public Deck(String nameDeck, List<Flashcard> deck, boolean deckRepeated) {
        this.nameDeck = nameDeck;
        this.deck = deck;
        this.deckRepeated = deckRepeated;
    }

    public String getNameDeck() {
        return nameDeck;
    }

    public void setNameDeck(String nameDeck) {
        this.nameDeck = nameDeck;
    }

    public List<Flashcard> getDeck() {
        return deck;
    }

    public void setDeck(List<Flashcard> deck) {
        this.deck = deck;
    }

    public boolean isDeckRepeated() {
        return deckRepeated;
    }

    public void setDeckRepeated(boolean deckRepeated) {
        this.deckRepeated = deckRepeated;
    }
}
