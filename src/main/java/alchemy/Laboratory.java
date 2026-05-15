package alchemy;

import be.kuleuven.cs.som.annotate.*;
import java.util.*;

/**
 * a class representing an alchemical laboratory.
 *
 * a laboratory stores alchemic ingredients, keeps devices, and is the only
 * place in which devices may operate. Every stored ingredient is kept at
 * its default state and default temperature.
 * incoming ingredients are normalised automatically using the devices present in the laboratory.
 * Ingredients with the same simple name are merged together.
 *
 * @invar   the capacity of this laboratory is non-negative
 *          | getCapacity() >= 0
 * @invar   the total quantity stored never exceeds the capacity
 *          | getTotalStoredSpoons() <= spoonsPerStoreroom() * getCapacity()
 * @invar   the storage map does not contain null keys or null values
 *          | for each entry in storage: entry.getKey() != null
 *          |                         && entry.getValue() != null
 * @invar   at most one device of each concrete type is registered
 *          | for each type in devices.keySet(): devices.get(type).getClass() == type
 * @invar   every registered device reports this laboratory as its laboratory
 *          | for each device in devices.values(): device.getLaboratory() == this
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class Laboratory {

    /**
     * Maximum storage capacity expressed in storerooms.
     */
    private final long capacity;

    /**
     * Stored ingredients indexed by simple name.
     */
    private final Map<String, AlchemicIngredient> storage = new HashMap<>();

    /**
     * Secondary index that maps special name to simple name, so that retrieve()
     * calls using a special name can still find the ingredient. (iets handiger vo mixtures)
     */
    private final Map<String, String> specialNameIndex = new HashMap<>();

    /**
     * Registered devices, at most one per concrete class.
     */
    private final Map<Class<? extends Device>, Device> devices = new HashMap<>();

    /**
     * The number of spoons in one liquid storeroom.
     */
    private static final long LIQUID_SPOONS_PER_STOREROOM = 6300L;

    /**
     * The number of spoons in one powder storeroom. (TOEVALLIG ETZELFDE??)
     */
    private static final long POWDER_SPOONS_PER_STOREROOM = 6300L;


    /**********************************************************
     * Constructor
     **********************************************************/

    /**
     * Create a new laboratory with the given capacity.
     *
     * @param   capacity
     *          the maximum storage capacity of this laboratory, expressed in storerooms
     *
     * @post    the capacity of this laboratory is set to the given capacity
     *          | new.getCapacity() == capacity
     *
     * @throws  IllegalArgumentException
     *          the given capacity is negative
     *          | capacity < 0
     */
    public Laboratory(long capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Capacity cannot be negative.");
        this.capacity = capacity;
    }

    /**********************************************************
     * Capacity
     **********************************************************/

    /**
     * Return the maximum storage capacity of this laboratory in storerooms.
     */
    @Basic @Raw @Immutable
    public long getCapacity() {
        return this.capacity;
    }

    /**
     * Return the total quantity currently stored, expressed in spoons.
     *
     * @return  the sum of the quantities (in spoons) of all stored ingredients
     *          | result == sum of ingredient.getQuantity() for each ingredient in storage
     */
    public long getTotalStoredSpoons() {
        long total = 0;
        for (AlchemicIngredient ingredient : storage.values()) {
            total += ingredient.getQuantity();
        }
        return total;
    }

    /**
     * Return the maximum number of spoons this laboratory can store.
     *
     * @return  getCapacity() * LIQUID_SPOONS_PER_STOREROOM (toch etzelfde)
     *          | result == getCapacity() * 6300L
     */
    private long capacityInSpoons() {
        return this.capacity * LIQUID_SPOONS_PER_STOREROOM;
    }

    /**********************************************************
     *                     ingredients
     **********************************************************/

    /**
     * Store the ingredient contained in the given container in this laboratory.
     *
     * The ingredient is first normalised to its default state and default
     * temperature using the devices present in this laboratory.  If an
     * ingredient with the same simple name is already stored, the two
     * quantities are merged. The container is destroyed
     * after its contents have been transferred.
     *
     * @param   container
     *          the container holding the ingredient to store
     *
     * @post    the ingredient (or merged ingredient) is present in storage
     *          under its simple name
     * @post    the container is destroyed after storage
     *          | container.isDestroyed()
     *
     * @throws  IllegalArgumentException
     *          the given container is null, empty, or already destroyed
     *          | container == null || container.getContent() == null
     *          |     || container.isDestroyed()
     * @throws  IllegalStateException
     *          storing the ingredient would exceed the laboratory's capacity
     *          | getTotalStoredSpoons() - currentlyStored + normalised.getQuantity()
     *          |     > capacityInSpoons()
     * @throws  IllegalStateException
     *          a required device is absent
     *          and needed for storage processing
     */
    public void store(IngredientContainer container) {
        if (container == null || container.getContent() == null || container.isDestroyed())
            throw new IllegalArgumentException("Container must be non-null, non-empty, and not destroyed.");

        AlchemicIngredient ingredient = container.getContent();
        ingredient = normalise(ingredient);

        String key = ingredient.getSimpleName();

        long alreadyStored = storage.containsKey(key)
                ? storage.get(key).getQuantity()
                : 0L;

        long futureTotal = getTotalStoredSpoons() - alreadyStored + ingredient.getQuantity();
        if (futureTotal > capacityInSpoons())
            throw new IllegalStateException("Storing this ingredient would exceed the laboratory's capacity.");

        if (storage.containsKey(key)) {
            ingredient = mergeWithStored(storage.get(key), ingredient);
        }

        storage.put(key, ingredient);

        // vo de specialname up te daten, anders wordt het verkeerde bijgehouden alsje eentje e mix maakt
        // hier ook mss apparte functie?
        if (ingredient instanceof MixtureAlchemicIngredient) {
            String special = ((MixtureAlchemicIngredient) ingredient).getSpecialName();
            if (special != null) {
                specialNameIndex.put(special, key);
            }
        }

        container.empty();
    }


    /**
     * Retrieve a specific quantity of the ingredient identified by the given name.
     *
     * The name may be the simple name or, for mixtures, the special name.
     * A new container is created and returned. the laboratory's stock is
     * reduced by the requested quantity. If the remaining stock reaches
     * zero the ingredient is removed from storage entirely.
     *
     * @param   name
     *          the simple or special name of the ingredient to retrieve
     * @param   unit
     *          the quantity unit determining how much to retrieve
     *
     * @return  a new container holding the requested quantity of the ingredient
     *          | result.getContent().getQuantity() == unit.getSpoons()
     *          | result.getCapacity() == unit
     *
     * @throws  IllegalArgumentException
     *          name is null, or unit is null, or unit is not a valid container unit
     *          | name == null || unit == null || !unit.isValidContainerUnit()
     * @throws  NoSuchElementException
     *          no ingredient with the given name is stored in this laboratory
     *          | !storage.containsKey(resolvedKey(name))
     * @throws  IllegalArgumentException
     *          the stored quantity is less than the requested quantity
     *          | storage.get(resolvedKey(name)).getQuantity() < unit.getSpoons()
     */
    public IngredientContainer retrieve(String name, UNIT unit) {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null.");
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null.");
        if (!unit.isValidContainerUnit())
            throw new IllegalArgumentException("Unit is not a valid container unit: " + unit);

        String key = resolveKey(name);
        AlchemicIngredient stored = storage.get(key);
        if (stored == null)
            throw new NoSuchElementException("No ingredient named '" + name + "' found in this laboratory.");

        long requestedSpoons = (long) unit.getSpoons();
        if (stored.getQuantity() < requestedSpoons)
            throw new IllegalArgumentException(
                    "Not enough quantity available: requested " + requestedSpoons
                            + " spoons but only " + stored.getQuantity() + " available.");

        AlchemicIngredient extracted = buildIngredient(stored, requestedSpoons);

        // vo de storage te updaten -> mss in e apparte functie??
        long remaining = stored.getQuantity() - requestedSpoons;
        if (remaining == 0) {
            removeFromStorage(key, stored);
        } else {
            storage.put(key, buildIngredient(stored, remaining));
        }

        return new IngredientContainer(unit, extracted);
    }

    /**
     * Retrieve the full available quantity of the ingredient by the given name.
     *
     * If the full quantity exceeds the largest valid container,
     * the excess is lost and only one container is returned.
     *
     * @param   name
     *          the simple or special name of the ingredient to retrieve
     *
     * @return  a new container holding as much of the ingredient as fits in
     *          the largest valid container for its state
     *
     * @throws  IllegalArgumentException
     *          name is null
     *          | name == null
     * @throws  NoSuchElementException (word gesmeten door de contents lijst)
     *          no ingredient with the given name is stored in this laboratory
     *          | !storage.containsKey(resolvedKey(name))
     */
    public IngredientContainer retrieve(String name) {
        if (name == null)
            throw new IllegalArgumentException("Name cannot be null.");

        String key = resolveKey(name);
        AlchemicIngredient stored = storage.get(key);
        if (stored == null)
            throw new NoSuchElementException("No ingredient named '" + name + "' found in this laboratory.");

        removeFromStorage(key, stored);

        UNIT containerUnit = largestContainerFor(stored.getCurrentState());
        long containerSpoons = (long) containerUnit.getSpoons();
        long quantityToReturn = Math.min(stored.getQuantity(), containerSpoons);

        AlchemicIngredient extracted = buildIngredient(stored, quantityToReturn);
        return new IngredientContainer(containerUnit, extracted);
    }

    /**
     * Return an overview of all stored ingredients.
     *
     * The returned map is a copy so it does not affect the
     * laboratory's storage. Each entry maps a simple name to the stored
     * ingredient.
     *
     * @return  an unmodifiable copy of the internal storage map
     * (mooi voorbeeldje van encapsulatie :) )
     *
     *          | result is unmodifiable
     *          | result.equals(storage)
     */
    public Map<String, AlchemicIngredient> getStorageOverview() {
        return Map.copyOf(storage);
    }

    /**********************************************************
     *                       devices
     **********************************************************/

    /**
     * Add the given device to this laboratory.
     *
     * Only one device of each type may be present at a time.
     * The bidirectional link between device and laboratory is established.
     *
     * @param   device
     *          the device to add
     *
     * @post    this laboratory contains the given device
     *          | hasDevice(device.getClass())
     * @post    the device reports this laboratory as its laboratory
     *          | device.getLaboratory() == this
     *
     * @throws  IllegalArgumentException
     *          the given device is null
     *          | device == null
     * @throws  IllegalStateException
     *          a device of the same concrete type is already registered
     *          | hasDevice(device.getClass())
     */
    public void addDevice(Device device) {
        if (device == null)
            throw new IllegalArgumentException("Device cannot be null.");
        Class<? extends Device> type = device.getClass();
        if (devices.containsKey(type))
            throw new DeviceAlreadyPresentException(this, device);
        devices.put(type, device);
        device.setLaboratory(this);
    }

    /**
     * Remove the device of the given type from this laboratory.
     *
     * If no such device is registered this method has no effect.
     * The bidirectional link is severed.
     *
     * @param   type
     *          the concrete class of the device to remove
     *
     * @post    this laboratory no longer contains a device of the given type
     *          | !hasDevice(type)
     * @post    if a device was removed, it no longer references this laboratory
     *          | old device.getLaboratory() == null
     *
     * @throws  IllegalArgumentException
     *          the given type is null
     *          | type == null
     */
    public void removeDevice(Class<? extends Device> type) {
        if (type == null)
            throw new IllegalArgumentException("Device type cannot be null.");
        Device removed = devices.remove(type);
        if (removed != null)
            removed.setLaboratory(null);
    }

    /**
     * Return whether this laboratory contains a device of the given type.
     *
     * @param   type
     *          the concrete device class to look up
     *
     * @return  true if and only if a device of the given type is registered
     *          | result == devices.containsKey(type)
     *
     * @throws  IllegalArgumentException
     *          the given type is null
     *          | type == null
     */
    public boolean hasDevice(Class<? extends Device> type) {
        if (type == null)
            throw new IllegalArgumentException("Device type cannot be null.");
        return devices.containsKey(type);
    }

    /**
     * Return whether this laboratory contains the given device instance.
     *
     * @param   device
     *          the device to look up
     *
     * @return  true if and only if the given device is registered in this laboratory
     *          | result == (device != null && devices.get(device.getClass()) == device)
     */
    public boolean hasDevice(Device device) {
        if (device == null) return false;
        return devices.get(device.getClass()) == device;
    }

    /**
     * Return the device of the given type registered in this laboratory,
     * or null if no such device is present.
     *
     * @param   type
     *          the concrete device class to look up
     *
     * @return  the registered device of the given type, or null
     *          | result == devices.get(type)
     */
    @SuppressWarnings("unchecked")
    public <T extends Device> T getDevice(Class<T> type) {
        return (T) devices.get(type);
    }

    /**********************************************************
     *                    normalisation
     **********************************************************/

    /**
     * Normalise the given ingredient to its default state and default temperature.
     *
     * State is corrected first (via the Transmogrifier), then temperature is
     * corrected (via the CoolingBox if too hot, or the Oven if too cold).
     *
     * @param   ingredient
     *          the ingredient to normalise
     *
     * @return  an ingredient of the same type with default state and temperature
     *
     * @throws  IllegalStateException
     *          a required device is absent from this laboratory
     */
    private AlchemicIngredient normalise(AlchemicIngredient ingredient) {
        IngredientType type = ingredient.getType();

        if (ingredient.getCurrentState() != type.getDefaultState()) {
            Transmogrifier t = getDevice(Transmogrifier.class);
            if (t == null)
                throw new IllegalStateException(
                        "A Transmogrifier is required to normalise the ingredient's state.");
            IngredientContainer in = new IngredientContainer(
                    largestContainerFor(ingredient.getCurrentState()), ingredient);
            t.addIngredient(in);
            t.executeOperation();
            IngredientContainer out = t.getResult();
            ingredient = out.getContent();
        }

        Temperature current = ingredient.getTemperature();
        Temperature defaultTemp = type.getDefaultTemperature();
        long netCurrent = current.getHotness() - current.getColdness();
        long netDefault = defaultTemp.getHotness() - defaultTemp.getColdness();

        if (netCurrent > netDefault) {
            CoolingBox cb = getDevice(CoolingBox.class);
            if (cb == null)
                throw new IllegalStateException(
                        "A CoolingBox is required to normalise the ingredient's temperature.");
            cb.setCoolingTemperature(new Temperature(
                    defaultTemp.getColdness(), defaultTemp.getHotness()));
            IngredientContainer in = new IngredientContainer(
                    largestContainerFor(ingredient.getCurrentState()), ingredient);
            cb.addIngredient(in);
            cb.executeOperation();
            IngredientContainer out = cb.getResult();
            ingredient = out.getContent();
        }

        if (netCurrent < netDefault) {
            Oven oven = getDevice(Oven.class);
            if (oven == null)
                throw new IllegalStateException(
                        "An Oven is required to normalise the ingredient's temperature.");
            oven.setOvenTemperature(new Temperature(
                    defaultTemp.getColdness(), defaultTemp.getHotness()));
            IngredientContainer in = new IngredientContainer(
                    largestContainerFor(ingredient.getCurrentState()), ingredient);
            oven.addIngredient(in);
            oven.executeOperation();
            IngredientContainer out = oven.getResult();
            ingredient = out.getContent();
        }

        return ingredient;
    }

    /**
     * Merge two ingredients of the same simple name via the Kettle.
     *
     * @param   existing
     *          the ingredient already in storage
     * @param   incoming
     *          the freshly normalised ingredient to merge in
     *
     * @return  a single merged ingredient
     *
     * @throws  IllegalStateException
     *          no Kettle is present in this laboratory
     */
    private AlchemicIngredient mergeWithStored(AlchemicIngredient existing,
                                               AlchemicIngredient incoming) {
        Kettle kettle = getDevice(Kettle.class);
        if (kettle == null)
            throw new IllegalStateException(
                    "A Kettle is required to merge ingredients of the same type.");

        IngredientContainer c1 = new IngredientContainer(
                largestContainerFor(existing.getCurrentState()), existing);
        IngredientContainer c2 = new IngredientContainer(
                largestContainerFor(incoming.getCurrentState()), incoming);

        kettle.addIngredient(c1);
        kettle.addIngredient(c2);
        kettle.executeOperation();

        IngredientContainer result = kettle.getResult();
        return result.getContent();
    }

    /**********************************************************
     *                        storage
     **********************************************************/

    /**
     * try to find a lookup name to the simple-name key used in the storage map.
     *
     * If the given name matches a special name in the special-name index, the
     * corresponding simple name is returned; otherwise the name itself is used.
     *
     * @param   name
     *          the name to resolve
     *
     * @return  the simple-name key for this name
     *          | result == specialNameIndex.getOrDefault(name, name)
     */
    private String resolveKey(String name) {
        return specialNameIndex.getOrDefault(name, name);
    }

    /**
     * Remove an ingredient from storage and clean up the special-name index.
     *
     * @param   key
     *          the simple-name key
     * @param   ingredient
     *          the ingredient being removed (used to find its special name)
     */
    private void removeFromStorage(String key, AlchemicIngredient ingredient) {
        storage.remove(key);
        if (ingredient instanceof MixtureAlchemicIngredient) {
            String special = ((MixtureAlchemicIngredient) ingredient).getSpecialName();
            if (special != null) {
                specialNameIndex.remove(special);
            }
        }
    }

    /**
     * Build a new ingredient with the same type, state and temperature as the
     * given ingredient but with the specified quantity.
     *
     * @param   source
     *          the ingredient to copy properties from
     * @param   spoons
     *          the new quantity in spoons
     *
     * @return  a new ingredient equal to source except for the quantity
     */
    private AlchemicIngredient buildIngredient(AlchemicIngredient source, long spoons) {
        if (source instanceof MixtureAlchemicIngredient) {
            return new MixtureAlchemicIngredient(
                    (MixtureIngredientType) source.getType(),
                    spoons,
                    source.getCurrentState(),
                    new Temperature(source.getTemperature().getColdness(),
                            source.getTemperature().getHotness()));
        }
        return new AlchemicIngredient(
                source.getType(),
                spoons,
                source.getCurrentState(),
                new Temperature(source.getTemperature().getColdness(),
                        source.getTemperature().getHotness()));
    }

    /**
     * Return the largest valid container unit for the given state.
     *
     * @param   state
     *          the state for which to determine the container unit
     *
     * @return  UNIT.BARREL if state is LIQUID, UNIT.CHEST if state is POWDER
     *          | if (state == STATE.LIQUID) then result == UNIT.BARREL
     *          | else result == UNIT.CHEST
     */
    private UNIT largestContainerFor(STATE state) {
        return state == STATE.LIQUID ? UNIT.BARREL : UNIT.CHEST;
    }
}