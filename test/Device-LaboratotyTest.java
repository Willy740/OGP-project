import alchemy.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IntegrationTests {

    @Test
    void coolingBoxFullWorkflow() {
        Laboratory lab = new Laboratory(20);
        CoolingBox cb = new CoolingBox(new Temperature(30, 0));
        lab.addDevice(cb);

        AlchemicIngredient hot = Helpmethods.makeLiquidIngredient("Potion", 2);
        hot.heat(60); // net=80

        cb.addIngredient(Helpmethods.containerOf(hot));
        cb.executeOperation();
        IngredientContainer result = cb.getResult();

        assertNotNull(result);
        long net = result.getContent().getTemperature().getHotness() - result.getContent().getTemperature().getColdness();
        assertTrue(net <= -30, "Moet gekoeld zijn tot ≤ -30, maar was: " + net);
    }

    @Test
    void ovenFullWorkflow() {
        Laboratory lab = new Laboratory(20);
        Oven oven = new Oven(new Temperature(0, 80));
        lab.addDevice(oven);

        AlchemicIngredient cold = Helpmethods.makeLiquidIngredient("Broth", 2);
        cold.cool(40); // net=-40

        oven.addIngredient(Helpmethods.containerOf(cold));
        oven.executeOperation();
        IngredientContainer result = oven.getResult();

        assertNotNull(result);
        long net = result.getContent().getTemperature().getHotness() - result.getContent().getTemperature().getColdness();
        assertTrue(net >= 75 && net <= 85,
                "Moet verwarmd zijn naar +/-5 van 80, maar was: " + net);
    }

    @Test
    void kettleResultStoredInLab(){
        Laboratory lab = new Laboratory(100);
        Kettle kettle = new Kettle();
        lab.addDevice(kettle);

        kettle.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Herb", 3)));
        kettle.addIngredient(Helpmethods.containerOf(Helpmethods.makeLiquidIngredient("Base", 3)));
        kettle.executeOperation();

        IngredientContainer result = kettle.getResult();
        assertNotNull(result);
        lab.store(result);

        Map<String, AlchemicIngredient> overview = lab.getStorageOverview();
        // Mengsel moet in storage zitten (naam bevat 'mixed with')
        boolean foundMixture = overview.keySet().stream()
                .anyMatch(k -> k.contains("mixed with"));
        assertTrue(foundMixture, "Mengsel niet gevonden in lab storage");
    }
}