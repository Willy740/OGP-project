package alchemy;

import be.kuleuven.cs.som.annotate.*;

// MixtureIngredientName.java
public class MixtureIngredientName extends IngredientName {
    private String specialName;

    // simpleName = "Beer mixed with Coke", specialName = "Mazout" of null
    public MixtureIngredientName(String simpleName, String specialName) {
        super(simpleName);
        if (specialName != null && specialName.isEmpty()) {
            throw new IllegalArgumentException("specialName cannot be empty");
        }
        this.specialName = specialName;
    }

    @Override
    public boolean isValidSimpleName(String simpleName){
        if (simpleName == null || simpleName.isEmpty()) {
            return false;
        }
        String[] parts = simpleName.split(" mixed with | and |, ");
        for (String part : parts) {
            if(!super.isValidSimpleName(part)){
                return false;
            }
        }
        return true;
    }

    public String getSpecialName() {
        return this.specialName;
    }

    public void setSpecialName(String specialName) {
        if (specialName != null && specialName.isEmpty()) {
            throw new IllegalArgumentException("specialName cannot be empty");
        }
        this.specialName = specialName;
    }

    @Override
    public String getFullName(Temperature temperature, Temperature defaultTemperature) {
        if (this.specialName != null) {
            return this.specialName + " (" + super.getFullName(temperature, defaultTemperature) + ")";
        }
        else {
            return super.getFullName(temperature, defaultTemperature);
        }
    }
}