package alchemy;

import be.kuleuven.cs.som.annotate.*;
// alles tov spoons want dat hebben we dan nodig om de hoeveelheid van een mixture te bepalen
public enum UNITS_POWDER extends Unit{
    PINCH(1/6),
    SPOON(1),
    SACHET(7),
    BOX(42),
    SACK(126),
    CHEST(1260),
    STOREROOM(6300);

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