package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * an enumeration of the units of measurement for liquid alchemic ingredients
 * all nominal values are expressed in function of spoons
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public enum LiquidUnit implements Unit {
        DROP(1.0 / 8),
        SPOON(1),
        VIAL(5),
        BOTTLE(15),
        JUG(105),
        BARREL(1260),
        STOREROOM(6300);

        /**
         * variable registering the nominal value of this liquid unit expressed in spoons
         */
        private final double nominalValue;

        /**
         * initialize a new liquid unit with the given nominal value
         *
         * @param   nominalValue
         *          the nominal value of this unit expressed in spoons
         * @post    the nominal value of this unit is set to the given value
         *          | new.getNominalValue() == nominalValue
         */
        LiquidUnit(double nominalValue) {
                this.nominalValue = nominalValue;
        }

        @Override
        public double getNominalValue() {
                return nominalValue;
        }

        /**
         * return the nominal value of this liquid unit expressed in spoons
         */
        @Override @Basic @Immutable
        public boolean isValidContainerUnit() {
                // DROP en STOREROOM zijn geen geldige containergrootten
                return this != DROP && this != STOREROOM;
        }

        /**
         * check whether this liquid unit is a valid unit for a container
         *
         * @return  true if and only if this unit is not DROP and not STOREROOM
         *          | result == (this != DROP && this != STOREROOM)
         */
        @Override
        public State getState() {
                return State.LIQUID;
        }
}