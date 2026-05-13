package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * an enumeration of the units of measurement for powder alchemic ingredients
 * all nominal values are expressed in function of spoons
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public enum PowderUnit implements Unit{


    /**
     * variable registering the nominal value of this powder unit expressed in spoons
     */
    private final double nominalValue;

    /**
     * initialize a new powder unit with the given nominal value
     *
     * @param   nominalValue
     *          the nominal value of this unit expressed in spoons
     * @post    the nominal value of this unit is set to the given value
     *          | new.getNominalValue() == nominalValue
     */
    PowderUnit(double nominalValue) {
        this.nominalValue = nominalValue;
    }

    /**
     * return the nominal value of this powder unit expressed in spoons
     */
    @Override
    public double getNominalValue() {
        return nominalValue;
    }

    /**
     * check whether this powder unit is a valid unit for a container
     *
     * @return  true if and only if this unit is not PINCH and not STOREROOM
     *          | result == (this != PINCH && this != STOREROOM)
     */
    @Override
    public boolean isValidContainerUnit() {
        // pinch en storeroom zijn geen geldige containergrootten
        return this != PINCH && this != STOREROOM;
    }

    /**
     * return the state associated with this unit
     *
     * @return  State.POWDER
     *          | result == State.POWDER
     */

}