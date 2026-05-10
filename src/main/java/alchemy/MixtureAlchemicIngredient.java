package alchemy;

import java.util.List;
import be.kuleuven.cs.som.annotate.*;

public class MixtureAlchemicIngredient extends AlchemicIngredient{
    // constructor
    public MixtureAlchemicIngredient(MixtureIngredientType type, Temperature temperature, long quantity, State currentState) {
        super(type, currentState, temperature, quantity);
    }
    @Override
    public String getFullName(){
        MixtureIngredientType mixtureType = (MixtureIngredientType) getType();
        if (mixtureType.getSpecialName == null){
            return super.getFullName
        }
        return mixtureType.getSpecialName() + "(" + super.getFullName() + ")";
    }

    public String getSpecialName(){
        return ((MixtureIngredientType) getType()).getSpecialName();
    }

    public void setSpecialName(String specialName){
        ((MixtureIngredientType) getType()).setSpecialName(specialName);
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