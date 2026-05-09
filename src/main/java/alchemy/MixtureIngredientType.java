package alchemy

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * isMixture(): boolean {true}
 * getSpecialName(): String
 * setSpecialName(n: String) {delegerend naar MixtureIngredientName}
 * isMixture(): boolean {true — delegeert naar MixtureIngredientName}
 */
public class MixtureIngredientType{
    private MixtureIngredientName name:
    ///  temperature is weighted average of ingredients
    private long defaultTemperature;
    /// quantity is sum ( rounding loss with Stateconversion)
    private State defaultState;

    // helpmethod to calculate temperature and quantity (in MixtureAlchemicIngredient)
    public List<String> getIngredientsAsList() {
        simpleName = this.name.getSimpleName();      //// bv Garlic mixed with Imp Gas, Mercurial Acid and Water

        // De regex splitst op: " mixed with " OF ", " OF " and "
        String regex = " mixed with |, | and ";
        String[] splitArray = simpleName.split(regex);

        // We zetten de array hier om naar een List<String>
        List<String> ingredientenLijst = Arrays.asList(splitArray);

        return ingredientenLijst;
    }

    public long getTemperature() {
        ingredients = getIngredientAsList();
        size = ingredients.size();
        long temp = 0;
        for (String ingredient : ingredients) {
            temp += ingredient.getTemperature()
        }
        long temperature = temp/size;
        return temperature;
    }

    public
}