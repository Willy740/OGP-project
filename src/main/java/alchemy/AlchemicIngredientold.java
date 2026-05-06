package alchemy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joran Naessens
 * @author Maxime Samyn
 */
public class AlchemicIngredientold {

    /**********************************************************
     * NAME [SIMPLE]
     **********************************************************/

    /** NAAMGEVING REGELS **/

    //De naam van een ingrediënt bestaat
    //uit één of meerdere woorden gescheiden door spaties. Woorden mogen geen cijfers, speciale
    //tekens of leestekens bevatten, buiten het afkappingsteken en ronde haakjes. Elk woord in
    //de naam van een ingrediënt bestaat uit minstens twee letters, tenzij het gaat om een naam
    //met slechts één woord; in dat geval moet dit woord uit minstens drie letters bestaan. Elk
    //nieuw woord in de naam begint met een hoofdletter of toegelaten speciaal teken, en de
    //andere letters in elk woord zijn kleine letters. Er zijn echter een paar uitzonderingswoorden
    //die deze hoofdletter niet krijgen: dit zijn ‘mixed’ en ‘with’. Deze woorden zijn niet toegelaten in de naam van een element.

    //The name of an ingredient consists of one or more words separated by spaces. Words may not contain numbers, special characters, or punctuation marks, except for the apostrophe and parentheses. Each word in the name of an ingredient consists of at least two letters, unless it is a single-word name; in that case, this word must consist of at least three letters. Each new word in the name begins with a capital letter or permitted special character, and the other letters in each word are lowercase. However, there are a few exception words that do not receive this capital letter: these are ‘mixed’ and ‘with’. These words are not permitted in the name of an ingredient. The name of an ingredient consists of one or more words separated by spaces. Words may not contain numbers, special characters, or punctuation marks, except for the apostrophe and parentheses. Each word in the name of an ingredient consists of at least two letters, unless it is a single-word name; in that case, this word must consist of at least three letters. Each new word in the name begins with a capital letter or permitted special character, and the other letters in each word are lowercase. However, there are a few exception words that do not take this capital letter: these are ‘mixed’ and ‘with’. These words are not allowed in the name of an element.



    private final String simpleName;

    // zonder bijvoegsels
    public String getSimpleName() {
        return simpleName;
    }

    /**
     * Static method
     *
     * @return String[] containing words that may not start with an uppercase char
     */
    private static String[] getExceptionWords() {
        return new String[]{"mixed", "with"};
    }

    /**********************************************************
     * NAME [FULL]
     **********************************************************/

    //    De volledige naam van een mengsel is nu de speciale naam gevolgd door, tussen haakjes, de
    //    eenvoudige naam aangevuld met eventuele bijvoegsels. Wanneer echter geen speciale naam
    //    werd toegekend aan een mengsel, dan wordt de volledige naam op dezelfde manier gevormd
    //    als bij niet-gemengde ingrediënten. Bij mengsels moeten dus drie namen opvraagbaar zijn:
    //    de eenvoudige, de volledige en de speciale naam.

    //    MISSCHIEN ENUM CLASS MAKEN ME ALLE PREFIXES/SUFFIXES

    private List<String> prefixes = new ArrayList<>();
    private List<String> suffixes = new ArrayList<>();
//    private final String IngredientName;

//    public String getIngredientName() {
//        return IngredientName;
//    }

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
     * prefix toevoegen
     * @param prefix
     */
    public void addPrefix(String prefix){
        prefixes.add(prefix);
    }

    /**
     * suffix toevoegen
     * @param suffix
     */
    public void addSuffix(String suffix){
        suffixes.add(suffix);
    }

    /**
     * maakt volledige naam van een mengsel
     * @param mixName
     * @param mix
     * @return
     */
    public String getFullMixName(String mixName,String mix) {
        //if (mixName == null || mixName.length() == 0){
        // GEVAL DAT ER GEEN SPECIALE NAAM IS
        // WAT MOET ER DAN GEBEUREN? => er zal toch een naam aangemaakt worden (zie grote comment vanboven)
        // NU ZAL ER EEN EXCEPTION GEGOOID WORDEN ALS ER GEEN SPECIALE NAAM IS
        if  (!isValidMixName(mixName,mix)) {
        throw new IllegalArgumentException("ongeldige mixnaam: " + mixName + "of ingredienten in de mix: " + mix);
    }
        return mixName + "(" + mix + ")";
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

    /**
     * kijkt voor een mix van bepaalde ingredienten of de mixName een valid naam is
     * @param mixName
     * @param mix
     * @return
     */
    public static boolean isValidMixName(String mixName, String mix) {
        if (!isValidMix(mix)) {
            return false;
        }
        if(!isValidIngredientName(mixName)){
            return false;
        }
        return true;
    }

    /**
     * kijkt of de twee ingredienten van de mix valid ingredienten zijn
     * @param mix
     * @return
     */
    public static boolean isValidMix(String mix) {
        if (mix == null || mix.length() == 0) {
            return false;
        }
        String[] ingredients = mix.split(" mixed with ");
        for  (String word : ingredients) {
            if (!isValidIngredientName(word)){
                return false;
            }
        }
        return true;
    }

    /**********************************************************
     * NAME [SPECIAL]
     **********************************************************/

    /** ZELFDE NAAMGEVING REGELS ALS <Name [SIMPLE]> **/

    private String specialName = "";

    /**
     * function that sets the specialName of the current ingredient.
     *
     * @post sets this.specialName to the output of checkSpecialName
     */
    private void setSpecialName() {
        this.specialName = checkSpecialName();
    }

    /**
     * [WIP]
     * function that checks if the current mix and status has a special name.
     * if so, return it.
     *
     * @return specialName or empty String
     */
    private String checkSpecialName(){return "";}

    /**********************************************************
     * CONSTRUCTORS
     **********************************************************/

    /**
     * constructor
     *
     * @param simpleName
     * @param IngredientName
     */
    public AlchemicIngredient(String simpleName, String IngredientName,String mix, String mixName, int quantity) {
        if (!isValidSimpleName(simpleName)) {
            throw new IllegalArgumentException("ongeldige eenvoudige naam: " + simpleName);
        }
        this.simpleName = simpleName;
        if (!isValidIngredientName(IngredientName)) {
            throw new IllegalArgumentException("ongeldige ingredientnaam: " + IngredientName);
        }
        this.IngredientName = IngredientName;
        if(!isValidMix(mix)){
            throw new IllegalArgumentException("ongeldige ingredienten in de mix: " + mix);
        }
        this.mix = mix;
        if(!isValidMixName(mixName,mix)){
            throw new IllegalArgumentException("ongeldige mixnaam: " + mixName + "of ongeldige ingredienten in de mix: " + mix );
        }
        this.mixName = mixName;

        // Quantity nominaal
        this.setQuantity(quantity);
    }

    public static boolean isValidSimpleName(String simpleName) {
        if (simpleName == null || simpleName.length() == 0) {
            return false;
        }
        String[] words = simpleName.split(" ");
        String kleine ="abcdefghijklmnopqrstuvwxyz";
        String hoofdletters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String tekens = "(')";

        if ((words.length == 1) && (countLetters(simpleName)<3)) {
            return false;
        }
        for (String word : words) {
            if (word.isEmpty() || word.length() == 0) {
                return false;
            }
            else if ((suffixes.contains(word)) || (prefixes.contains(word))){
                return false;
            }
            else if ((words.length != 1) && (countLetters(word)<2)) {
                return false;
            }
  //          else  {                                           //
  //              boolean isException = false;                  //
  //              for (String exceptionWord : ExeptionWords) {  //denk niet dat dit hier hoort, wat denken jullie?
  //                  if (exceptionWord.equals(word)) {         //
  //                      isException = true;                   //
  //                      break;                                //
  //                  }                                         //
  //              }                                             //

                if (isException) {
                    // alle letters moeten kleine letters zijn
                    for (int i = 0; i < word.length(); i++) {
                        if (kleine.indexOf(word.charAt(i)) == -1) {
                            return false;
                        }
                    }
                } else {
                    // gewoon woord: eerste letter hoofdletter/teken
                    if (hoofdletters.indexOf(word.charAt(0)) == -1 && tekens.indexOf(word.charAt(0)) == -1) {
                        return false;
                    }
                    // overige letters: kleine letters of tekens
                    for (int i = 1; i < word.length(); i++) {
                        if (kleine.indexOf(word.charAt(i)) == -1 && tekens.indexOf(word.charAt(i)) == -1) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * telt letters in woord
     * @param word
     * @return
     */
    private static int countLetters(String word) {
        int count = 0;
        for (char c : word.toCharArray()) {
            if (Character.isLetter(c)) {
                count++;
            }
        }
        return count;
    }

    /**
     * kijken of het een geldig ingredientennaam is
     * @param IngredientName
     * @return
     */
    public static boolean isValidIngredientName(String IngredientName) {
        if (IngredientName == null || IngredientName.length() == 0) {
            return false;
        }
        String[] words = IngredientName.split(" ");
        List<String> kopie = new ArrayList<>(Arrays.asList(words));

        kopie.removeIf(word -> prefixes.contains(word) || suffixes.contains(word));

        String resultaat = String.join(" ", kopie);
        if (!isValidSimpleName(resultaat)) {
            return false;
        }
        return true;
    }

    /**********************************************************
     * QUANTITY
     **********************************************************/

    // Quantity moet nominaal worden geïmplementeerd
    private int Quantity;

    /**
     * @pre quantity needs to be positive
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.Quantity=quantity
    }
    public int getQuantity() {
        return Quantity;
    }

    /**********************************************************
     * STATE
     **********************************************************/
