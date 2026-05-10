package alchemy

import be.kuleuven.cs.som.annotate.*;

public class MixtureAlchemicIngredient extends AlchemicIngredient{
    // constructor
    public MixtureAlchemicIngredient(MixtureIngredientType type, long temperature, long quantity, State currrentState) {
        super(type, currentState, temperature,quantity);
    }
    @Override
    public String getFullName(){
        MixtureIngredientType mixtureType = (MixtureIngredientType) getType();
        return mixtureType.getSpecialName() + "(" + super.getFullName() + ")"
    }

    public String getSpecialName(){
        return ((MixtureIngredientType) getType()).getSpecialName();
    }

    public void setSpecialName(String specialName){
        ((MixtureIngredientType) getType()).setSpecialName(specialName);
    }

    public long  getTemperature() {
        return this.type.getDefaultTemperature();
    }

    public State getCurrrentState() {
        return this.type.getDefaultState();
    }

    /// quantity is sum ( rounding loss with Stateconversion)
    public long getQuantity() {
        List<AlchemicIngredient> ingredients = ((MixtureIngredientType) getType()).getIngredientsAsList();
        long quantity = 0;
        for (AlchemicIngredient ingredient : ingredients){
            quantity += ingredient.getQuantity();
        }
        return quantity;
    }

}