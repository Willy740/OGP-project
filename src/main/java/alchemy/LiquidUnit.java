package alchemy;

import be.kuleuven.cs.som.annotate.*;
// alles tov spoons want dat hebben we dan nodig om de hoeveelheid van een mixture te bepalen

public enum LiquidUnit implements Unit{
        DROP(1.0/8),
        SPOON(1),
        VIAL(5),
        BOTTLE(15),
        JUG(105),
        BARREL(1260),
        STOREROOM(6300);

        private final double nominalValue;

        LiquidUnit(double nominalValue) {
                this.nominalValue = nominalValue;
        }

        @Override
        public double getNominalValue() {
                return nominalValue;
        }

        @Override
        public boolean isValidContainerUnit() {
                // DROP en STOREROOM zijn geen geldige containergrootten
                return this != DROP && this != STOREROOM;
        }

        @Override
        public State getState() {
                return State.LIQUID;
}
