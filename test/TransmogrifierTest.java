package test;

class TransmogrifierTests {

    private Laboratory lab;
    private Transmogrifier transmogrifier;

    @BeforeEach
    void setUp() {
        transmogrifier = new Transmogrifier();
        lab = new Laboratory(50);
        lab.addDevice(transmogrifier);
    }

    @Test
    void executeWithoutLabThrows() {
        Transmogrifier standalone = new Transmogrifier();
        assertThrows(RuntimeException.class, standalone::executeOperation);
    }

    @Test
    void executeWithNoIngredientThrows() {
        assertThrows(RuntimeException.class, transmogrifier::executeOperation);
    }

    @Test
    void liquidToPowder() {
        AlchemicIngredient liquid = Helpmethods.makeLiquidIngredient("Water", 5);
        transmogrifier.addIngredient(Helpmethods.containerOf(liquid));
        transmogrifier.executeOperation();
        IngredientContainer result = transmogrifier.getResult();
        assertNotNull(result);
        assertEquals(State.POWDER, result.getIngredient().getCurrentState());
    }

    @Test
    void powderToLiquid() {
        AlchemicIngredient powder = Helpmethods.makePowderIngredient("Sugar", 5);
        transmogrifier.addIngredient(Helpmethods.containerOf(powder));
        transmogrifier.executeOperation();
        IngredientContainer result = transmogrifier.getResult();
        assertNotNull(result);
        assertEquals(State.LIQUID, result.getIngredient().getCurrentState());
    }

    @Test
    void typePreservedAfterTransmogrification() {
        AlchemicIngredient liquid = Helpmethods.makeLiquidIngredient("Potion", 3);
        IngredientType originalType = liquid.getType();
        transmogrifier.addIngredient(Helpmethods.containerOf(liquid));
        transmogrifier.executeOperation();
        IngredientContainer result = transmogrifier.getResult();
        assertEquals(originalType, result.getIngredient().getType());
    }

    @Test
    void originalDestroyedAfterTransmogrification() {
        AlchemicIngredient liquid = Helpmethods.makeLiquidIngredient("Water", 3);
        transmogrifier.addIngredient(Helpmethods.containerOf(liquid));
        transmogrifier.executeOperation();
        assertTrue(liquid.isDestroyed());
    }

    @Test
    void multipleIngredientsAllConverted() {
        transmogrifier.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Water", 2)));
        transmogrifier.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Oil", 2)));
        transmogrifier.executeOperation();
        IngredientContainer result = transmogrifier.getResult();
        assertNotNull(result);
        assertEquals(State.POWDER, result.getIngredient().getCurrentState());
    }
}