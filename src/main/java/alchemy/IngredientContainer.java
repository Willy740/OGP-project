package alchemy;

/**
 * @invar the amount the container holds can't be zero
 *  | this.amount >= 0
 */
public class IngredientContainer {

    private Unit capacity;

    private AlchemicIngredient content;

    private boolean destroyed;
}