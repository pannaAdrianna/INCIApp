package edu.ib.inciapp;

public class Flashcard {
    private int id;
   final String definition;
   final String label;
   private boolean remembered;


    public Flashcard(int id, String definition, String label) {
        this.id = id;
        this.definition = definition;
        this.label = label;
        this.remembered = false;
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


}
