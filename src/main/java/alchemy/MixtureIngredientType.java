package alchemy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import be.kuleuven.cs.som.annotate.*;

/**
 * a class representing the type of a mixture alchemic ingredient
 * consisting of multiple combinded ingredient types with calculated
 * default temperature and state
 *
 * @invar the name of this mixture ingredient type can't be null
 *        | this.name != null
 * @invar the default temperature of a mixture ingredient can't be null
 *        | getDefaultTemperature() != null
 * @invar the default state of a mixture ingredient con't be null
 *        | getDefaultState != null
 */

public class MixtureIngredientType extends IngredientType{
    private final List<AlchemicIngredient> ingredients;

    // constructor
    public MixtureIngredientType(MixtureIngredientName name, List<AlchemicIngredient> ingredients) {
        super(name, calculateDefaultState(ingredients), calculateDefaultTemperature(ingredients));
        this.ingredients = ingredients;
    }

    public List<AlchemicIngredient> getIngredientsAsList() {
        return this.ingredients;
    }
    // methode voor hulp contructor
    private static Temperature calculateDefaultTemperature(List<AlchemicIngredient> ingredients) {
        if ((ingredients.isEmpty()) || (ingredients == null)) {
            return new Temperature(0,0)
        }
        long totalHotness = 0;
        long totalColdness = 0;
        for (AlchemicIngredient ingredient : ingredients){
            totalHotness += ingredient.getTemperature().getHotness();
            totalColdness += ingredient.getTemperature().getColdness();
        }
        Temperature newTemperature = new Temperature(totalColdness/ingredients.size(), totalHotness/ingredients.size());
        return newTemperature;
    }

    private static State calculateDefaultState(List<AlchemicIngredient> ingredients) {
        long minDistance = Long.MAX_VALUE;
        State result = State.LIQUID;
        for (AlchemicIngredient ingredient : ingredients) {
            long d = ingredient.getType().distanceToInterval();
            if ((d < minDistance) || ((d == minDistance) && (ingredient.getType().getDefaultState() == State.LIQUID))) {
                minDistance = d;
                result = ingredient.getType().getDefaultState();
            }
        }
        return result;
    }

    @Override
    public boolean isMixture(){
        return true
    }

    public String getSpecialName(){
        return ((MixtureIngredientName) getName()).getSpecialName();
    }

    public void setSpecialName(String specialName){
        ((MixtureIngredientName) getName()).setSpecialName(specialName);
    }
}