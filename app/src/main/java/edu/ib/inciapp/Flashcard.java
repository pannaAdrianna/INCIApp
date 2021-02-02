package edu.ib.inciapp;

public class Flashcard {
    private int id;
    final String label;
    final String description;
    private boolean remembered;


    public Flashcard(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public Flashcard(int id, String label, String description, boolean remembered) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.remembered = remembered;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
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
