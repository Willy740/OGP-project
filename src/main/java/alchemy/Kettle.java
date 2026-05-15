package alchemy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.round;

import be.kuleuven.cs.som.annotate.*;

/**
 * @author  Joran Naessens
 * @author  Maxime Samyn
 *
 * A class that implements a kettle device allowing the mix between multiple AlchemicIngredients
 */
public class Kettle extends Device{

    private List<AlchemicIngredient> ingredients = new List<AlchemicIngredient>();

    /**
     * initialize a new kettle with no limit on the amount of ingredients it can hold
     *
     * @effect initializes this kettle as a device with no ingredient limit
     *         | super()
     * @post the maximum ingredient amount is set to the maximum integer value
     *       | new.getMaxIngredientAmount() == Integer.MAX_VALUE
     */
    public Kettle() {
    }

    public int getMaxIngredientAmount(){return Integer.MAX_VALUE;}

    /**
     * Generates a new String name based on ingredients currently in ingredientList.
     * The format is "Alpha mixed with Beta,Delta,...,Zeta"
     * The order will be the same as the order of ingredientList
     * @pre All simple names must be valid
     *      | for ingr in IngredientList:
     *      | AlchemicIngredient.canHaveAsName(ingr.getType.getSimpleName()) == True
     * @return The newly generated name
     *        |result += IngredientList.get(0).getSimpleName() + " mixed with"
     *        |for ingr in IngredientList:
     *        |    result +=  ingr.getSimpleName() +", "
     *        |result = result[:-1]
     */

    private String mergeNames(){
        StringBuilder stringBuilder = new StringBuilder();
        AlchemicIngredient firstIngredient = ingredients.getFirst();
        stringBuilder.append(firstIngredient.getType().getSimpleName());
        stringBuilder.append(" mixed with ");

        for (int i = 1; i < ingredients.size(); i++){
            if (i> 1){stringBuilder.append(", ");}
            AlchemicIngredient ingredient = ingredients.get(i);
            stringBuilder.append(ingredient.getType().getSimpleName());


        }
        return stringBuilder.toString();
    }

    /**
     * Chooses a new State based on all current ingredients in IngredientList. The one closest to 20 Hotness will specify the state, if multiple are closest, Liquid will prevail
     * @pre all ingredients in IngredientList must have a valid temperature and State
     *      | for ingr in ingredientList:
     *      |   ingr.getIngredientState != none
     *      |   ingr.getHotness != none
     *      |   ingr.getColdness != none
     * @return State, the right State of matter based on the temperature of the ingredients.
     *      | for ingr in ingredientList:
     *      |   if abs(ingr.getTemperature() - 20) < smallestDif || (abs(ingr.getTemperature() - 20) == smallestDif && ingr.getIngredientState == State.LIQUID:
     *      |       closestIngr = ingr.get(0)
     *      |       smallestDif = abs(ingr.get(0).getTemperature() - 20)
     *      | result =  closestIngr.getState()
     *
     */
    private STATE getNewState() throws IsDestroyedException{
//        AlchemicIngredient closestIngredient = EERSTE INGREDIENT

        long smallestDifference = calculateTempDifference(closestIngredient,20);
        for (int i = 1; i < ingredients.size(); i++){
            long difference = calculateTempDifference(ingredients.get(i),20);
            if (difference < smallestDifference) {
                smallestDifference = difference;
                ingredients = Collections.singletonList(ingredients.get(i));
            }
            if ((difference == smallestDifference)&& (ingredients.get(i).getIngredientState() == STATE.LIQUID)){
                closestIngredient = ingredients.get(i);
            }
        }
        return closestIngredient.getType().getState();
    }

    /**
     * Calculates the unrounded total of all the ingredient amounts.
     * @pre all ingredients must have a valid amount.
     *      | for ingr in ingredientList:
     *      |   AlchemicIngredient.isValidSpoonsAmount(ingr.getSpoons(),ingr.getIngredientState()) == True
     * @return the totol amount.
     *      | result = 0
     *      | for ingr in ingredientList:
     *      |   result += ingr.getSpoons()
     * @post the amount of spoons must be a valid amount.
     *      | result >= 0
     */
    private float getTotalAmount() throws IsDestroyedException{
        float total = 0;
        for (int i = 0; i < ingredients.size(); i++){
            AlchemicIngredient ingredient = ingredients.get(i);

            total = total + ingredient.getQuantity();

        }

        return total;
    }

    /**
     * Calculates the rounded total of all the ingredient amounts.
     * @pre all ingredients must have a valid amount.
     *      | for ingr in ingredientList:
     *      |   AlchemicIngredient.isValidSpoonsAmount(ingr.getSpoons(),ingr.getIngredientState()) == True
     * @param newState must be a valid Powder or Liquid State
     *      | newState == State.LIQUID || newState == State.POWDER
     * @return the totol amount.
     *      | result = 0
     *      | for ingr in ingredientList:
     *      |   result += ingr.getSpoons()
     *      | result = AmountConversion.StateAmountConversion(result, newState)
     * @post the amount of spoons must be a valid amount.
     *      | AlchemicIngredient.isValidSpoonsAmount(result, newState)
     */
    private float getNewRoundedAmount(STATE newState) throws IsDestroyedException{

        float oldTotal = getTotalAmount();

        float roundedTotal = //CONVERTIE
        return roundedTotal;
    }

    /**
     * Calculate the new weighted total that is equal to the sum off all temperatures times amount and then divided by the total amount of all ingredients.
     * @pre all ingredients must have a valid amount.
     *      | for ingr in ingredientList:
     *      |   AlchemicIngredient.isValidSpoonsAmount(ingr.getSpoons(),ingr.getIngredientState()) == True
     *
     * @return double containing the newly calculated weighted temperature.
     *      | result = 0
     *      | for ingr in ingredientList:
     *      |     result += ingr.getSpoons() * ingr.getTemperature()
     */
    private long getNewTemp() throws IsDestroyedException{
        long weightedTotal = 0;
        for (AlchemicIngredient ingredient : ingredients) {
            weightedTotal += (long) (ingredient.getQuantity() * (ingredient.getTemperature().getHotness() - ingredient.getTemperature().getColdness()));
        }
        return (long) (weightedTotal/ getTotalAmount());
    }

    /**
     * Calculates the diffrence between an ingredient and a given temperature
     * @param ingredient Ingredient must have a valid temperature
     *      |   ingredient.getHotness != none
     *      |   ingredient.getColdness != none
     * @param temperature temperature must be a valid integer
     *      |   temperature != none
     * @return
     */ // ZOU EIG IN TEMPERATURE MOETN ZIJN
    private long calculateTempDifference(AlchemicIngredient ingredient , int temperature) throws IsDestroyedException{
        return abs(temperature - (ingredient.getTemperature().getHotness() - ingredient.getTemperature().getColdness()));
    }

    /**
     *Calculates the new standard temperatures based on the ingredient closest to hotness 20, the hottest ingredient will be chosen if 2 have the same difference.
     * @return ArrayList containing Negative and Positive standard temperatures
     */

    /**
     * Calculates the new standard temperaturs based on all current ingredients in IngredientList. The one closest to 20 Hotness will specify the standard temperature, the hottest ingredient will be chosen if 2 have the same difference.
     * @pre all ingredients in IngredientList must have a valid temperature and Standard temperature.
     *      | for ingr in ingredientList:
     *      |   ingr.getType().getStandardCoolness != none
     *      |   ingr.getType().getStandardHotness  != none
     *      |   ingr.getHotness != none
     *      |   ingr.getColdness != none
     * @return ArrayList containing Negative and Positive standard temperatures in that order
     *      | for ingr in ingredientList:
     *      |   if abs(ingr.getTemperature() - 20) < smallestDif || (abs(ingr.getTemperature() - 20) == smallestDif && ingr.getHotness > 0)
     *      |       closestIngr = ingr.get(0)
     *      |       smallestDif = abs(ingr.get(0).getTemperature() - 20)
     *      | result = closestIngr.getType().getStandardCoolness(), closestIngr.getType().getStandardHotness()
     *
     */
    private ArrayList getNewStdTemp() throws IsDestroyedException{
        AlchemicIngredient closestIngredient = ingredients.get(0);

        long smallestDifference = calculateTempDifference(closestIngredient,20);

        for (int i = 1; i < ingredients.size(); i++){
            long difference = calculateTempDifference(ingredients.get(i),20);

            if (difference < smallestDifference) {
                smallestDifference = difference;
                closestIngredient = ingredients.get(i);
            }
            if ((difference == smallestDifference)&& (ingredients.get(i).getTemperature().getHotness() > 0)){

                smallestDifference = difference;
                closestIngredient = ingredients.get(i);
            }
        }
        ArrayList<Long> longs = new ArrayList<>(Arrays.asList(closestIngredient.getType().getStdCoolness(), closestIngredient.getType().getStdHotness()));
        return longs;
    }

    /**
     * Merges the ingredients in kettle to one ingredient
     *
     * @post the new name wil be merged by mergeNames()
     *       | container.getIngredient().getType().getSimpleName() == mergeNames()
     * @post the new state will be chosen by getNewState()
     *       | container.getIngredient().getType().getState() == getNewState()
     * @post the new standard temperatures will be calculated by getNewStdTemp()
     *       | container.getIngredient().getType().getStandardCoolness == getNewStdTemp()[0]
     *       | container.getIngredient().getType().getStandardHotness== getNewStdTemp()[1]
     * @post the new Amount will be calculated by getNewRoundedAmount()
     *       | container.getIngredient().getSpoons() == getNewRoundedAmount()
     * @post ingredientList will contain the 1 newly created valid AlchemicIngredient
     *       | getIngredientList().size() == 1
     *
     * @throws  IsDestroyedException
     *          The device has already been destroyed
     *          | this.isDestroyed() == true
     * @throws  DeviceNotInLaboratoryException
     *          The device isn't part of a laboratory
     *          | this.getLaboratory() == null
     * @throws  NotEnoughIngredientLeftException
     *          There are less than two different ingredients in this device
     *          | getIngredientList().size() < 2
     */
    public void executeOperation() throws IsDestroyedException,DeviceNotInLaboratoryException,NotEnoughIngredientLeftException {
        if (isDestroyed()) {
            throw new IsDestroyedException("Device is already destroyed");
        }
        if (getLaboratory() == null){
            throw new DeviceNotInLaboratoryException("Kettle must be in a laboratory to be used");
        }
        if (ingredients.size() < 2){
            throw new NotEnoughIngredientLeftException("Kettle must contain at least 2 ingredients!");
        }
        String newName = mergeNames();
        STATE newState = getNewState();
        ArrayList<Long> newStdTemp = getNewStdTemp();
        long newTemp = getNewTemp();
        float newAmount = getNewRoundedAmount(newState);

        IngredientType newType = new IngredientType(newName,newState,newStdTemp);
        AlchemicIngredient newIngredient = new AlchemicIngredient(newType, newAmount);
        if (newTemp > 0){
            newIngredient.heat(newTemp);
        }
        else{
            newIngredient.cool(newTemp);
        }

        for (AlchemicIngredient ingredient : ingredients) {
            ingredient.destroy();
        }

        ingredients = new ArrayList<>(Arrays.asList(newIngredient));
    }

    public List<AlchemicIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<AlchemicIngredient> ingredients) {
        this.ingredients = ingredients;
    }
}
