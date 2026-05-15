package Alchemy;

public class MixtureAlchemicIngredient extends AlchemicIngredient{
    public MixtureAlchemicIngredient(MixtureIngredientType type, long quantity, State currentState, Temperature temperature) {
        super(type, quantity, currentState, temperature);
    }
    
    public String getFullName(){
        return getMixturetype().getFullName(getTemperature(), getType().getDefaultTemperature());
    }
    
    public String getSpecialName(){
        return getMixturetype().getFullName(getTemperature(), getType().getDefaultTemperature());
    }

    public void setSpecialName(String specialName){
        getMixtureType().setSpecialName(specialName);
    }

    public MixtureIngredientType getMixtureType(){
        return (MixtureIngredientType) getType();
    }
    
}