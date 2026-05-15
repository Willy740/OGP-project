import alchemy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeviceBaseTests {

    private CoolingBox coolingBox;

    @BeforeEach
    void setUp() {
        coolingBox = new CoolingBox(Helpmethods.roomTemp());
    }

    @Test
    void addIngredientWithoutLabThrows() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 1);
        IngredientContainer c = Helpmethods.containerOf(ing);
        assertThrows(IllegalStateException.class, () -> coolingBox.addIngredient(c));
    }

    @Test
    void addIngredientNullThrows() {
        Laboratory lab = new Laboratory(10);
        lab.addDevice(coolingBox);
        assertThrows(IllegalArgumentException.class, () -> coolingBox.addIngredient(null));
    }

    @Test
    void addIngredientEmptyContainerThrows() {
        Laboratory lab = new Laboratory(10);
        lab.addDevice(coolingBox);
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 1);
        IngredientContainer c = Helpmethods.containerOf(ing);
        c.empty();
        assertThrows(IllegalArgumentException.class, () -> coolingBox.addIngredient(c));
    }

    @Test
    void executeOperationWithoutLabThrows() {
        assertThrows(IllegalStateException.class, () -> coolingBox.executeOperation());
    }

    @Test
    void getResultWithoutLabThrows() {
        assertThrows(IllegalStateException.class, () -> coolingBox.getResult());
    }

    @Test
    void getResultNullWhenNoResult() {
        Laboratory lab = new Laboratory(10);
        lab.addDevice(coolingBox);
        assertNull(coolingBox.getResult());
    }

    @Test
    void containerEmptiedAfterAdd() {
        Laboratory lab = new Laboratory(10);
        lab.addDevice(coolingBox);
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 1);
        IngredientContainer c = Helpmethods.containerOf(ing);
        coolingBox.addIngredient(c);
        assertTrue(c.isEmpty());
    }
}