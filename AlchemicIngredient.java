public class AlchemicIngredient {
    private String name;

    /**
     * hier worden de uitzonderingen geimplementeerd
     */
    private static final String[] ExeptionWords = {"mixed","with"};

    /**
     * kijkt of de naam voldoet aand e voorwaarden voor de eenvoudige naam
     * @param name
     * @return
     */
    public static boolean isValidSimpleName(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }
        String[] words = name.split(" ");
        String kleine ="abcdefghijklmnopqrstuvwxyz";
        String hoofdletters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String tekens = "(')";

        if ((words.length == 1) && (countLetters(name)<3)) {
            return false;
        }
        for (String word : words) {
            if (word.isEmpty() || word.length() == 0) {
                return false;
            }
            else if ((words.length != 1) && (countLetters(word)<2)) {
                return false;
            }
            else {
                boolean isException = false;
                for (String exceptionWord : ExeptionWords) {
                    if (exceptionWord.equals(word)) {
                        isException = true;
                        break;
                    }
                }

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
    public static boolean isValidIngredientName(String name) {

    }
}