import alchemy.*;

public class Helpmethods{
    public static Temperature roomTemp() {
        return new Temperature(0, 20);
    }

    public static Temperature hotTemp() {
        return new Temperature(0, 200);
    }

    public static AlchemicIngredient makeLiquidIngredient(String name, float spoons) {
        IngredientType type = new IngredientType(name, STATE.LIQUID,
                new java.util.ArrayList<>(java.util.Arrays.asList(0L, 20L)));
        AlchemicIngredient ingredient = new AlchemicIngredient(type, spoons);
        return ingredient;
    }

    public static AlchemicIngredient makePowderIngredient(String name, float spoons) {
        IngredientType type = new IngredientType(name, STATE.POWDER,
                new java.util.ArrayList<>(java.util.Arrays.asList(0L, 20L)));
        AlchemicIngredient ingredient = new AlchemicIngredient(type, spoons);
        return ingredient;
    }

    public static IngredientContainer containerOf(AlchemicIngredient ingredient) {
        STATE state = ingredient.getType().getDefaultState();
        UNIT unit = null;
        double smallest = Double.MAX_VALUE;
        for (UNIT u : UNIT.values()) {
            if (u.getState() == state && u.isValidContainerUnit()
                    && u.getSpoons() < smallest) {
                smallest = u.getSpoons();
                unit = u;
            }
        }
        if (unit == null) throw new IllegalStateException("geen geldige container-unit gevonden voor staat " + state);
        return new IngredientContainer(unit, ingredient);
    }
}