package alchemy;

import java.util.List;
import be.kuleuven.cs.som.annotate.*;

/**
 * a class representing a mixture alchemic ingredient
 * a mixture ingredient is an alchemic ingredient whose type is a mixture,
 * composed of multiple component alchemic ingredients
 *
 * @invar   the type of this mixture alchemic ingredient must be a mixture ingredient type
 *          | getType() instanceof MixtureIngredientType
 *
 * @note    all other invariants are inherited from AlchemicIngredient
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class MixtureAlchemicIngredient extends AlchemicIngredient{
    /**********************************************************
     * constructor
     **********************************************************/

    /**
     * initialize a new mixture alchemic ingredient with the given mixture type,
     * temperature, quantity and current state
     *
     * @param   type
     *          the mixture ingredient type of this new mixture alchemic ingredient
     * @param   temperature
     *          the temperature of this new mixture alchemic ingredient
     * @param   quantity
     *          the quantity of this new mixture alchemic ingredient, expressed in spoons
     * @param   currentState
     *          the current state of this new mixture alchemic ingredient
     *
     * @effect  the new mixture alchemic ingredient is initialized as an alchemic ingredient
     *          with the given type, current state, temperature and quantity
     *          | super(type, currentState, temperature, quantity)
     */
    @Raw
    public MixtureAlchemicIngredient(MixtureIngredientType type, Temperature temperature, long quantity, State currentState) {
        super(type, currentState, temperature, quantity);

    /**
     * return the full name of this mixture alchemic ingredient
     *
     * if the mixture type has a special name, it is prepended to the full name
     * of the superclass (which may contain a "Heated" or "Cooled" prefix)
     * enclosed in parentheses
     * if no special name is set, the full name of the superclass is returned directly
     *
     * @return  if the mixture type has a special name:
     *          the special name followed by the superclass full name in parentheses
     *          | if (((MixtureIngredientType) getType()).getSpecialName() != null)
     *          | then result.equals(((MixtureIngredientType) getType()).getSpecialName()
     *          |          + " (" + super.getFullName() + ")")
     * @return  otherwise, the full name of the superclass is returned
     *          | else result.equals(super.getFullName())
     */
    @Override
    public String getFullName(){
        MixtureIngredientType mixtureType = (MixtureIngredientType) getType();
        if (mixtureType.getSpecialName == null){
            return super.getFullName
        }
        return mixtureType.getSpecialName() + "(" + super.getFullName() + ")";
    }

    /**
     * return the special name of this mixture alchemic ingredient, or null if none is set
     *
     * @return  the special name as provided by the mixture ingredient type
     *          | result == ((MixtureIngredientType) getType()).getSpecialName()
     */
    public String getSpecialName(){
        return ((MixtureIngredientType) getType()).getSpecialName();
    }

    /**
     * set the special name of this mixture alchemic ingredient to the given name
     *
     * @param   specialName
     *          the new special name, or null to clear it
     *
     * @effect  the special name of the mixture ingredient type is set
     *          | ((MixtureIngredientType) getType()).setSpecialName(specialName)
     *
     * @throws  IllegalArgumentException
     *          the given special name is effective but empty
     *          | specialName != null && specialName.isEmpty()
     */
    public void setSpecialName(String specialName){
        ((MixtureIngredientType) getType()).setSpecialName(specialName);
    }

    /**
     * return the total quantity of this mixture alchemic ingredient, expressed in spoons
     *
     * the total quantity is the sum of the quantities of all component ingredients
     *
     * @return  the sum of the quantities of all component ingredients
     *          | result == (sum of ingredient.getQuantity()
     *          |             for each ingredient in
     *          |             ((MixtureIngredientType) getType()).getIngredientsAsList())
     *
     * @note    due to rounding that may occur while converting it is not sure that the quantity will be exact
     */
    @Override
    public long getQuantity() {
        List<AlchemicIngredient> ingredients = ((MixtureIngredientType) getType()).getIngredientsAsList();
        long quantity = 0;
        for (AlchemicIngredient ingredient : ingredients){
            quantity += ingredient.getQuantity();
        }
        return quantity;
    }

}