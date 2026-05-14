package Alchemy;

public class MixtureAlchemicIngredient extends AlchemicIngredient{

    /*************************************
     *              NAME                 *
     *************************************/

    private IngredientName name = "";

    public IngredientName getName(){
        return this.name;
    }

    /*************************************
     *               TYPE                *
     *************************************/

    private final MixtureIngredientType type;

    public MixtureIngredientType getType() {return type;}

}