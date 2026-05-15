package Alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * a class representing a mixture of alchemic ingredients, extending AlchemicIngredient
 * a mixture can optionally have a special name assigned to it
 *
 * @invar the mixture type of this mixture ingredient cannot be null
 *        | getMixtureType() != null
 *
 * @author Joran Naessens
 * @author Maxime Samyn
 */
public class MixtureAlchemicIngredient extends AlchemicIngredient{

    /**********************************************************
     * constructor
     *********************************************************/

    /**
     * initialize a new mixture alchemic ingredient with a given mixture type,
     * quantity, state and temperature
     *
     * @param type
     *        the mixture ingredient type of this new mixture ingredient
     * @param quantity
     *        the quantity (in spoons) of this new mixture ingredient
     * @param currentState
     *        the state of this new mixture ingredient
     * @param temperature
     *        the temperature of this new mixture ingredient
     *
     * @effect initializes this mixture ingredient as an alchemic ingredient
     *         with the given type, state, temperature and quantity
     *         | super(type, currentState, temperature, quantity)
     */
    @Raw
    @Raw
    public MixtureAlchemicIngredient(MixtureIngredientType type, long quantity, State currentState, Temperature temperature) {
        super(type, quantity, currentState, temperature);
    }

    /**
     * return the full name of this mixture ingredient based on its current
     * temperature relative to its default temperature
     *
     * @return the full name of this mixture ingredient
     *         | result.equals(getMixtureType().getFullName(getTemperature(), getType().getDefaultTemperature()))
     */
    @Override
    public String getFullName(){
        return getMixturetype().getFullName(getTemperature(), getType().getDefaultTemperature());
    }

    /**
     * return the special name of this mixture ingredient, if one has been set
     *
     * @return the special name of this mixture ingredient
     *         | result.equals(getMixtureType().getFullName(getTemperature(), getType().getDefaultTemperature()))
     */
    public String getSpecialName(){
        return getMixturetype().getFullName(getTemperature(), getType().getDefaultTemperature());
    }

    /**
     * set the special name of this mixture ingredient to the given name
     *
     * @param specialName
     *        the new special name for this mixture ingredient
     *
     * @post the special name of this mixture ingredient is set to the given name
     *       | getMixtureType().getSpecialName().equals(specialName)
     *
     * @throws IllegalArgumentException
     *         the given special name is null
     *         | specialName == null
     * @throws IllegalArgumentException
     *         the given special name is empty
     *         | specialName.isEmpty()
     */
    public void setSpecialName(String specialName){
        if (specialName == null)
            throw new IllegalArgumentException("specialName is can't be null.");
        if (specialName.isEmpty())
            throw new IllegalArgumentException("specialName is can't be empty.");
        getMixtureType().setSpecialName(specialName);
    }


    /**
     * return the mixture ingredient type of this mixture ingredient
     *
     * @return the mixture ingredient type of this ingredient
     *         | result == (MixtureIngredientType) getType()
     */
    @Basic @Raw
    public MixtureIngredientType getMixtureType(){
        return (MixtureIngredientType) getType();
    }
    
}