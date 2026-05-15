import alchemy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoolingBoxTests {

    private Laboratory lab;
    private CoolingBox coolingBox;

    @BeforeEach
    void setUp() {
        coolingBox = new CoolingBox(new Temperature(50, 0)); // doel: coldness=50
        lab = new Laboratory(10);
        lab.addDevice(coolingBox);
    }

    @Test
    void constructorNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> new CoolingBox(null));
    }

    @Test
    void getCoolingTemperature() {
        Temperature t = new Temperature(50, 0);
        CoolingBox cb = new CoolingBox(t);
        assertEquals(t, cb.getCoolingTemperature());
    }

    @Test
    void setCoolingTemperatureNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> coolingBox.setCoolingTemperature(null));
    }

    @Test
    void ingredientWarmerThanTargetGetsCooled() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 1);
        ing.heat(80);
        IngredientContainer c = Helpmethods.containerOf(ing);

        coolingBox.addIngredient(c);
        coolingBox.executeOperation();
        IngredientContainer result = coolingBox.getResult();

        assertNotNull(result);
        AlchemicIngredient cooled = result.getContent();
        long netResult = cooled.getTemperature().getHotness() - cooled.getTemperature().getColdness();
        long netTarget = 0 - 50;
        assertTrue(netResult <= netTarget, "Gekoeld ingrediënt moet kouder of gelijk aan doel zijn");
    }

    @Test
    void ingredientAlreadyColdStaysUnchanged() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Ice", 1);
        ing.cool(80);
        long originalNet = ing.getTemperature().getHotness() - ing.getTemperature().getColdness();

        IngredientContainer c = Helpmethods.containerOf(ing);
        coolingBox.addIngredient(c);
        coolingBox.executeOperation();
        IngredientContainer result = coolingBox.getResult();

        assertNotNull(result);
        AlchemicIngredient unchanged = result.getContent();
        long resultNet = unchanged.getTemperature().getHotness() - unchanged.getTemperature().getColdness();
        assertEquals(originalNet, resultNet);
    }

    @Test
    void addSecondIngredientThrows() {
        AlchemicIngredient ing1 = Helpmethods.makeLiquidIngredient("Water", 1);
        AlchemicIngredient ing2 = Helpmethods.makeLiquidIngredient("Oil", 1);
        coolingBox.addIngredient(Helpmethods.containerOf(ing1));
        assertThrows(RuntimeException.class, () -> coolingBox.addIngredient(Helpmethods.containerOf(ing2)));
    }

    @Test
    void resultClearedAfterRetrieval() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 1);
        coolingBox.addIngredient(Helpmethods.containerOf(ing));
        coolingBox.executeOperation();
        coolingBox.getResult();
        assertNull(coolingBox.getResult());
    }
}