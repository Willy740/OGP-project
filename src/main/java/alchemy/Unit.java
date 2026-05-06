package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * @param unit
 */
public class Unit {
    private final int unit;

    private Unit(int unit){
        this.unit = unit;
    }

    public int tounit(){
        return this.unit;
    }

    public float toUnit(Unit unit){
        return ((float) unit.tounit() /this.unit);
}