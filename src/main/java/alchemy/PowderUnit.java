package alchemy;

import be.kuleuven.cs.som.annotate.*;

public enum UNITS_POWDER extends Unit{
    PINCH(1),
    SPOON(6),
    SACHET(42),
    BOX(252),
    SACK(756),
    CHEST(7560),
    STOREROOM(37800);

    private final int nominalValue;

    PowderUnit(int nominalValue) {
        this.nominalValue = nominalValue;
    }

    @Override
    public int getNominalValue() {
        return nominalValue;
    }

    @Override
    public boolean isValidContainerUnit() {
        // PINCH en STOREROOM zijn geen geldige containergrootten
        return this != PINCH && this != STOREROOM;
    }

    @Override
    public State getState() {
        return State.POWDER;
}