package alchemy;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;

public class Transmogrifier extends Device {


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

        // DE EXCEPTIONS GAK LATER TOEVOEGEN

        List<IngredientContainer> convertedContents = new ArrayList<>();

        for (IngredientContainer container : contents) {
            AlchemicIngredient ingredient = container.getContent();

            STATE oldState = ingredient.getCurrentState();
            STATE newState;
            if (oldState == STATE.LIQUID) {
                newState = STATE.POWDER;
            } else {
                newState = STATE.LIQUID;
            }

            long oldQuantity = ingredient.getQuantity();
            long newQuantity = convertQuantity(oldQuantity, newState);

            AlchemicIngredient converted = getAlchemicIngredient(ingredient, newQuantity, newState);

            ingredient.destroy(); // ALLEEN BIJ CONTAINERS
            UNIT capacity = getContainerUnit(newQuantity, newState);
            convertedContents.add(new IngredientContainer(capacity, converted));


        }
        contents = convertedContents;
    }

    private static AlchemicIngredient getAlchemicIngredient(AlchemicIngredient ingredient, long newQuantity, STATE newState) {
        IngredientType originalType = ingredient.getType();
        Temperature currentTemperature = ingredient.getTemperature();
        AlchemicIngredient converted;

        if (originalType.isMixture()) {
            converted = new MixtureAlchemicIngredient(
                    (MixtureIngredientType) originalType, newQuantity, newState, currentTemperature);
        } else {
            converted = new AlchemicIngredient(originalType, newQuantity, newState, currentTemperature);
        }
        return converted;
    }

    private long convertQuantity ( long spoonsQuantity, STATE newState){
            if (newState == null) {
                throw new IllegalArgumentException("newState can't be null");
            }
            UNIT smallestUnit = null;
            double smallestNominal = Double.MAX_VALUE;
            for (UNIT unit : UNIT.values()) {
                if (unit.getState() == newState && unit.getSpoons() < smallestNominal) {
                    smallestNominal = unit.getSpoons();
                    smallestUnit = unit;
                }
            }

            if (smallestUnit == null) {
                throw new IllegalArgumentException("no units found for new state");
            }

            long count = (long) (spoonsQuantity / smallestNominal);
            return (long) (count * smallestNominal);
        }

    private UNIT getContainerUnit(long spoonsQuantity, STATE state) {
        if (state == null) {
            throw new IllegalArgumentException("state cannot be null");
        }

        UNIT bestUnit = null;
        double bestNominal = 0;
        for (UNIT unit : UNIT.values()) {
            if (unit.getState() == state
                    && unit.isValidContainerUnit()
                    && unit.getSpoons() <= spoonsQuantity
                    && unit.getSpoons() > bestNominal) {
                bestNominal = unit.getSpoons();
                bestUnit = unit;
            }
        }
        if (bestUnit == null) {
            double smallestNominal = Double.MAX_VALUE;
            for (UNIT unit : UNIT.values()) {
                if (unit.getState() == state
                        && unit.isValidContainerUnit()
                        && unit.getSpoons() < smallestNominal) {
                    smallestNominal = unit.getSpoons();
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
