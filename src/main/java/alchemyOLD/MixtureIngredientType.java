package alchemy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import be.kuleuven.cs.som.annotate.*;

/**
 * a class representing the type of a mixture alchemic ingredient
 * a mixture ingredient type is composed of multiple alchemic ingredients,
 * whose default temperature and default state are calculated from the components
 *
 * @invar   the name of this mixture ingredient type must be effective
 *          | getName() != null
 * @invar   the default temperature of this mixture ingredient type must be effective
 *          | getDefaultTemperature() != null
 * @invar   the default state of this mixture ingredient type must be effective
 *          | getDefaultState() != null
 * @invar   the list of ingredients of this mixture ingredient type must be effective
 *          | getIngredientsAsList() != null
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class MixtureIngredientType extends IngredientType{
    /**
     * variable referencing the list of alchemic ingredients that make up this mixture type, final
     */
    private final List<AlchemicIngredient> ingredients;

    /**********************************************************
     * constructor
     **********************************************************/

    /**
     * initialize a new mixture ingredient type with the given name and
     * list of component alchemic ingredients
     *
     * the default temperature and default state are calculated from the
     * component ingredients
     *
     * @param   name
     *          the name of this new mixture ingredient type
     * @param   ingredients
     *          the list of alchemic ingredients that make up this mixture
     *
     * @effect  the new mixture ingredient type is initialized as an ingredient type
     *          with the given name, a calculated default state and a calculated
     *          default temperature
     *          | super(name, calculateDefaultState(ingredients),
     *          |            calculateDefaultTemperature(ingredients))
     * @post    the list of ingredients of this mixture ingredient type is set to
     *          the given list
     *          | new.getIngredientsAsList() == ingredients
     */
    public MixtureIngredientType(MixtureIngredientName name, List<AlchemicIngredient> ingredients) {
        super(name, calculateDefaultState(ingredients), calculateDefaultTemperature(ingredients));
        this.ingredients = ingredients;
    }

    /**
     * return the list of alchemic ingredients that make up this mixture type
     */
    @Basic @Raw @Immutable
    public List<AlchemicIngredient> getIngredientsAsList() {
        return this.ingredients;
    }

    /**
     * calculate the default temperature for a mixture given its component ingredients
     *
     * the default temperature is the arithmetic average of the temperatures of all
     * component ingredients, computed separately for coldness and hotness
     *
     * @param   ingredients
     *          the list of component alchemic ingredients
     *
     * @return  a temperature with coldness 0 and hotness 0 if the list is null or empty
     *          | if (ingredients == null || ingredients.isEmpty())
     *          | then result.getColdness() == 0 && result.getHotness() == 0
     * @return  otherwise, a temperature whose coldness equals the average coldness
     *          and whose hotness equals the average hotness over all components
     *          | else result.getColdness() ==
     *          |       (sum of ingredient.getTemperature().getColdness()) / ingredients.size()
     *          |   && result.getHotness() ==
     *          |       (sum of ingredient.getTemperature().getHotness())  / ingredients.size()
     *
     * @note    this method is only accesible in this class
     */
    private static Temperature calculateDefaultTemperature(List<AlchemicIngredient> ingredients) {
        if ((ingredients.isEmpty()) || (ingredients == null)) {
            return new Temperature(0,0);
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

    /**
     * calculate the default state for a mixture given its component ingredients
     *
     * the default state is taken from the ingredient whose type lies closest to the
     * neutral temperature interval [0, 20], in case of a tie, the liquid state is preferred
     *
     * @param   ingredients
     *          the list of component alchemic ingredients
     *
     * @return  State.LIQUID if the list is null or empty
     *          | if (ingredients == null || ingredients.isEmpty())
     *          | then result == State.LIQUID
     * @return  otherwise, the default state of the ingredient type with the smallest
     *          distance to the neutral temperature interval; in case of a tie,
     *          the liquid state takes priority.
     *          | else result == (the defaultState of the ingredient whose type has
     *          |   the minimum distanceToInterval(), with State.LIQUID preferred on ties)
     *
     * @note    this method is only accesible in this class
     */
    private static STATE calculateDefaultState(List<AlchemicIngredient> ingredients) {
        if ((ingredients.isEmpty()) || (ingredients == null)) {
            return STATE.LIQUID;
        }
        long minDistance = Long.MAX_VALUE;
        STATE result = STATE.LIQUID;
        for (AlchemicIngredient ingredient : ingredients) {
            long d = ingredient.getType().distanceToInterval();
            if ((d < minDistance) || ((d == minDistance) && (ingredient.getType().getDefaultState() == STATE.LIQUID))) {
                minDistance = d;
                result = ingredient.getType().getDefaultState();
            }
        }
        return result;
    }

    /**
     * check whether this ingredient type is a mixture
     *
     * @return  true, since this class always represents a mixture ingredient type
     *          | result == true
     */
    @Override
    public boolean isMixture(){
        return true;
    }

    /**
     * return the special name of this mixture ingredient type, or null if none is set
     *
     * @return  the special name as provided by the mixture ingredient name object
     *          | result == ((MixtureIngredientName) getName()).getSpecialName()
     */
    public String getSpecialName(){
        return ((MixtureIngredientName) getName()).getSpecialName();
    }

    /**
     * set the special name of this mixture ingredient type to the given name
     *
     * @param   specialName
     *          the new special name, or null to clear it
     *
     * @effect  the special name of the mixture ingredient name object is set
     *          | ((MixtureIngredientName) getName()).setSpecialName(specialName)
     *
     * @throws  IllegalArgumentException
     *          the given special name is effective but empty
     *          | specialName != null && specialName.isEmpty()
     */
    public void setSpecialName(String specialName){
        ((MixtureIngredientName) getName()).setSpecialName(specialName);
    }
}