package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * A subclass of AlchemicIngredient representing mixed ingredients
 *
 * A mixture ingredient is backed by a MixtureIngredientType and can carry
 * a special name that is stored in that type.
 *
 * @invar   the type of this mixture ingredient is an instance of MixtureIngredientType
 *          | getType() instanceof MixtureIngredientType
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class MixtureAlchemicIngredient extends AlchemicIngredient {

    /**********************************************************
     *                      constructor
     **********************************************************/

    /**
     * Initialize a new mixture alchemic ingredient with the given mixture type,
     * quantity, state and temperature.
     *
     * @param   type
     *          the mixture ingredient type of this new mixture ingredient
     * @param   quantity
     *          the quantity (in spoons) of this new mixture ingredient
     * @param   currentState
     *          the state of this new mixture ingredient
     * @param   temperature
     *          the temperature of this new mixture ingredient
     *
     * @effect  initializes this mixture ingredient as an alchemic ingredient
     *          with the given type, quantity, state and temperature
     *          | super(type, quantity, currentState, temperature)
     */
    @Raw
    public MixtureAlchemicIngredient(MixtureIngredientType type, long quantity,
                                     STATE currentState, Temperature temperature) {
        super(type, quantity, currentState, temperature);
    }

    /**********************************************************
     *                         name
     **********************************************************/

    /**
     * Return the full name of this mixture ingredient based on its current
     * temperature relative to its default temperature.
     *
     * If a special name has been assigned, the full name is the special name
     * followed by the simple name (with any Heated/Cooled prefix) in parentheses.
     * Otherwise the full name is formed the same way as for non-mixed ingredients.
     *
     * @return  the full name of this mixture ingredient
     *          | result.equals(getMixtureType()
     *          |       .getFullName(getTemperature(), getType().getDefaultTemperature()))
     */
    @Override
    public String getFullName() {
        return getMixtureType().getFullName(getTemperature(), getType().getDefaultTemperature());
    }

    /**
     * Return the special name of this mixture ingredient, or null if none has been set.
     *
     * @return  the special name of the mixture ingredient type of this ingredient
     *          | result == getMixtureType().getSpecialName()
     */
    public String getSpecialName() {
        return getMixtureType().getSpecialName();
    }

    /**
     * Set the special name of this mixture ingredient to the given name.
     *
     * The special name must follow all conditions imposed on the name of a non-mixed ingredient.
     *
     * @param   specialName
     *          the new special name for this mixture ingredient, or null to clear it
     *
     * @post    the special name of this mixture ingredient is set to the given name
     *          | getMixtureType().getSpecialName().equals(specialName)
     *
     * @throws  IllegalArgumentException
     *          the given special name is effective but empty
     *          | specialName != null && specialName.isEmpty()
     */
    public void setSpecialName(String specialName) {
        getMixtureType().setSpecialName(specialName);
    }

    /**********************************************************
     *                        type
     **********************************************************/

    /**
     * Return the mixture ingredient type of this mixture ingredient.
     *
     * @return  the type of this ingredient cast to MixtureIngredientType
     *          | result == (MixtureIngredientType) getType()
     */
    @Basic @Raw
    public MixtureIngredientType getMixtureType() {
        return (MixtureIngredientType) getType();
    }
}