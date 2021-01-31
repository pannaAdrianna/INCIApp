package edu.ib.inciapp;

public class Flashcard {
    private int id;
   final String definition;
   final String label;
   private boolean remembered;
   private int repeated;


    public Flashcard(int id, String definition, String label, boolean remembered, int repeated) {
        this.id = id;
        this.definition = definition;
        this.label = label;
        this.remembered = remembered;
        this.repeated = repeated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDefinition() {
        return definition;
    }

    public String getLabel() {
        return label;
    }

    public boolean isRemembered() {
        return remembered;
    }

    public void setRemembered(boolean remembered) {
        this.remembered = remembered;
    }

    public int getRepeated() {
        return repeated;
    }

    public void setRepeated(int repeated) {
        this.repeated = repeated;
    }
}
