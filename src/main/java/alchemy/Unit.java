package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * dit is een interface omdat enums niet kunnen worden extend
 */
public interface Unit {
    int getNominalValue();

    boolean isValidContainerUnit();

    State getState();
}