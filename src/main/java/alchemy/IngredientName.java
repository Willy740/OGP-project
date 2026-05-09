package alchemy;

import java.util.Arrays;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing the name of an ingredient type.
 *
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class IngredientName {
    /**
     * RESTART START
     * <p>
     * <p>
     * getSimpleName(): String                                                          done
     * getFullName(temp: Temperature, defTemp: Temperature): String {«abstract»}
     * {prefix Heated/Cooled; suffix bijvoegsels (bv. Heated Water)}
     * isValidName(n: String): boolean {«static»}                                       done
     *
     *
     */

    private String simpleName;

    private List<String> lowercase = List.of(
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
    private List<String> uppercase = List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
    private List<String> symbols = List.of("'", "(", ")");

    private List<String> disallowedWords = List.of("with", "mixed");
    private List<String> characteristics = List.of("Heated", "Cooled")
    // constructor

    public IngredientName(String simpleName) {
        if (simpleName == null || simpleName.length() == 0 || !isValidSimpleName(simpleName)) {
            throw new IllegalArgumentException("Invalid simple name: " + simpleName);
        }
        this.simpleName = simpleName;
    }


    // checks if simpleName is valid
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
                    char letter = word.charAt(i);
                    if (i == 0) {
                        if (!uppercase.contains(letter)) {
                            return false;
                        }
                    }
                    if (i != 0) {
                        if (!allowed.contains(letter)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

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

        boolean isHotter = temperature.getHotness() > defaultTemperature.getHotness()
                || temperature.getColdness() < defaultTemperature.getColdness();
        boolean isCooler = temperature.getColdness() > defaultTemperature.getColdness()
                || temperature.getHotness() < defaultTemperature.getHotness();

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
}

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
