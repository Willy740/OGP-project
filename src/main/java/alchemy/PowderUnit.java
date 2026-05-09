package alchemy;

import be.kuleuven.cs.som.annotate.*;
// alles tov spoons want dat hebben we dan nodig om de hoeveelheid van een mixture te bepalen
public enum UNITS_POWDER implements Unit{
    PINCH(1.0/6),
    SPOON(1),
    SACHET(7),
    BOX(42),
    SACK(126),
    CHEST(1260),
    STOREROOM(6300);

    private final double nominalValue;

    PowderUnit(double nominalValue) {
        this.nominalValue = nominalValue;
    }

    @Override
    public double getNominalValue() {
        return nominalValue;
    }

    @Override
    public boolean isValidContainerUnit() {
        // pinch en storeroom zijn geen geldige containergrootten
        return this != PINCH && this != STOREROOM;
    }

    @Override
    public State getState() {
        return State.POWDER;
}