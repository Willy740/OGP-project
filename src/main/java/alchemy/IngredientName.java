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
 * function returning the list of lowercase letters used in name validation
 */
    private static List<String> getLowercase(){
        return List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
    }

    /**
     * function returning the list of uppercase letters used in name validation
     */
    private static List<String> getUppercase() {
        return List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
    }

    /**
     * function returning the list of symbols used in name validation
     */
    private static List<String> getSymbols() {
        return List.of("'", "(", ")");
    }

    /**
     * function returning the words that may not appear in a simple name
     */
    private static List<String> getDisallowedWords() {
        return List.of("with", "mixed");
    }

    /**
     * function returning the temperature-related prefixes that can appear
     * in a full name
     */
    private static List<String> getCharacteristics() {
        return List.of("Heated", "Cooled");
    }


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
        if (simpleName == null || simpleName.isEmpty() || !isValidSimpleName(simpleName)) {
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
     * - aach word must start with an uppercase letter or accepted symbol or accepted symbol
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
     *          or contains characters after the first that are neither lowercase letters
     *          nor accepted symbols
     *          | if (simpleName.split(" ").length >= 2)
     *          | then for each word in simpleName.split(" "):
     *          |   if (word.length() < 2
     *          |        || disallowedWords.contains(word)
     *          |        || (!uppercase.contains(String.valueOf(word.charAt(0)))
     *                          && !symbols.contains(String.valueOf(word.charAt(0))))
     *          |        || (exists i in 1..word.length()-1 :
     *          |              !lowercase.contains(String.valueOf(word.charAt(i)))
     *          |              && !symbols.contains(String.valueOf(word.charAt(i)))))
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
                if (getDisallowedWords().contains(word)) {
                    return false;
                }
                for (int i = 0; i < word.length(); i++) {
                    String letter = String.valueOf(word.charAt(i));
                    if (i == 0) {
                        if (!getUppercase().contains(letter)&& !getSymbols().contains(letter)) {
                            return false;
                        }
                    }
                    if (i != 0) {
                        if (!getLowercase().contains(letter) && !getSymbols().contains(letter)) {
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
