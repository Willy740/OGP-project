package alchemy;

import static java.lang.Math.min;
import be.kuleuven.cs.som.annotate.*;

/**
 * @author Joran Naessens
 * @author Maxime Samyn
 *
 *
 * getName(): IngredientName                                    done
 * getSimpleName(): String                                      check
 * getDefaultState(): State                                     done
 * getDefaultTemp(): Temperature                                check/done heb geen idee
 * isMixture(): boolean {«abstract»}                            check
 * distanceToInterval(): long {afstand defaultTemp tot [0,20]}  done
 *
 *
 */
public class IngredientType{
    private final IngredientName name;
    private State defaultState;
    private Temperature defaultTemperature;

    // constructor
    public IngredientType(IngredientName name, State defaultState, Temperature defaultTemperature) {
        this.name = name;
        this.defaultState = defaultState;
        this.defaultTemperature = defaultTemperature;
    }
    public IngredientName getName(){
        return this.name;
    }

    public String getSimpleName(){
        return this.name.getSimpleName();
    }

    public State getDefaultState(){
        return this.defaultState;
    }

    public Temperature getDefaultTemperature(){
        return this.defaultTemperature;                     //// weet niet of je het moet halen uit de klasse Temperatuur of gewoon hier
    }

    public boolean isMixture(){
        return this.name.isMixture();
    }

    // helpmethod for later classes
    public long DistanceToInterval(){
        long distance = 0;
        long defaulttemp = getDefaultTemperature();
        if ((defaulttemp <= 20) && (defaulttemp >= 0)){
            return distance;
        }
        distance = min(defaulttemp - 20, defaulttemp);
        return distance;
    }
}