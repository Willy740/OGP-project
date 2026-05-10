package alchemy;

import static java.lang.Math.min;
import be.kuleuven.cs.som.annotate.*;

/**
 * @author Joran Naessens
 * @author Maxime Samyn
 *
 */
public class IngredientType{
    private final IngredientName name;
    private State defaultState;
    private Temperature defaultTemperature;

    // constructor
    public IngredientType(IngredientName name, State defaultState, Temperature defaultTemperature) {
        if (name == null) {
            this.name = new IngredientName("Default");
        } else {
            this.name = name;
        }

        if (defaultState == null) {
            this.defaultState = State.LIQUID;
        } else {
            this.defaultState = defaultState;
        }

        if (defaultTemperature == null) {
            this.defaultTemperature = new Temperature(0, 0);
        } else {
            this.defaultTemperature = defaultTemperature;
        }
    }
    public IngredientName getName(){
        return this.name;
    }

    public String getSimpleName(){
        return this.name.getSimpleName();
    }

    public String getFullName(){
        return this.name.getFullName(this.defaultTemperature, this.defaultTemperature);
    }

    public State getDefaultState(){
        return this.defaultState;
    }

    public Temperature getDefaultTemperature(){
        return this.defaultTemperature;
    }

    public boolean isMixture(){
        return this instanceof MixtureIngredientType;
    }

    // helpmethod for later classes
    public long distanceToInterval(){
        long defaulttemp = getDefaultTemperature().getHotness();
        if ((defaulttemp <= 20) && (defaulttemp >= 0)){
            return 0;
        }
        long distance = min(defaulttemp - 20, defaulttemp);
        return distance;
    }
}