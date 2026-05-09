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
    private long quantity;
    private State currrentState;

    // constructor
    public MixtureAlchemicIngredient(MixtureIngredientType type, long temperature, long quantity, State currrentState) {
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
        String fullName = this.type.getFullName();
        String specialName = getSpecialName();
        return specialName + "("+ fullName + ")";
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

    public long getQuantity() {
        List<String> ingredients = this.type.getIngredientsAsList();                //
        long quantity = 0;                                                          //
        for (String ingredient : ingredients){                                      // ZAL NIET WERKEN MAAR WEET NIET
            quantity += ingredient.getQuantity();                                   // HOE OPLOSSEN
        }                                                                           //
        return quantity;                                                            //
    }

}