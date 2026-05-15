package test;

// testing basics
class IngredientContainerTests {

    @Test
    void newContainerIsNotEmpty() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 1);
        IngredientContainer c = Helpmethods.containerOf(ing);
        assertFalse(c.isEmpty());
    }

    @Test
    void afterEmptyContainerIsEmpty() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 1);
        IngredientContainer c = Helpmethods.containerOf(ing);
        c.empty();
        assertTrue(c.isEmpty());
    }
}