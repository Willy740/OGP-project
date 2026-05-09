package alchemy

public class MixtureIngredientName extends IngredientName{
    private String specialName;

    public MixtureIngredientName(String specialName) {
        if  (specialName != null || specialName.equals("")){        // mag wel null zijn
            throw new IllegalArgumentException("specialName cannot be empty");
        }
        this.specialName = specialName;
    }

    public boolean isSpecialName(){
        return isValidSimpleName();
    }
    public String getSpecialName(){
        return this.specialName;
    }

    public void setSpecialName(String specialName) {
        if (specialName != null && specialName.isEmpty()) {
            throw new IllegalArgumentException("specialName cannot be empty (use null for no special name)");
        }
        this.specialName = specialName;
    }

    @Override
    public String getFullName(Temperature temperature, Temperature defaultTemperature) {
        // special name exists
        if ((this.specialName != null) && !(this.specialName.isEmpty())) {
            return this.specialName + "(" + super.getFullName(temperature, defaultTemperature) + ")";
        }
        // special name doesn't exists
        else {
            super.getFullName(temperature, defaultTemperature)
        }
    }

}