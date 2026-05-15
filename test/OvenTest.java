package test;

class OvenTests {

    private Laboratory lab;
    private Oven oven;

    @BeforeEach
    void setUp() {
        oven = new Oven(new Temperature(0, 100));
        lab = new Laboratory(10);
        lab.addDevice(oven);
    }

    @Test
    void constructorNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Oven(null));
    }

    @Test
    void setOvenTemperatureNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> oven.setOvenTemperature(null));
    }

    @Test
    void getOvenTemperature() {
        Temperature t = new Temperature(0, 100);
        Oven o = new Oven(t);
        assertEquals(t, o.getOvenTemperature());
    }

    @Test
    void ingredientCoolerThanTargetGetsHeated() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 1);
        IngredientContainer c = Helpmethods.containerOf(ing);
        oven.addIngredient(c);
        oven.executeOperation();
        IngredientContainer result = oven.getResult();

        assertNotNull(result);
        AlchemicIngredient heated = result.getIngredient();
        long netResult = heated.getHotness() - heated.getColdness();
        long netTarget = 100;
        assertTrue(Math.abs(netResult - netTarget) <= 5, "Verwarmd ingrediënt moet binnen ±5 van doel liggen, was: " + netResult);
    }

    @Test
    void ingredientAlreadyHotStaysUnchanged() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Lava", 1);
        ing.heat(180);
        long originalNet = ing.getHotness() - ing.getColdness();
        oven.addIngredient(Helpmethods.containerOf(ing));
        oven.executeOperation();
        IngredientContainer result = oven.getResult();
        assertNotNull(result);
        assertEquals(originalNet, result.getIngredient().getHotness() - result.getIngredient().getColdness());
    }

    @Test
    void addSecondIngredientThrows() {
        AlchemicIngredient ing1 = Helpmethods.makeLiquidIngredient("Water", 1);
        AlchemicIngredient ing2 = Helpmethods.makeLiquidIngredient("Oil", 1);
        oven.addIngredient(Helpmethods.containerOf(ing1));
        assertThrows(RuntimeException.class, () -> oven.addIngredient(Helpmethods.containerOf(ing2)));
    }

    @Test
    void resultClearedAfterRetrieval() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 1);
        oven.addIngredient(Helpmethods.containerOf(ing));
        oven.executeOperation();
        oven.getResult();
        assertNull(oven.getResult());
    }

    @Test
    void ovenWithoutLabThrows() {
        Oven standalone = new Oven(new Temperature(0, 100));
        assertThrows(IllegalStateException.class, () -> standalone.executeOperation());
    }
}