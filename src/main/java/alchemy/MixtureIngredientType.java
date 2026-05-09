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

    // constructor


    public MixtureIngredientType(MixtureIngredientName name, long defaultTemperature, State defaultState) {
        this.name = name;
        this.defaultTemperature = getDefaultTemperature();
        this.defaultState = getDefaultState();
    }

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
    // methode voor hulp contructor
    public long getDefaultTemperature() {
        ingredients = getIngredientAsList();
        size = ingredients.size();
        long temp = 0;
        for (String ingredient : ingredients) {
            temp += ingredient.getTemperature()
        }
        long temperature = temp/size;
        return this.defaultTemperature = temperature
    }

    // methode voor hulp contructor
    public State getDefaultState() {
        ingredientType type;                          // same problem as in MixtureAlchemicIngredient (getQuantity), de ingredient is een String = PROBLEEM
        ingredients = getIngredientAsList();
        long distance = getTemperature();
        for (String ingredient : ingredients){
            distance2 = ingredient.getType().DistanceToInterval();
            if (distanct2 == distance){
                if (ingredient.get.type().getDefaultState() == LIQUID){
                    distance = distance2;
                    type = ingredient.getType()
                }
            }
            else if (distance2 < distance){
                distance = distance2
                type = ingredient.getType()
            }
        }
        return this.defaultState = type.getDefaultState();
    }

    public boolean isMixture(){
        return True
    }

    public String getSpecialname(){
        return this.name.getSpecialName();
    }

    public void setSpecialname(String specialname){
        this.name.setSpecialName(specialname);
    }
}