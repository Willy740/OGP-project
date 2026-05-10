package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * an interface for units of measurement
 *
 * @note    we had to use an interface because enums can't be extended
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public interface Unit {
    /**
     * return the nominal value of this unit, expressed in spoons
     */
    double getNominalValue();

    /**
     * check whether this unit is a valid unit for a container
     *
     * @return  true if this unit can be used as the size of a container;
     *          false otherwise
     *          the smallest and largest units (DROP/PINCH and STOREROOM)
     *          are not valid container sizes
     */
    boolean isValidContainerUnit();

    /**
     * return the state associated with this unit
     *
     * @return  the state that this unit applies to
     *          (either State.LIQUID or State.POWDER)
     */
    State getState();
}