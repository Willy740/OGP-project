package alchemy

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
        } else {
            return super.getFullName(temperature, defaultTemperature);
        }
    }
}