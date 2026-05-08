package alchemy;

import java.util.ArrayList;
import java.util.List;
import alchemy.IngredientType;
import be.kuleuven.cs.som.annotate.*;

/**
 * @author Joran Naessens
 * @author Maxime Samyn
 */
public class AlchemicIngredient{
    private IngredientType type

    /**********************************************************
     * CONSTRUCTORS
     **********************************************************/

    /**
     * onze basisconstructor, wordt alleen intern gebruikt
     *
     * @param type
     * @param quantity
     * @param name
     * @param temperature
     * @param state
     *
     */
    public AlchemicIngredient(IngredientType type, int quantity, String name, int temperature, STATE state){
        setIngredientType(type);
        setQuantity(quantity);
        setSimpleName(name);
        setTemperature(temperature);
        setSTATE(state);
    }

    public AlchemicIngredient(IngredientType type, int quantity, String name){
        this(type,quantity,name,0,null);
    }

    /**********************************************************
     * INGREDIENT TYPE
     **********************************************************/

    private IngredientType ingredientType = null;


    /**********************************************************
     * NAME [SIMPLE]
     **********************************************************/

    /**
     - one or more words, separated by spaces
     - no numbers or special chars
     - only ' and () as symbols
     - at least three letters for single word
     - at least two letters for multiple words
     - every word starts with an uppercase or symbol except for the words "mixed" and "with"
     */
    private String simpleName = "";

    // zonder bijvoegsels
    public String getSimpleName() {
        return simpleName;
    }

    /** [WIP]
     * setter for the simpleName attribute of the class
     *
     * @param simpleName
     * @pre k
     * @post k
     */
    public  void setSimpleName(String simpleName) {
        if (isValidName(simpleName)) {
            this.simpleName = simpleName;
        }
        else{throw new IllegalArgumentException("Invalid simple name: "+simpleName);}
    }

    /** [WIP]
     * Static method
     *
     * @return String[] containing words that may not start with an uppercase char
     */
    private static List<String> getExceptionWords() {
        return List.of(new String[]{"mixed", "with"});
    }



    /**********************************************************
     * NAME [FULL]
     **********************************************************/

    private List<String> prefixes = new ArrayList<>();
    private List<String> suffixes = new ArrayList<>();


    public List<String> getPrefixes() {
        return prefixes;
    }

    public List<String> getSuffixes() {
        return suffixes;
    }

    public void setPrefixes(List<String> prefixes) {
        this.prefixes = prefixes;
    }

    public void setSuffixes(List<String> suffixes) {
        this.suffixes = suffixes;
    }

    /**
     * [WIP]
     * add a prefix to the current ingredient's prefix list
     *
     * @param prefix prefix to add
     */
    public void addPrefix(String prefix) {
        this.prefixes.add(prefix);
    }

    /**
     * [WIP]
     * add a suffix to the current ingredient's suffix list
     *
     * @param suffix to add
     */
    public void addSuffix(String suffix) {
        this.suffixes.add(suffix);
    }

    /**
     * Make the full name of the ingredient including all statuses
     *
     * @return
     */
    public String getFullName() {

        String prefixes = "";
        String suffixes = "";

        for (String prefix : getPrefixes()) {
            prefixes += prefix;
        }
        for (String suffix : getSuffixes()) {
            suffixes += suffix;
        }

        if (this.isMix()){
            if (this.getSpecialName() != null){
                return (this.getSpecialName()+" ("+prefixes+this.getSimpleName()+suffixes+")");
            }
            else{return prefixes+suffixes;}
        }
        else{
            return (prefixes+this.getSimpleName()+suffixes);
        }
    }

    /**********************************************************
     * MIXING
     **********************************************************/

    private List<AlchemicIngredient> mixedIngredients = null;

    /**
     * getter for this.mixedIngredients
     */
    private List<AlchemicIngredient> getMixedIngredients() {return mixedIngredients;}

    /**
     * function that checks whether the current ingredient already is a mix of multiple ingredients
     *
     * @pre this.mixedIngredients >= 1
     *
     * @return true if and only if there is more than one ingredient in the mixed ingredients list
     */
    private boolean isMix(){
        if (this.getMixedIngredients().size() > 1){
            return true;}
        else {return false;}
    }

    /** [WIP]
     * returns the special name of the mix if one has been predefined.
     *
     * @return String specialName or null depending on whether SPECIALNAMES.exists()
     */
    public String getSpecialName() {return null;}

}