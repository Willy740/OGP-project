package alchemy

public class MixtureAlchemicIngredient{
    /**
     * getFullName(): String {«override»}                               check
     * {met specialName: "specialName (Heated/Cooled simpleName)"}
     * getSpecialName(): String                                         check
     * setSpecialName(n: String) {defensief}                            check
     */

    private MixtureIngredientType type;
    private long temperature;
    private int quantity;
    private State currrentState;

    // constructor
    public MixtureAlchemicIngredient(MixtureIngredientType type, long temperature, int quantity, State currrentState) {
        if (type == null) {
            throw new IllegalArgumentException("Mixture Ingredient type cannot be null");
        }
        if (temperature < 0) {
            throw new IllegalArgumentException("Mixture Ingredient temperature cannot be negative");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Mixture Ingredient quantity cannot be negative");
        }
        if (currrentState == null){
            throw new IllegalArgumentException("Mixture Ingredient currrent state cannot be null");
        }
        this.type = type;
        this.temperature = temperature;
        this.quantity = quantity;
        this.currrentState = currrentState;
    }
    // methods
    public String getFullName(){
        return this.type.getFullName();
    }

    public String getSpecialName(){
        return this.type.getSpecialName();
    }

    public void setSpecialName(String specialName){
        this.type.setSpecialName(specialName);
    }

    public long  getTemperature() {
        return this.type.getTemperature();
    }

    public State getCurrrentState() {
        return this.type.getDefaultState();
    }

    public int getQuantity() {
        return this.quantity;
    }

}