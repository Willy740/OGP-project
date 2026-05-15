import alchemy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LaboratoryTest{

    private Laboratory lab;
    
    @BeforeEach
    void setUp() {
        lab = new Laboratory(100);
    }

    @Test
    void negativeCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Laboratory(-1));
    }

    @Test
    void capacityStored() {
        assertEquals(100, lab.getCapacity());
    }


    @Test
    void addDeviceLinksLab() {
        CoolingBox cb = new CoolingBox(Helpmethods.roomTemp());
        lab.addDevice(cb);
        assertTrue(lab.hasDevice(CoolingBox.class));
        assertEquals(lab, cb.getLaboratory());
    }

    @Test
    void addDuplicateDeviceThrows() {
        lab.addDevice(new CoolingBox(Helpmethods.roomTemp()));
        assertThrows(IllegalStateException.class, () -> lab.addDevice(new CoolingBox(Helpmethods.roomTemp())));
    }

    @Test
    void addNullDeviceThrows() {
        assertThrows(NullPointerException.class, () -> lab.addDevice(null));
    }

    @Test
    void removeDeviceUnlinksLab() {
        CoolingBox cb = new CoolingBox(Helpmethods.roomTemp());
        lab.addDevice(cb);
        lab.removeDevice(CoolingBox.class);
        assertFalse(lab.hasDevice(CoolingBox.class));
        assertNull(cb.getLaboratory());
    }

    @Test
    void getDeviceReturnsCorrect() {
        Oven oven = new Oven(Helpmethods.hotTemp());
        lab.addDevice(oven);
        assertSame(oven, lab.getDevice(Oven.class));
    }

    @Test
    void hasDeviceFalseWhenAbsent() {
        assertFalse(lab.hasDevice(Oven.class));
    }

    @Test
    void multipleDifferentDevicesAllowed() {
        lab.addDevice(new CoolingBox(Helpmethods.roomTemp()));
        lab.addDevice(new Oven(Helpmethods.hotTemp()));
        lab.addDevice(new Kettle());
        lab.addDevice(new Transmogrifier());
        assertTrue(lab.hasDevice(CoolingBox.class));
        assertTrue(lab.hasDevice(Oven.class));
        assertTrue(lab.hasDevice(Kettle.class));
        assertTrue(lab.hasDevice(Transmogrifier.class));
    }

    @Test
    void storeNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> lab.store(null));
    }

    @Test
    void storeEmptyContainerThrows() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 1);
        IngredientContainer c = Helpmethods.containerOf(ing);
        c.empty();
        assertThrows(IllegalArgumentException.class, () -> lab.store(c));
    }

    @Test
    void storeAndRetrieve() {
        AlchemicIngredient ing = Helpmethods.makeLiquidIngredient("Water", 5);
        lab.store(Helpmethods.containerOf(ing));
        IngredientContainer retrieved = lab.retrieve("Water");
        assertNotNull(retrieved);
        assertFalse(retrieved.isEmpty());
    }

    @Test
    void retrieveUnknownThrows() {
        assertThrows(java.util.NoSuchElementException.class, () -> lab.retrieve("Onbekend"));
    }

    @Test
    void storeSameIngredientTwiceMerges() {
        lab.store(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Water", 3)));
        lab.store(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Water", 3)));
        IngredientContainer retrieved = lab.retrieve("Water");
        assertTrue(retrieved.getContent().getQuantity() > 3);
    }

    @Test
    void afterRetrieveIngredientGone() {
        lab.store(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Water", 2)));
        lab.retrieve("Water");
        assertThrows(java.util.NoSuchElementException.class, () -> lab.retrieve("Water"));
    }

    @Test
    void storageOverviewContainsIngredients() {
        lab.store(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Water", 2)));
        lab.store(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Oil", 2)));
        Map<String, AlchemicIngredient> overview = lab.getStorageOverview();
        assertTrue(overview.containsKey("Water"));
        assertTrue(overview.containsKey("Oil"));
    }

    @Test
    void storageOverviewIsImmutable() {
        Map<String, AlchemicIngredient> overview = lab.getStorageOverview();
        assertThrows(UnsupportedOperationException.class, () -> overview.put("Test", Helpmethods.makeLiquidIngredient("Test", 1)));
    }

    @Test
    void storeExceedingCapacityThrows() {
        Laboratory tinyLab = new Laboratory(0);
        assertThrows(IllegalStateException.class, () -> tinyLab.store(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Water", 999999))));
    }
}