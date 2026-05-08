package alchemy;

import be.kuleuven.cs.som.annotate.*;

public enum LiquidUnit implements Unit{
        DROP(1),
        SPOON(8),
        VIAL(40),
        BOTTLE(120),
        JUG(840),
        BARREL(10080),
        STOREROOM(50400);

        private final int nominalValue;

        LiquidUnit(int nominalValue) {
                this.nominalValue = nominalValue;
        }

        @Override
        public int getNominalValue() {
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
