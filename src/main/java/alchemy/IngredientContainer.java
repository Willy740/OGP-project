package alchemy;

/**
 * @invar the amount the container holds can't be zero
 *  | this.amount >= 0
 */
public class IngredientContainer {

    private final UNIT capacity;

    private AlchemicIngredient content;

    private boolean destroyed = false;

    public IngredientContainer(UNIT capacity, AlchemicIngredient content, boolean destroyed) {
        this.capacity = capacity;
        this.content = content;
        this.destroyed = destroyed;
    }

    public UNIT getCapacity() {
        return this.capacity;
    }

    public AlchemicIngredient getContent() {
        return this.content;
    }

    public boolean isEmpty() {
        if (getCapacity() == null) {
            return true;
        }
        return false;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public void empty() {
        this.capacity = null;
        this.content = null;
        this.destroyed = true;
    }
}