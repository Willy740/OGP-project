package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * a class representing an alchemic ingredient with a type, state, temperature and quantity
 *
 * @invar the type of this ingredient can't be null
 *        | getType() != null
 * @invar the current state of this ingredient can't be null
 *        | getCurrentState() != null
 * @invar the temperature of this ingredient can't be null
 *        | getTemperature() != null
 * @invar the quantity of this ingredient can't be negatif
 *        | getQuantity() >= 0
 *
 * @author Joran Naessens
 * @author Maxime Samyn
 *
 */
public class AlchemicIngredient{
    /**
     * variable referencing the type of an ingredient, final
     */
    private final IngredientType type;

    /**
     * a variabale referencing the state of an ingredient
     */
    private STATE currentState;

    /**
     * a variable referencing the temperature of an ingredient
     */
    private Temperature temperature;

    /**
     * a variabel referencing the quantity (spoons) of an ingredient, final
     */
    private final long quantity;

    /**********************************************************
     * constructor
     *********************************************************/

    /**
     * initialize a new alchemic ingredient with given type, state, temperature and quantity
     *
     * @param type
     *        the ingredient type of this new ingredient
     * @param currentState
     *        the state of this new ingredient
     * @param temperature
     *        the temperature of this new ingredient
     * @param quantity
     *        the quantity of this new ingredient
     *
     * @post the type of this ingredient is set to the given type
     *       | new.getType() == type
     * @post the current state of this ingredient is set to the given state
     *       | new.getCurrentState == currentState
     * @post the temperature of this ingredient is set to the given temperature
     *       | new.getTempertature() == temperature
     * @post the quantity of this ingredient is set to the given quantity
     *       | new.getQuantity == quantity
     *
     * @throws IllegalArgumentException
     *         the given type is null
     *         | type == null
     * @throws IllegalArgumentException
     *         the given state is null
     *         | currentState == null
     * @throws IllegalArgumentException
     *         the given temperature is null
     *         | temperature == null
     * @throws IllegalArgumentException
     *         the given quantity is negatif
     *         | quantity < 0
     */
    @Raw
    public AlchemicIngredient(IngredientType type, long quantity, STATE currentState, Temperature temperature) {
        if (type == null)
            throw new IllegalArgumentException("Ingredient type cannot be null");
        if (currentState == null)
            throw new IllegalArgumentException("CurrentState cannot be null");
        if (temperature == null)
            throw new IllegalArgumentException("Temperature mag niet null zijn.");   //// ben ik niet zeker -> ZIET ER OKE UIT
        if (quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative");

        this.type = type;
        this.currentState = currentState;
        this.temperature = temperature;
        this.quantity = quantity;
    }

    /**********************************************************
     * getters
     *********************************************************/

    /**
     * return the ingredient type of this ingredient
     */
    @Basic @Raw @Immutable
    public IngredientType getType() {
        return this.type;
    }

    /**
     * return the simple name of this ingredient
     *
     * @return the simple name of this ingredient
     *         | result.equals(getType().getSimpleName())
     */
    public String getSimpleName() {
        return this.type.getSimpleName();
    }

    /**
     * return the full name of this ingredient
     *
     * @return the full name of this ingredien
     *         | result.equals(getType().getFullName())
     */
    public String getFullName() {
        return this.type.getName().getFullName(this.temperature,type.getDefaultTemperature()); //moetn we nog fixen
    }

    /**
     * return the quantity of this ingredient
     *
     * @return the quantity of this ingredient
     *         | result.equals(getType().getQuantity())
     */
    public long getQuantity() {
        return this.quantity;
    }

    /**
     * return the state of this ingredient
     */
    public STATE getCurrentState() {
        return this.currentState;
    }

    /**
     * return de temperature of this ingredient
     */
    public Temperature getTemperature() {
        return this.temperature;
    }

    /**
     * set the state of this ingredient to the given state
     *
     * @param currentState
     *        the new state of this ingredient
     *
     * @post the current state of this ingredient is set to the given state
     *       | new.getCurrentState() == currentState
     *
     * @throws IllegalArgumentException
     *         the given state is null
     *         | currentState == null
     */
    public void setCurrentState(STATE currentState) {
        if (currentState == null)
            throw new IllegalArgumentException("CurrentState mag niet null zijn.");
        this.currentState = currentState;
    }


    /**
     * set temperature of this ingredietn to the given temperature
     *
     * @param temperature
     *        the new temperature of this ingredient
     *
     * @post the temperature of this ingredient is set to the given temperature
     *       | new.getTemperature() == temperature
     *
     * @throws IllegalArgumentException
     *         the given temperature is null
     *         | temperature == null
     */
    public void setTemperature(Temperature temperature) {
        if (temperature == null)
            throw new IllegalArgumentException("Temperature mag niet null zijn.");
        this.temperature = temperature;
    }

    /**
     * heat this ingredient by de given amount
     *
     * @param amount
     *        the amount which to heat this ingredient
     *
     * @effect the temperature of this ingredient is heated by the given amount
     *         | getTemperature.heat(amout)
     *
     * @throws IllegalArgumentException
     *         the given amoun can't be negative
     *         | amount <= 0
     */
    public void heat(long amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount moet groter dan 0 zijn.");
        this.temperature.heat(amount);
    }

    /**
     * @param amount
     *        the amount which to cool this ingredient
     *
     * @effect the temperature of this ingredient is cooled by the given amount
     *         | getTemperature.cool(amount)
     *
     * throws IllegalArgumentException
     *        the given amoun can't be negative
     *        | amount <= 0
     */
    public void cool(long amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("Amount moet groter dan 0 zijn.");
        this.temperature.cool(amount);
    }
}