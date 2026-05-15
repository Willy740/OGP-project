package alchemy;

import java.util.*;

/**
 * Represents an alchemical laboratory.
 *
 * A laboratory can:
 * - store ingredients
 * - retrieve ingredients
 * - host devices
 *
 * Devices may only operate while assigned to a laboratory.
 */
public class Laboratory {

    /**
     * Maximum storage capacity expressed in ??? [WIP]
     */
    private final long capacity;

    /**
     * Stored ingredients indexed by simple/special name.
     */
    private final Map<String, AlchemicIngredient> storage = new HashMap<>();

    /**
     * Registered devices.
     * Only one device per concrete class is allowed.
     */
    private final Map<Class<? extends Device>, Device> devices = new HashMap<>();


    /**********************************************************
     * Constructor
     **********************************************************/

    /**
     * Create a laboratory with the given capacity.
     *
     * @param capacity storage capacity in storerooms
     * @throws IllegalArgumentException if capacity is negative
     */
    public Laboratory(long capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative.");
        }
        this.capacity = capacity;
    }

    /**
     * Return the storage capacity.
     */
    public long getCapacity() {
        return capacity;
    }

    /**
     * Store the ingredient contained in the given container.
     *
     * The container becomes empty/destroyed after storage.
     *
     * @param container container holding the ingredient
     * @throws IllegalArgumentException if container is null or empty
     * @throws IllegalStateException if capacity would be exceeded
     */
    public void store(IngredientContainer container) {
        if (container == null || container.isEmpty()) {
            throw new IllegalArgumentException("Container is empty or null.");
        }
        AlchemicIngredient ingredient = container.getIngredient();
        ingredient = normalizeIngredient(ingredient);
        String key = ingredient.getName().getSimpleName();
        if (storage.containsKey(key)) {
            AlchemicIngredient existing = storage.get(key);
            ingredient = existing.mixWith(ingredient);
        }
        long futureQuantity = getTotalStoredQuantity()
                - getStoredQuantity(key)
                + ingredient.getQuantityInLowestUnit();
        if (futureQuantity > convertStoreroomsToLowestUnit(capacity)) {
            throw new IllegalStateException("Laboratory capacity exceeded.");
        }
        storage.put(key, ingredient);
        container.clear();
    }

    /**
     * Retrieve a specific quantity of ingredient.
     *
     * @param ingredientName simple or special name
     * @param quantity quantity to retrieve
     * @return container containing the requested ingredient
     * @throws NoSuchElementException if ingredient does not exist
     * @throws IllegalArgumentException if quantity invalid
     */
    public IngredientContainer retrieve(String ingredientName, Quantity quantity) {
        Objects.requireNonNull(ingredientName);
        Objects.requireNonNull(quantity);
        AlchemicIngredient stored = storage.get(ingredientName);
        if (stored == null) {
            throw new NoSuchElementException("Ingredient not found.");
        }
        if (stored.getQuantityInLowestUnit() < quantity.toLowestUnit()) {
            throw new IllegalArgumentException("Not enough quantity available.");
        }
        AlchemicIngredient extracted = stored.extract(quantity);
        AlchemicIngredient remaining = stored.subtract(quantity);
        if (remaining.getQuantityInLowestUnit() == 0) {
            storage.remove(ingredientName);
        } else {
            storage.put(ingredientName, remaining);
        }
        return new IngredientContainer(quantity.getUnit(), extracted);
    }

    /**
     * Retrieve the full quantity of a stored ingredient.
     *
     * @param ingredientName simple or special name
     * @return container containing the entire available quantity
     */
    public IngredientContainer retrieve(String ingredientName) {
        Objects.requireNonNull(ingredientName);
        AlchemicIngredient stored = storage.get(ingredientName);
        if (stored == null) {
            throw new NoSuchElementException("Ingredient not found.");
        }
        storage.remove(ingredientName);
        QuantityUnit containerUnit = determineLargestContainer(stored);
        return new IngredientContainer(containerUnit, stored);
    }

    /**
     * Return an immutable overview of all stored ingredients.
     */
    public Map<String, AlchemicIngredient> getStorageOverview() {
        return Collections.unmodifiableMap(new HashMap<>(storage));
    }

    /**
     * Add a device to this laboratory.
     *
     * Only one device of each type may exist.
     *
     * @param device device to add
     * @throws IllegalArgumentException if device is null
     * @throws IllegalStateException if device type already exists
     */
    public void addDevice(Device device) {
        Objects.requireNonNull(device);
        Class<? extends Device> type = device.getClass();
        if (devices.containsKey(type)) {
            throw new IllegalStateException(
                    "Only one device of each type may exist in a laboratory."
            );
        }
        devices.put(type, device);
        device.setLaboratory(this);
    }

    /**
     * Remove a device from this laboratory.
     */
    public void removeDevice(Class<? extends Device> type) {
        Device removed = devices.remove(type);
        if (removed != null) {
            removed.setLaboratory(null);
        }
    }

    /**
     * Return whether this laboratory contains the given device type.
     */
    public boolean hasDevice(Class<? extends Device> type) {
        return devices.containsKey(type);
    }

    /**
     * Get a device by type.
     */
    @SuppressWarnings("unchecked")
    public <T extends Device> T getDevice(Class<T> type) {
        return (T) devices.get(type);
    }

    /**
     * Restore ingredient to default state and default temperature.
     *
     * In a complete implementation this should use ovens,
     * cooling boxes and transmogrifiers.
     */
    private AlchemicIngredient normalizeIngredient(AlchemicIngredient ingredient) {
        IngredientType type = ingredient.getType();
        AlchemicIngredient normalized = ingredient;
        if (ingredient.getState() != type.getDefaultState()) {
            Transmogrifier transmogrifier = getDevice(Transmogrifier.class);
            if (transmogrifier == null) {
                throw new IllegalStateException(
                        "A transmogrifier is required to normalize state."
                );
            }
            normalized = transmogrifier.transform(normalized);
        }
        int[] defaultTemperature = type.getDefaultTemperature();
        int[] currentTemperature = normalized.getTemperature();
        if (currentTemperature[0] > defaultTemperature[0]) {
            CoolingBox coolingBox = getDevice(CoolingBox.class);
            if (coolingBox == null) {
                throw new IllegalStateException(
                        "A cooling box is required to normalize temperature."
                );
            }
            normalized = coolingBox.coolTo(normalized, defaultTemperature);
        }
        if (currentTemperature[1] > defaultTemperature[1]) {
            Oven oven = getDevice(Oven.class);
            if (oven == null) {
                throw new IllegalStateException(
                        "An oven is required to normalize temperature."
                );
            }
            normalized = oven.heatTo(normalized, defaultTemperature);
        }
        return normalized;
    }

    /**
     * Return total stored quantity in lowest units.
     */
    private long getTotalStoredQuantity() {
        long total = 0;
        for (AlchemicIngredient ingredient : storage.values()) {
            total += ingredient.getQuantityInLowestUnit();
        }
        return total;
    }

    /**
     * Return stored quantity for a single ingredient.
     */
    private long getStoredQuantity(String name) {
        AlchemicIngredient ingredient = storage.get(name);

        return ingredient == null
                ? 0
                : ingredient.getQuantityInLowestUnit();
    }

    /**
     * Convert storerooms to the system's lowest quantity unit.
     *
     * Placeholder implementation.
     */
    private long convertStoreroomsToLowestUnit(long storerooms) {
        return storerooms * 100000L;
    }

    /**
     * Determine the largest valid retrieval container.
     */
    private QuantityUnit determineLargestContainer(AlchemicIngredient ingredient) {
        if (ingredient.getState() == State.LIQUID) {
            return QuantityUnit.BARREL;
        }
        return QuantityUnit.CHEST;
    }
}
