import alchemy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KettleTests {

    private Laboratory lab;
    private Kettle kettle;

    @BeforeEach
    void setUp() {
        kettle = new Kettle();
        lab = new Laboratory(50);
        lab.addDevice(kettle);
    }

    @Test
    void executeWithoutLabThrows() {
        Kettle standalone = new Kettle();
        assertThrows(RuntimeException.class, standalone::executeOperation);
    }

    @Test
    void executeWithFewerThanTwoIngredientsThrows() {
        kettle.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Water", 5)));
        assertThrows(RuntimeException.class, kettle::executeOperation);
    }

    @Test
    void executeWithEmptyKettleThrows() {
        assertThrows(RuntimeException.class, kettle::executeOperation);
    }

    @Test
    void mixTwoIngredientsSingleResult(){
        kettle.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Water", 5)));
        kettle.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Oil", 3)));
        kettle.executeOperation();
        IngredientContainer result = kettle.getResult();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void mixedNameContainsMixedWith(){
        kettle.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Water", 5)));
        kettle.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Oil", 3)));
        kettle.executeOperation();
        IngredientContainer result = kettle.getResult();
        String name = result.getContent().getSimpleName();
        assertTrue(name.contains("mixed with"), "Naam moet 'mixed with' bevatten maar was: " + name);
    }

    @Test
    void totalAmountIsSum(){
        AlchemicIngredient w = Helpmethods.makeLiquidIngredient("Water", 4);
        AlchemicIngredient o = Helpmethods.makeLiquidIngredient("Oil", 4);
        kettle.addIngredient(Helpmethods.containerOf(w));
        kettle.addIngredient(Helpmethods.containerOf(o));
        kettle.executeOperation();
        IngredientContainer result = kettle.getResult();
        assertTrue(result.getContent().getQuantity() > 0);
    }

    @Test
    void mixThreeIngredients(){
        kettle.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Water", 2)));
        kettle.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Oil", 2)));
        kettle.addIngredient(Helpmethods.containerOf(Helpmethods.makePowderIngredient("Sugar", 2)));
        kettle.executeOperation();
        IngredientContainer result = kettle.getResult();
        assertNotNull(result);
    }

    @Test
    void originalIngredientsDestroyedAfterMix(){
        AlchemicIngredient w = Helpmethods.makeLiquidIngredient("Water", 4);
        AlchemicIngredient o = Helpmethods.makeLiquidIngredient("Oil", 4);
        IngredientContainer cw = Helpmethods.containerOf(w);
        IngredientContainer co = Helpmethods.containerOf(o);
        kettle.addIngredient(cw);
        kettle.addIngredient(co);
        kettle.executeOperation();
        assertTrue(w.isDestroyed());
        assertTrue(o.isDestroyed());
    }
}