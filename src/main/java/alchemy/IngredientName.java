package alchemy;

import java.util.Arrays;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;

/**
 * a class representing the name of an ingredient type
 * a name consists of a simple name and can produce a full name
 * that includes a "Heated" or "Cooled" prefix depending on the
 * current temperature relative to the default temperature
 *
 * @invar   the simple name of each ingredient name must be valid
 *          | isValidSimpleName(getSimpleName())
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class IngredientName {
    /**
     * variable referencing the simple name of this ingredient name
     */
    private String simpleName;

/**
 * variable referencing the list of lowercase letters used in name validation
 */
    private List<String> lowercase = List.of(
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");

    /**
     * variable referencing the list of uppercase letters used in name validation
     */
    private List<String> uppercase = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

    /**
     * variable referencing the list of symbols used in name validation
     */
    private List<String> symbols = List.of("'", "(", ")");

    /**
     * variable referencing the words that may not appear in a simple name
     */
    private List<String> disallowedWords = List.of("with", "mixed");

    /**
     * variable referencing the temperature-related prefixes that can appear
     * in a full name
     */
    private List<String> characteristics = List.of("Heated", "Cooled");

    /**********************************************************
     * constructor
     **********************************************************/

    /**
     * initialize a new ingredient name with the given simple name
     *
     * @param   simpleName
     *          the simple name of this new ingredient name
     *
     * @post    the simple name of this ingredient name is set to the given name
     *          | new.getSimpleName().equals(simpleName)
     *
     * @throws  IllegalArgumentException
     *          the given simple name is not valid
     *          | !isValidSimpleName(simpleName)
     */
    public IngredientName(String simpleName) {
        if (simpleName == null || simpleName.length() == 0 || !isValidSimpleName(simpleName)) {
            throw new IllegalArgumentException("Invalid simple name: " + simpleName);
        }
        this.simpleName = simpleName;
    }

    /**
     * check whether the given string is a valid simple name for an ingredient
     *
     * when is a simpleName a valid name:
     * - a single-word name must be at least 3 characters long
     * - in a multi-word name, each word must be at least 2 characters long
     * - no word may be a disallowed word ("with" or "mixed")
     * - aach word must start with an uppercase letter
     * - all subsequent characters of each word must be lowercase letters
     *
     * @param   simpleName
     *          the string to check
     *
     * @return  false if the given string is not effective or is empty
     *          | if (simpleName == null || simpleName.isEmpty())
     *          | then result == false
     * @return  false if the name consists of a single word with fewer than 3 characters
     *          | if (simpleName.split(" ").length == 1
     *          |      && simpleName.split(" ")[0].length() < 3)
     *          | then result == false
     * @return  false if any word in a multi-word name has fewer than 2 characters,
     *          is a disallowed word, does not start with an uppercase letter,
     *          or contains non-lowercase letters after the first character
     *          | if (simpleName.split(" ").length >= 2)
     *          | then for each word in simpleName.split(" "):
     *          |   if (word.length() < 2
     *          |        || disallowedWords.contains(word)
     *          |        || !uppercase.contains(String.valueOf(word.charAt(0)))
     *          |        || (exists i in 1..word.length()-1 :
     *          |              !lowercase.contains(String.valueOf(word.charAt(i)))))
     *          |   then result == false
     */
    public boolean isValidSimpleName(String simpleName) {
        String[] words = simpleName.split(" ");
        if (words == null || words.length == 0) {
            return false;
        }
        if (words.length == 1) {
            String word = words[0];
            if (word.length() < 3) {
                return false;
            }
        }
        if (words.length >= 2) {
            for (String word : words) {
                if (word.length() < 2) {
                    return false;
                }
                if (disallowedWords.contains(word)) {
                    return false;
                }
                for (int i = 0; i < word.length(); i++) {
                    String letter = String.valueOf(word.charAt(i));
                    if (i == 0) {
                        if (!uppercase.contains(letter)) {
                            return false;
                        }
                    }
                    if (i != 0) {
                        if (!lowercase.contains(letter)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * return the simple name of this ingredient name
     */
    @Basic @Raw
    public String getSimpleName() {
        return this.simpleName;
    }
// voorlopig overbodig misschien later nog handig

//    public boolean isValidFullName(String fullName){
//        String[] words = fullName.split(" ");
//        int lenght = words.length;
//        lenght -= 1;
//        String word1 = words[0];
//        String lastWord = words[lenght];
//        if (characteristics.contains(word1)){
//            words.remove(word1);
//        }
//        if (characteristics.contains(lastWord)){
//            words.remove(lastWord)
//        }
//        String simpleName = String.join(" ", words);
//        if (!isValidSimpleName(simpleName)){
//            return false;
//        }
//        return true;


    public String getFullName(Temperature temperature, Temperature defaultTemperature) {
        if (temperature == null || defaultTemperature == null) {
            throw new IllegalArgumentException("Temperature cannot be null");
        }

        boolean isHotter = ((temperature.getHotness() > defaultTemperature.getHotness()) || (temperature.getColdness() < defaultTemperature.getColdness()));
        boolean isCooler = ((temperature.getColdness() > defaultTemperature.getColdness()) || (temperature.getHotness() < defaultTemperature.getHotness()));

        if (isHotter) {
            return "Heated " + this.simpleName;
        }
        if (isCooler) {
            return "Cooled " + this.simpleName;
        }
        return this.simpleName;
    }

}

    /**
     * RESTART FINISH
     */


//
//public static final IngredientName WATER = new IngredientName(null, "Water");
//
//    private static List<String> getAllowedSymbols(){
//        List<String> allowedNameSymbols = Arrays.asList("'","(",")");
//        return allowedNameSymbols;
//    };
//
//    private static List<String> getIllegalWords(){
//        List<String> illegalWords = Arrays.asList("with","mixed");
//        return illegalWords;
//    };
//
//    /**********************************************************
//     * CONSTRUCTOR
//     **********************************************************/
//
//    /**
//     * A constructor for creating a new name with given special name and simple name parts.
//     *
//     */
//    @Raw
//    public Name(String... simpleNameParts) throws IllegalArgumentException, IllegalStateException {
//        if (!isValidSimpleNameParts(simpleNameParts)) {
//            throw new IllegalArgumentException("Invalid parts");
//        }
//
////     order the simple name parts and store them
//        Arrays.sort(simpleNameParts);
//        this.simpleNameParts = simpleNameParts;
//    }
//
//
//    /**********************************************************
//     * SIMPLE NAME
//     **********************************************************/
//
//    /**
//     * A variable referencing the simple name parts of the name.
//     */
//    private final String[] simpleNameParts;
//
//    /**
//     * A method for getting the simple name parts of this name.
//     */
//    @Basic @Immutable
//    public String[] getSimpleNameParts() {
//        return simpleNameParts;
//    }
//
//    /**
//     * A method that returns the simple name
//     */
//    @Immutable
//    public String getSimpleName() {
//        if (!isMix()) {
//            return simpleNameParts[0];
//        } else {
//            // first part
//            String toReturn = simpleNameParts[0] + " mixed with " + simpleNameParts[1];
//            // middle parts
//            for (int i = 2; i < simpleNameParts.length; i++) {
//                if (i != simpleNameParts.length - 1) {
//                    toReturn += ", ";
//                } else {
//                    // last part
//                    toReturn += " and ";
//                }
//                toReturn += simpleNameParts[i];
//            }
//            return toReturn;
//        }
//    }
//
//    /**
//     * A method for checking whether a simple name parts array is valid.
//     */
//    @Raw
//    public static boolean isValidSimpleNameParts(String[] simpleNameParts) {
//        if (simpleNameParts == null || simpleNameParts.length == 0) {
//            return false;
//        }
//        for (String simpleNamePart : simpleNameParts) {
//            if (!isValidName(simpleNamePart)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//
//    /**********************************************************
//     * SPECIAL NAME
//     **********************************************************/
//
//    /**
//     * A method for setting the special name of the name.
//     *
//     * @param specialName The special name to set.
//     * @return
//     */
//    @Raw @Model
//    protected String getSpecialName(String specialName){
//        if (!isMix() && specialName != null) {
//            throw new IllegalStateException("Can't have special name");
//        }
//        if (specialName != null && !isValidName(specialName)) {
//            //throw exceptie
//        }
//        return specialName;
//    }
//
//
//
//    /**********************************************************
//     * LOGIC
//     **********************************************************/
//
//    /**
//     * A method for checking whether the name is mixed
//     */
//    public boolean isMix() {
//        return getSimpleNameParts().length > 1;
//    }
//
//    /**
//     * A method for checking whether a given name is a valid name for an ingredient type.
//     *
//     * @param 	name The name to check.
//     *
//     * @return true if and only if the class invariant i
//     */
//    public static boolean isValidName(String name) {
//        if (name == null || name.isEmpty()) {
//            return false;
//        }
//        if (!name.matches("[A-Za-z'() ]+")) {
//            return false;
//        }
//        String[] words = name.split(" ");
//        if (words.length == 1) {
//            String lettersOnly = words[0].replaceAll("[^A-Za-z]", "");
//            if (lettersOnly.length() < 3) {
//                return false;
//            }
//        } else {
//            for (String word : words) {
//                String lettersOnly = word.replaceAll("[^A-Za-z]", "");
//                if (lettersOnly.length() < 2) {
//                    return false;
//                }
//            }
//        }
//        for (String word : words) {
//            if (word.isEmpty()) return false;
//
//            if (getIllegalWords().contains(word)) {
//                continue;
//            }
//            char firstChar = word.charAt(0);
//            if (!(Character.isUpperCase(firstChar) || firstChar == '\'' || firstChar == '(')) {
//                return false;
//            }
//        }
//        return true;
//    }
