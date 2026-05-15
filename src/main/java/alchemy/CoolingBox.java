package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * a device that cools a single alchemic ingredient to a configurable target temperature
 *
 * Rules:
 * - Can hold only one ingredient at a time.
 * - Adding a second ingredient while one is already loaded throws an exception.
 * - Ingredients already colder than (or equal to) the target temperature are left unchanged.
 *
 * @invar   the cooling temperature of this cooling box must be valid
 *          | getCoolingTemperature() != null
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class CoolingBox extends Device {

    /**
     * The target temperature to cool ingredients to.
     */
    private Temperature coolingTemperature;

    /**********************************************************
     * Constructor
     **********************************************************/

    /**
     * Initialize a new cooling box with the given target cooling temperature.
     *
     * @param   coolingTemperature
     *          the target temperature this cooling box will cool ingredients to
     *
     * @post    the cooling temperature of this cooling box is set to the given temperature
     *          | new.getCoolingTemperature() == coolingTemperature
     *
     * @throws  IllegalArgumentException
     *          the given cooling temperature is null
     *          | coolingTemperature == null
     */
    public CoolingBox(Temperature coolingTemperature) {
        if (coolingTemperature == null) {
            throw new IllegalArgumentException("Cooling temperature cannot be null.");
        }
        this.coolingTemperature = coolingTemperature;
    }

    /**********************************************************
     * Cooling temperature
     **********************************************************/

    /**
     * Return the target cooling temperature of this cooling box.
     */
    @Basic @Raw
    public Temperature getCoolingTemperature() {
        return this.coolingTemperature;
    }

    /**
     * Set the target cooling temperature of this cooling box.
     *
     * @param   coolingTemperature
     *          the new target cooling temperature
     *
     * @post    the cooling temperature of this cooling box is set to the given temperature
     *          | new.getCoolingTemperature() == coolingTemperature
     *
     * @throws  IllegalArgumentException
     *          the given cooling temperature is null
     *          | coolingTemperature == null
     */
    public void setCoolingTemperature(Temperature coolingTemperature) {
        if (coolingTemperature == null) {
            throw new IllegalArgumentException("Cooling temperature cannot be null.");
        }
        this.coolingTemperature = coolingTemperature;
    }

    /**********************************************************
     * Adding ingredients
     **********************************************************/

    /**
     * Add the ingredient from the given container to this cooling box.
     *
     * @param   container
     *          the container holding the ingredient to add (non-null, non-empty)
     *
     * @throws  IllegalStateException
     *          this cooling box already contains an ingredient
     *          | !loadedContainers.isEmpty()
     */
    @Override
    protected void addIngredient(IngredientContainer container) {
        if (!loadedContainers.isEmpty()) {throw new DeviceFullException(this);}
        loadedContainers.add(container);
    }

    /**********************************************************
     * Execute
     **********************************************************/

    /**
     * Cool the loaded ingredient to the target cooling temperature.
     *
     * If the ingredient is already at or colder than the target temperature,
     * it is left unchanged.
     *
     * @post    if the loaded ingredient is warmer than the cooling temperature,
     *          its temperature is adjusted to the cooling temperature
     * @post    the loaded containers list is cleared after execution
     *          | loadedContainers.isEmpty()
     * @post    the result holds the (possibly cooled) ingredient
     *          | result != null
     */
    @Override
    protected void operate() {
        if (loadedContainers.isEmpty()) {
            return; // nothing to do
        }

        IngredientContainer container = loadedContainers.get(0);
        AlchemicIngredient ingredient = container.getIngredient();
        Temperature current = ingredient.getTemperature();
        Temperature target  = this.coolingTemperature;
        if (!isWarmerThan(current, target)) {
            this.result = ingredient;
        } else {
            long netCurrent = toNetValue(current);
            long netTarget  = toNetValue(target);
            long diff = netCurrent - netTarget;
            Temperature newTemp = copyTemperature(current);
            if (diff > 0) {newTemp.cool(diff);}
            else if (diff < 0) {newTemp.heat(-diff);}
            this.result = ingredient.withTemperature(newTemp);
        }
        loadedContainers.clear();
    }

    /**
     * Return whether temperature a is strictly warmer than temperature b.
     *
     * @param   a   first temperature
     * @param   b   second temperature
     *
     * @return  true if the net value of a is strictly greater than the net value of b
     *          | result == (toNetValue(a) > toNetValue(b))
     */
    private boolean isWarmerThan(Temperature a, Temperature b) {
        return toNetValue(a) > toNetValue(b);
    }

    /**
     * Convert a temperature to a single signed long value.
     * Positive means hot, negative means cold.
     *
     * @param   t   the temperature to convert
     * @return  hotness - coldness
     *          | result == t.getHotness() - t.getColdness()
     */
    private long toNetValue(Temperature t) {
        return t.getHotness() - t.getColdness();
    }

    /**
     * Create a copy of the given temperature.
     *
     * @param   t   the temperature to copy
     * @return  a new Temperature with the same coldness and hotness
     */
    private Temperature copyTemperature(Temperature t) {
        return new Temperature(t.getColdness(), t.getHotness());
    }
}
