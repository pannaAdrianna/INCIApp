package edu.ib.inciapp;

/**
 * class creats a Flashcard object
 */
public class Flashcard {
    private String label;
    final String function;
    final String description;

    /**
     *
     * @param label name of ingredient
     * @param function function of ingredient
     * @param description reservations
     */
    public Flashcard(String label, String function, String description) {
        this.label = label;
        this.function = function;
        this.description = description;
    }


    /**
     * method gets label
     * @return label name of infredient
     */
    public String getLabel() {
        return label;
    }

    /**
     * method returns string
     * @return string of function, description
     */
    public String getBackgroundDescrpition(){
        return function+", "+description;
    }

    /**
     * method returns Flashcard
     * @return flashcard properties as string
     */
    @Override
    public String toString() {
        return "Label:" + label + "\nfunction: " + function + "\ndescription: " + description;

    }
}
