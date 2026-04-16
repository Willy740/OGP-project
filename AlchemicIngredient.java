/**
 * @author Joran Naessens
 * @author Maxim Samyn
 * @author Lars Debrabander
 * @param simpleName
 * @return
 */
public class AlchemicIngredient {
    private static final String[] ExeptionWords = {"mixed", "with"};
    private final String simpleName;
    private final String IngredientName;
    private List<String> prefixes = new ArrayList<>();
    private List<String> suffixes = new ArrayList<>();
    private String mix;
    private String mixName;

    /**
     * getters en setters
     * @return
     */


    public String getSimpleName() {
        return simpleName;
    }

    public String getIngredientName() {
        return IngredientName;
    }

    public List<String> getPrefixes() {
        return prefixes;
    }

    public List<String> getSuffixes() {
        return suffixes;
    }

    // eenvoudige naam mengsels
    public String getMix() {
        return mix;
    }

    public void setMix(String mix) {
        this.mix = mix;
    }

    // speciale naam mengsels
    public String getMixName() {
        return mixName;
    }

    public void setMixName(String mixName) {
        this.mixName = mixName;
    }

    // volledige naam mengsels
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPrefixes(List<String> prefixes) {
        this.prefixes = prefixes;
    }

    public void setSuffixes(List<String> suffixes) {
        this.suffixes = suffixes;
    }



    /**
     * constructor
     *
     * @param simpleName
     * @param IngredientName
     */
    public AlchemicIngredient(String simpleName, String IngredientName,String mix, String mixName) {
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
            if (!sValidIngredientName(word)){
                return false;
            }
        }
        return true;
    }

    /**
     * kijkt voor een mix van bepaaltde ingredienten of de mixName een valid naam is
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
     * maakt volledige naam van een mengsel
     * @param mixName
     * @param mix
     * @return
     */
    public String FullMixName(String mixName,String mix) {
        //if (mixName == null || mixName.length() == 0){
            // GEVAL DAT ER GEEN SPECIALE NAAM IS
            // WAT MOET ER DAN GEBEUREN?
            // NU ZAL ER EEN EXCEPTION GEGOOID WORDEN ALS ER GEEN SPECIALE NAAM IS
        }
        if  (!isValidMixName(mixName,mix)) {
            throw new IllegalArgumentException("ongeldige mixnaam: " + mixName + "of ingredienten in de mix: " + mix);
        }
        String fullName= mixName + "(" + mix + ")";
        return fullName
    }





}