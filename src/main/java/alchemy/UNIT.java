package alchemy;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;

/**
 * an enumeration of the units of measurement for alchemic ingredients
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public enum UNIT {

    DROP(1.0/8, STATE.LIQUID),
    SPOON(1, STATE.LIQUID),
    VIAL(5, STATE.LIQUID),
    BOTTLE(15, STATE.LIQUID),
    JUG(105, STATE.LIQUID),
    BARREL(1260, STATE.LIQUID),
    STOREROOM(6300, STATE.LIQUID),

    PINCH(1.0/6, STATE.POWDER),
    SPOON(1, STATE.POWDER),
    SACHET(42, STATE.POWDER),
    BOX(42, STATE.POWDER),
    SACK(126, STATE.POWDER),
    CHEST(1260, STATE.POWDER),
    STOREROOM(6300, STATE.POWDER);

    /**
     * variable registering the nominal value of this unit
     */
    private final double spoons;

    /**
     * variable keeping track of the state the unit is expressed in
     */
    private final STATE state;

    /**
     * initialize a new unit with the given nominal value and state
     *
     * @param   spoons
     *          the value of this unit expressed in spoons
     *
     * @param   state
     *          the state the unit is in
     *
     * @post    the nominal value of this unit is set to the given value
     *          | new.getNominalValue() == nominalValue
     */
    UNIT(double spoons, STATE state) {
        this.spoons = spoons;
        this.state = state;
    }
    public double getSpoons() {
        return this.spoons;
    }

    public STATE getState() {
        return this.state;
    }

    /**
     * check whether this liquid unit is a valid unit for a container
     *
     * @return  true if and only if this unit is not DROP and not STOREROOM
     *          | result == (this != DROP && this != STOREROOM)
     */
    @Basic
    @Immutable
    public boolean isValidContainerUnit() {
        // DROP en STOREROOM zijn geen geldige containergrootten
        return this != DROP && this != STOREROOM;
    }


}
