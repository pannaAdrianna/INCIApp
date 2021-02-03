package edu.ib.inciapp;

public class Flashcard {
    private String label;
    final String function;
    final String description;

    public Flashcard(String label, String function, String description) {
        this.label = label;
        this.function = function;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getLabel() {
        return label;
    }

    public String getFunction() {
        return function;
    }

    public String getBackgroundDescrpition(){
        return function+" "+description;
    }

    @Override
    public String toString() {
        return "Label:" + label + "\nfunction: " + function + "description: " + description;

    }
}
