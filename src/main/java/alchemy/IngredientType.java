package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * @author Joran Naessens
 * @author Maxime Samyn
 */
public class IngredientType{

    /**********************************************************
     * CONSTRUCTORS
     **********************************************************/

    public IngredientType(STATE defaultState){
        if(defaultState!=null){this.defaultState = defaultState;}
        else{throw new IllegalStateException("Default state is null");}
    }

    /**********************************************************
     * DEFAULT STATE
     **********************************************************/

    private final STATE defaultState;

    public STATE getDefaultState() {
        return defaultState;
    }

}