package alchemy;

import be.kuleuven.cs.som.annotate.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class representing a device that can process alchemic ingredients.
 *
 * Devices are the only way to modify alchemic ingredients. Every device:
 * - accepts ingredient quantities via ingredient containers
 * - can execute its specific operation
 * - can return the result in a new ingredient container
 *
 * @invar a device must be located inside a laboratory before it can be used.
 *
 * @invar the laboratory of this device is either null or a laboratory
 *        that contains this device
 *          | getLaboratory() == null || getLaboratory().hasDevice(this)
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public abstract class Device {

    /**
     * The laboratory this device is currently located in, or null if none.
     */
    private Laboratory laboratory = null;

    public getLaboratory(){return this.laboratory;}


    /**
     * The list of ingredient containers that have been loaded into this device.
     */
    protected List<IngredientContainer> contents = new ArrayList<>();

    /**
     * The result of the last executeOperation() call, stored as a quantity in spoons.
     * Null if no result is available yet.
     */
    protected AlchemicIngredient result = null;

    /**********************************************************
     * Laboratory — BIDIRECTIONAL
     **********************************************************/

    /**
     * Return the laboratory this device is located in.
     */
    @Basic @Raw
    public Laboratory getLaboratory() {
        return this.laboratory;
    }

    /**
     * Set the laboratory this device is located in.
     *
     * @param   laboratory
     *          the new laboratory for this device
     *
     * @post    the laboratory of this device is set to the given laboratory
     *          | new.getLaboratory() == laboratory
     */
    @Raw
    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    /**
     * Check whether this device is currently inside a laboratory.
     *
     * @return  true if and only if the laboratory of this device is not null
     *          | result == (getLaboratory() != null)
     */
    public boolean isInLaboratory() {
        return this.laboratory != null;
    }

    /**********************************************************
     * Adding ingredients
     **********************************************************/

    /**
     * Add an ingredient from the given container to this device.
     *
     * The container becomes empty after this operation and is permanently destroyed.
     *
     * @param   container
     *          the ingredient container to add
     *
     * @post    the container is emptied after adding
     *          | container.isEmpty()
     *
     * @throws  IllegalStateException
     *          this device is not located in a laboratory
     *          | !isInLaboratory()
     * @throws  IllegalArgumentException
     *          the given container is null or empty
     *          | container == null || container.isEmpty()
     */
    public void addIngredient(IngredientContainer container) {
        if (!isInLaboratory()) {
            throw new IllegalStateException("Device must be in a laboratory to be used.");
        }
        if (container == null || container.isEmpty()) {
            throw new IllegalArgumentException("Container must not be null or empty.");
        }
        addIngredientImpl(container);
        container.empty();
    }

    /**
     * Template method for subclasses to define how an ingredient is added.
     *
     * @param   container
     *          a non-null, non-empty container (already validated)
     */
    protected void addIngredient(IngredientContainer container);

    /**********************************************************
     * Executing the operation
     **********************************************************/

    /**
     * Execute this device's specific alchemical operation on the loaded ingredients.
     *
     * @throws  IllegalStateException
     *          this device is not located in a laboratory
     *          | !isInLaboratory()
     */
    public void executeOperation() {
        if (!isInLaboratory()) {
            throw new IllegalStateException("Device must be in a laboratory to be used.");
        }
        executeOperation();
    }

    /**
     * Template method for subclasses to implement the actual operation.
     */
    protected void operate();

    /**********************************************************
     * Retrieving the result
     **********************************************************/

    /**
     * Retrieve the result of this device's last operation as a new ingredient container.
     *
     * Only one result container can be returned. Excess ingredient beyond the
     * container's capacity is lost.
     *
     * @return  a new ingredient container holding the result, or null if no result
     *          is available
     *
     * @post    after retrieval, the internal result is cleared
     *          | new.result == null
     *
     * @throws  IllegalStateException
     *          this device is not located in a laboratory
     *          | !isInLaboratory()
     */
    public IngredientContainer getResult() {
        if (!isInLaboratory()) {
            throw new IllegalStateException("Device must be in a laboratory to be used.");
        }
        if (this.result == null) {
            return null;
        }
        IngredientContainer container = buildResultContainer(this.result);
        this.result = null;
        return container;
    }

    /** [WIP]
     * Build an appropriate ingredient container for the given ingredient result.
     *
     * The container is chosen to be the largest valid container unit that does
     * not exceed the ingredient's quantity. Excess quantity beyond the container
     * capacity is lost.
     *
     * @param   ingredient
     *          the resulting ingredient to package
     *
     * @return  a new ingredient container holding as much of the ingredient as fits
     */
    protected IngredientContainer buildResultContainer(AlchemicIngredient ingredient) {
        UNIT bestUnit = null;
        double spoons = ingredient.getQuantityInSpoons();
        STATE state = ingredient.getCurrentState();

        for (UNIT unit : UNIT.values()) {
            if (unit.getState() == state && unit.isValidContainerUnit()) {
                if (unit.getNominalValue() <= spoons) {
                    if (bestUnit == null || unit.getNominalValue() > bestUnit.getNominalValue()) {
                        bestUnit = unit;
                    }
                }
            }
        }

        if (bestUnit == null) {
            for (UNIT unit : UNIT.values()) {
                if (unit.getState() == state && unit.isValidContainerUnit()) {
                    if (bestUnit == null || unit.getNominalValue() < bestUnit.getNominalValue()) {
                        bestUnit = unit;
                    }
                }
            }
        }
        return new IngredientContainer(bestUnit, ingredient);
    }
}
