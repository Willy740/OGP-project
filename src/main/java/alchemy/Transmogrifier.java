package alchemy;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;

class Transmogrifier extends Device {
    public Transmogrifier() {
        super();
    }

    @Override
    public void executeOperation() {
        if (isDestroyed()) {
            throw new IsDestroyedException("Device is already destroyed");
        }

        if (getLaboratory() == null) {
            throw new DeviceNotInLaboratoryException("Transmogrifier must be in a laboratory to be used");
        }

        if (contents.isEmpty()) {
            throw new NotEnoughIngredientLeftException("Transmogrifier must contain an ingredient to operate");
        }

        List<IngredientContainer> convertedContents = new ArrayList<>();

        for (IngredientContainer container : contents) {
            AlchemicIngredient ingredient = container.getContent();

            State oldState = ingredient.getCurrentState();
            State newState;
            if (oldState == State.LIQUID) {
                newState = State.POWDER;
            } else {
                newState = State.LIQUID;
            }

            long oldQuantity = ingredient.getQuantity();
            long newQuantity = convertQuantity(oldQuantity, newState);

            IngredientType originalType = ingredient.getType();
            Temperature currentTemperature = ingredient.getTemperature();
            AlchemicIngredient converted;

            if (originalType.isMixture()) {
                converted = new MixtureAlchemicIngredient(
                        (MixtureIngredientType) originalType, newQuantity, newState, currentTemperature);
            } else {
                converted = new AlchemicIngredient(originalType, newQuantity, newState, currentTemperature);
            }

            ingredient.destroy();
            UNIT capacity = getContainerUnit(newQuantity, newState);
            convertedContents.add(new IngredientContainer(capacity, converted, false));


        }
        contents = convertedContents;
    }

        private long convertQuantity ( long spoonsQuantity, State newState){
            if (newState == null) {
                throw new IllegalArgumentException("newState can't be null");
            }
            UNIT smallestUnit = null;
            double smallestNominal = Double.MAX_VALUE;
            for (UNIT unit : UNIT.values()) {
                if (unit.getState() == newState && unit.getNominalValue() < smallestNominal) {
                    smallestNominal = unit.getNominalValue();
                    smallestUnit = unit;
                }
            }

            if (smallestUnit == null) {
                throw new IllegalArgumentException("no units found for new state");
            }

            long count = (long) (spoonsQuantity / smallestNominal);
            return (long) (count * smallestNominal);
        }

    private UNIT getContainerUnit(long spoonsQuantity, State state) {
        if (state == null) {
            throw new IllegalArgumentException("state cannot be null");
        }

        UNIT bestUnit = null;
        double bestNominal = 0;
        for (UNIT unit : UNIT.values()) {
            if (unit.getState() == state
                    && unit.isValidContainerUnit()
                    && unit.getNominalValue() <= spoonsQuantity
                    && unit.getNominalValue() > bestNominal) {
                bestNominal = unit.getNominalValue();
                bestUnit = unit;
            }
        }
        if (bestUnit == null) {
            double smallestNominal = Double.MAX_VALUE;
            for (UNIT unit : UNIT.values()) {
                if (unit.getState() == state
                        && unit.isValidContainerUnit()
                        && unit.getNominalValue() < smallestNominal) {
                    smallestNominal = unit.getNominalValue();
                    bestUnit = unit;
                }
            }
        }

        if (bestUnit == null) {
            throw new IllegalArgumentException("no valid container units found for state: " + state);
        }
        return bestUnit;
    }

}
