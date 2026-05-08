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
public class Name {


    public static final Name WATER = new Name(null, "Water");

    private static List<String> getAllowedSymbols(){
        List<String> allowedNameSymbols = Arrays.asList("'","(",")");
        return allowedNameSymbols;
    };

    private static List<String> getIllegalWords(){
        List<String> illegalWords = Arrays.asList("with","mixed");
        return illegalWords;
    };

    /**********************************************************
     * CONSTRUCTOR
     **********************************************************/

    /**
     * A constructor for creating a new name with given special name and simple name parts.
     *
     */
    @Raw
    public Name(String... simpleNameParts) throws IllegalArgumentException, IllegalStateException {
        if (!isValidSimpleNameParts(simpleNameParts)) {
            throw new IllegalArgumentException("Invalid parts");
        }

//     order the simple name parts and store them
        Arrays.sort(simpleNameParts);
        this.simpleNameParts = simpleNameParts;
    }


    /**********************************************************
     * SIMPLE NAME
     **********************************************************/

    /**
     * A variable referencing the simple name parts of the name.
     */
    private final String[] simpleNameParts;

    /**
     * A method for getting the simple name parts of this name.
     */
    @Basic @Immutable
    public String[] getSimpleNameParts() {
        return simpleNameParts;
    }

    /**
     * A method that returns the simple name
     */
    @Immutable
    public String getSimpleName() {
        if (!isMix()) {
            return simpleNameParts[0];
        } else {
            // first part
            String toReturn = simpleNameParts[0] + " mixed with " + simpleNameParts[1];
            // middle parts
            for (int i = 2; i < simpleNameParts.length; i++) {
                if (i != simpleNameParts.length - 1) {
                    toReturn += ", ";
                } else {
                    // last part
                    toReturn += " and ";
                }
                toReturn += simpleNameParts[i];
            }
            return toReturn;
        }
    }

    /**
     * A method for checking whether a simple name parts array is valid.
     */
    @Raw
    public static boolean isValidSimpleNameParts(String[] simpleNameParts) {
        if (simpleNameParts == null || simpleNameParts.length == 0) {
            return false;
        }
        for (String simpleNamePart : simpleNameParts) {
            if (!isValidName(simpleNamePart)) {
                return false;
            }
        }
        return true;
    }


    /**********************************************************
     * SPECIAL NAME
     **********************************************************/

    /**
     * A method for setting the special name of the name.
     *
     * @param specialName The special name to set.
     * @return
     */
    @Raw @Model
    protected String getSpecialName(String specialName){
        if (!isMix() && specialName != null) {
            throw new IllegalStateException("Can't have special name");
        }
        if (specialName != null && !isValidName(specialName)) {
            //throw exceptie
        }
        return specialName;
    }



    /**********************************************************
     * LOGIC
     **********************************************************/

    /**
     * A method for checking whether the name is mixed
     */
    public boolean isMix() {
        return getSimpleNameParts().length > 1;
    }

    /**
     * A method for checking whether a given name is a valid name for an ingredient type.
     *
     * @param 	name The name to check.
     *
     * @return true if and only if the class invariant i
     */
    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (!name.matches("[A-Za-z'() ]+")) {
            return false;
        }
        String[] words = name.split(" ");
        if (words.length == 1) {
            String lettersOnly = words[0].replaceAll("[^A-Za-z]", "");
            if (lettersOnly.length() < 3) {
                return false;
            }
        } else {
            for (String word : words) {
                String lettersOnly = word.replaceAll("[^A-Za-z]", "");
                if (lettersOnly.length() < 2) {
                    return false;
                }
            }
        }
        for (String word : words) {
            if (word.isEmpty()) return false;

            if (getIllegalWords().contains(word)) {
                continue;
            }
            char firstChar = word.charAt(0);
            if (!(Character.isUpperCase(firstChar) || firstChar == '\'' || firstChar == '(')) {
                return false;
            }
        }
        return true;
    }
