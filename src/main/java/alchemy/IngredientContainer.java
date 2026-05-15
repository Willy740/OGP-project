package alchemy;

/**
 * class representing the container an ingredient can be put in
 * these containers are needed for Devices to be able to work with them
 *
 * @invar the amount the container holds can't be zero
 *  | this.amount >= 0
 * @invar the amount the container holds can't exceed the capacity
 *  | this.content.getQuantity() =< this.capacity.inSpoons()
 */
public class IngredientContainer {

    /**
     * each container has a fixed capacity that can't be exceeded
     */
    private final UNIT capacity;

    /**
     * the contents of a container
     */
    private AlchemicIngredient content;

    /**
     * tells us if the container is destroyed and can be removed by the GC
     */
    private boolean destroyed = false;

    /**********************************************************
     *                     constructor
     **********************************************************/

    public IngredientContainer(UNIT capacity, AlchemicIngredient content) {
        this.capacity = capacity;
        this.content = content;
        this.destroyed = false;
    }

    public UNIT getCapacity() {
        return this.capacity;
    }

    public AlchemicIngredient getContent() {
        return this.content;
    }

    public boolean isEmpty() {
        return getCapacity() == null;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public void empty() {
//        this.capacity = null;
        this.content = null;
        this.destroyed = true;
    }
}