package Alchemy;

public class MixtureAlchemicIngredient extends AlchemicIngredient{
    public MixtureAlchemicIngredient(MixtureIngredientType type, long quantity, State currentState, Temperature temperature) {
        super(type, quantity, currentState, temperature);
    }

    /**
     * return the full name of a mixture ingredient
     */
    @Override
    public String getFullName(){
        return getMixtureType().getFullName(getTemperature(), getType().getDefaultTemperature());
    }

    /**
     * return the special name of an mixture ingredient
     */
    public String getSpecialName(){
        return getMixtureType().getSpecialName();
    }

    /**
     * set the special name of a mixture ingredient to specialName
     * @param specialName
     */
    public void setSpecialName(String specialName){
    
    }
    
}