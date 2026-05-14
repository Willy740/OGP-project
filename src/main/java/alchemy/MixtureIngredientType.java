package Achemy;

public class MixtureIngredientType extends IngredientType{

    public MixtureIngredientType(Set<IngredientType> types){
        super(new MixtureIngredientName(types), calculateDefaulatState(types), calculateDefaultTemperature(types) )
    }

    public boolean isMixture(){
        return true;
    }

    public String getSpecialName(){
        return ((MixtureIngredientName) getName()).getSpecialName;
    }

    public void setSpecialName(String specialName){
        ((MixtureIngredientName) getName()).setSpecialName(specialName);
    }

    public State calculateDefaultState(Set<IngredientType> types){
        IngredientType best = null;
        for(IngredientType type : types){
            if (best == null){
                best = type;
            }
            else if (type.distanceToInterval() < best.distanceToInterval()){
                best = type;
            }
            else if (type.distanceToInterval() == best.distanceToInterval()){
                if (type.getDefaultState() == State.LIQUID){
                    best = type;
                }
            }
        }
        return best.getDefaultState();
    }

    public Temperature calculateDefaultTemperature(Set<IngredientType> types){
        IngredientType best == null;
        for(IngredientType type : types) {
            if (best == null) {
                best = type;
            } else if (type.distanceToInterval() < best.distanceToInterval()) {
                best = type;
            } else if (type.distanceToInterval() == best.distanceToInterval()) {
                if (type.getDefaultTemperature().getHotness > best.getDefaultTemperature.getHotness) {
                    best = type;
                }
            }
        }
        return best.getDefaultTemperature();
    }

}