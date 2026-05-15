package alchemy;

import be.kuleuven.cs.som.annotate.*;
import java.util.Random;

/**
 * A device that heats a single alchemic ingredient to a configurable target temperature.
 *
 * @invar can hold only one ingredient at a time.
 * @throws DeviceFullException a second ingredient while one is already loaded throws an InvalidDeviceContents exception.
 * @? The resulting temperature may deviate by up to 5 units from the target.
 * @? Ingredients already hotter than (or equal to) the target temperature are left unchanged.
 * @invar   the oven temperature of this oven must be valid
 *          | getOvenTemperature() != null
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class Oven extends Device {

    /**
     * the target temperature to heat ingredients to
     */
    private Temperature ovenTemperature;

    /**
     * random number generator for applying the +/-5 deviation
     */
    private static final Random RANDOM = new Random();

    /**
     * maximum allowed deviation from the target temperature
     */
    private static final int MAX_DEVIATION = 5;

    /**********************************************************
     * Constructor
     **********************************************************/

    /**
     * Initialize a new oven with the given target heating temperature.
     *
     * @param   ovenTemperature
     *          the target temperature this oven will heat ingredients to
     *
     * @post    the oven temperature of this oven is set to the given temperature
     *          | new.getOvenTemperature() == ovenTemperature
     *
     * @throws  IllegalArgumentException
     *          the given oven temperature is null
     *          | ovenTemperature == null
     */
    public Oven(Temperature ovenTemperature) {
        if (ovenTemperature == null) {
            throw new IllegalArgumentException("Oven temperature cannot be null.");
        }
        this.ovenTemperature = ovenTemperature;
    }

    /**********************************************************
     * Oven temperature
     **********************************************************/

    /**
     * Return the target heating temperature of this oven.
     */
    @Basic @Raw
    public Temperature getOvenTemperature() {
        return this.ovenTemperature;
    }

    /**
     * Set the target heating temperature of this oven.
     *
     * @param   ovenTemperature
     *          the new target heating temperature
     *
     * @post    the oven temperature of this oven is set to the given temperature
     *          | new.getOvenTemperature() == ovenTemperature
     *
     * @throws  IllegalArgumentException
     *          the given oven temperature is null
     *          | ovenTemperature == null
     */
    public void setOvenTemperature(Temperature ovenTemperature) {
        if (ovenTemperature == null) {
            throw new IllegalArgumentException("Oven temperature cannot be null.");
        }
        this.ovenTemperature = ovenTemperature;
    }

    /**********************************************************
     * Adding ingredients
     **********************************************************/

    /**
     * Add the ingredient from the given container to this oven.
     *
     * @param   container
     *          the container holding the ingredient to add (non-null, non-empty)
     *
     * @throws  IllegalStateException
     *          this oven already contains an ingredient
     *          | !contents.isEmpty()
     */
    @Override
    public void addIngredient(IngredientContainer container) {
        if (!contents.isEmpty()) {throw new DeviceFullException(this);}
        contents.add(container);
    }

    /**********************************************************
     * Execute
     **********************************************************/

    /**
     * heat the loaded ingredient toward the target oven temperature
     *
     * the resulting temperature may deviate by up to 5 units from the target
     * (in either direction), but is clamped to [0, Temperature.getMax()].
     * If the ingredient is already at or hotter than the target temperature,
     * it is left unchanged.
     *
     * @post    if the loaded ingredient is cooler than the oven temperature,
     *          its temperature is adjusted to approximately the oven temperature
     *          (within ±5 units deviation)
     * @post    the loaded containers list is cleared after execution
     *          | loadedContainers.isEmpty()
     * @post    the result holds the (possibly heated) ingredient
     *          | result != null
     */
    @Override
    public void executeOperation() {
        if (contents.isEmpty()) {
            return;
        }

        IngredientContainer container = contents.getFirst();
        AlchemicIngredient ingredient = container.getContent();

        Temperature current = ingredient.getTemperature();
        Temperature target  = this.ovenTemperature;

        if (!isColderThan(current, target)) {
            // Already hot enough — leave unchanged
            this.result = ingredient;
        } else {
            // Apply random deviation in [-MAX_DEVIATION, +MAX_DEVIATION]
            long deviation = RANDOM.nextInt(2 * MAX_DEVIATION + 1) - MAX_DEVIATION;

            long netTarget   = toNetValue(target);
            long netAdjusted = netTarget + deviation;

            // Clamp to [-MAX, +MAX] (negative = cold side, positive = hot side)
            long maxVal = Temperature.getMax();
            netAdjusted = Math.max(-maxVal, Math.min(maxVal, netAdjusted));

            Temperature newTemp;
            if (netAdjusted >= 0) {
                newTemp = new Temperature(0, netAdjusted);
            } else {
                newTemp = new Temperature(-netAdjusted, 0);
            }
            ingredient.setTemperature(newTemp);
            this.result = ingredient;
        }
        contents.clear();
    }

    /**
     * Return whether temperature a is strictly colder than temperature b.
     *
     * @param   a   first temperature
     * @param   b   second temperature
     *
     * @return  true if the net value of a is strictly less than the net value of b
     *          | result == (toNetValue(a) < toNetValue(b))
     */
    private boolean isColderThan(Temperature a, Temperature b) {
        return toNetValue(a) < toNetValue(b);
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
}
