package alchemy;

import static java.lang.Math.min;
import be.kuleuven.cs.som.annotate.*;

/**
 * a class representing the type of an alchemic ingredient,
 * characterised by a name, a default state and a default temperature
 *
 * @invar   the name of each ingredient type must be effective
 *          | getName() != null
 * @invar   the default state of each ingredient type must be effective
 *          | getDefaultState() != null
 * @invar   the default temperature of each ingredient type must be effective
 *          | getDefaultTemperature() != null
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class IngredientType{
    /**
     * variable referencing the name of this ingredient type, final
     */
    private final IngredientName name;

    /**
     * variable referencing the default state of this ingredient type
     */
    private final STATE defaultState;

    /**
     * variable referencing the default temperature of this ingredient type
     */
    private final Temperature defaultTemperature;

    /**********************************************************
     * constructor
     **********************************************************/

    /**
     * initialize a new ingredient type with the given name, default state
     * and default temperature
     *
     * @param   name
     *          the name of this new ingredient type
     * @param   defaultState
     *          the default state of this new ingredient type
     * @param   defaultTemperature
     *          the default temperature of this new ingredient type
     *
     * @post    if the given name is effective, the name of this ingredient type
     *          is set to the given name; otherwise it is set to a default name
     *          | if (name != null)
     *          | then new.getName() == name
     *          | else new.getName().getSimpleName().equals("Default")
     * @post    if the given default state is effective, the default state of this
     *          ingredient type is set to the given state; otherwise it is set to LIQUID
     *          | if (defaultState != null)
     *          | then new.getDefaultState() == defaultState
     *          | else new.getDefaultState() == State.LIQUID
     * @post    if the given default temperature is effective, the default temperature
     *          of this ingredient type is set to the given temperature;
     *          otherwise it is set to (0, 0)
     *          | if (defaultTemperature != null)
     *          | then new.getDefaultTemperature() == defaultTemperature
     *          | else new.getDefaultTemperature().getColdness() == 0
     *          |   && new.getDefaultTemperature().getHotness()  == 0
     */
    public IngredientType(IngredientName name, STATE defaultState, Temperature defaultTemperature) {
        if (name == null) {
            this.name = new IngredientName("Default");
        } else {
            this.name = name;
        }

        if (defaultState == null) {
            this.defaultState = STATE.LIQUID;
        } else {
            this.defaultState = defaultState;
        }

        if (defaultTemperature == null) {
            this.defaultTemperature = new Temperature(0, 0);
        } else {
            this.defaultTemperature = defaultTemperature;
        }
    }

    /**
     * return the name object of this ingredient type
     */
    @Basic @Raw @Immutable
    public IngredientName getName(){
        return this.name;
    }

    /**
     * return the simple name of this ingredient type.
     *
     * @return  the simple name as provided by the name object
     *          | result.equals(getName().getSimpleName())
     */
    public String getSimpleName(){
        return this.name.getSimpleName();
    }

//    [WIP]
//    public String getFullName(){ return this.name.getFullName();}

    /**
     * return the default state of this ingredient type
     */
    @Basic @Raw
    public STATE getDefaultState(){
        return this.defaultState;
    }

    /**
     * return the default temperature of this ingredient type
     */
    public Temperature getDefaultTemperature(){
        return this.defaultTemperature;
    }

    /**
     * check whether this ingredient type is a mixture
     *
     * @return  true if and only if this ingredient type is an instance of
     *          MixtureIngredientType; false otherwise
     *          | result == (this instanceof MixtureIngredientType)
     */
    public boolean isMixture(){
        return this instanceof MixtureIngredientType;
    }

    /**
     * return the distance of the default hotness of this ingredient type
     * to the neutral temperature interval [0, 20]
     *
     * @return  0 if the default hotness lies within [0, 20]
     *          | if (getDefaultTemperature().getHotness() >= 0
     *          |      && getDefaultTemperature().getHotness() <= 20)
     *          | then result == 0
     * @return  otherwise, the minimum of (hotness - 20) and hotness
     *          | else result == min(getDefaultTemperature().getHotness() - 20,
     *          |                    getDefaultTemperature().getHotness())
     */
    public long distanceToInterval(){
        long defaulttemp = getDefaultTemperature().getHotness();
        if ((defaulttemp <= 20) && (defaulttemp >= 0)){
            return 0;
        }
        return min(defaulttemp - 20, defaulttemp);
    }
}