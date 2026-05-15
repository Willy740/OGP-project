package alchemy;

import be.kuleuven.cs.som.annotate.*;
import java.util.Set;

/**
 * A class representing the type of a mixture of alchemic ingredient types.
 *
 * A mixture ingredient type is created by combining two or more ingredient types.
 * Its default state and default temperature are based of the types
 * according to the mixing rules.
 *
 * @invar   the name of this mixture ingredient type is a valid mixture ingredient name
 *          | getName() instanceof MixtureIngredientName
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class MixtureIngredientType extends IngredientType {

    /**********************************************************
     *                      constructor
     **********************************************************/

    /**
     * Initialize a new mixture ingredient type from the given set of ingredient types.
     *
     * The simple name is derived by combining the simple names of the constituent types.
     * The default state and default temperature are calculated from the constituent types.
     *
     * @param   simpleName
     *          the pre-built mixture simple name (e.g. "Garlic mixed with Water")
     * @param   types
     *          the set of ingredient types that make up this mixture;
     *          must contain at least two types
     *
     * @pre     types is not null and contains at least two elements
     *          | types != null && types.size() >= 2
     *
     * @effect  initializes this mixture ingredient type as an ingredient type with the
     *          given simple name, the calculated default state and the calculated
     *          default temperature
     *          | super(new MixtureIngredientName(simpleName, null),
     *          |       calculateDefaultState(types),
     *          |       calculateDefaultTemperature(types))
     */
    public MixtureIngredientType(String simpleName, Set<IngredientType> types) {
        super(
                new MixtureIngredientName(simpleName, null),
                calculateDefaultState(types),
                calculateDefaultTemperature(types)
        );
    }

    /**
     * Return whether this ingredient type is a mixture.
     *
     * @return  always true for mixture ingredient types
     *          | result == true
     */
    @Override
    @Basic @Immutable
    public boolean isMixture() {
        return true;
    }

    /**********************************************************
     *                     special name
     **********************************************************/

    /**
     * return the special name of this mixture ingredient type, or null if none is set.
     *
     * @return  the special name of the mixture ingredient name of this type
     *          | result == ((MixtureIngredientName) getName()).getSpecialName()
     */
    @Raw
    public String getSpecialName() {
        return ((MixtureIngredientName) getName()).getSpecialName();
    }

    /**
     * Set the special name of this mixture ingredient type.
     *
     * @param   specialName
     *          the new special name, or null to clear it
     *
     * @post    the special name of this mixture ingredient type is set to the given name
     *          | ((MixtureIngredientName) getName()).getSpecialName() == specialName
     *
     * @throws  IllegalArgumentException
     *          the given special name is effective but empty
     *          | specialName != null && specialName.isEmpty()
     */
    public void setSpecialName(String specialName) {
        ((MixtureIngredientName) getName()).setSpecialName(specialName);
    }

    /**
     * Return the full name of this mixture ingredient type given the current
     * and default temperature of the ingredient.
     *
     * @param   temperature
     *          the current temperature of the ingredient
     * @param   defaultTemperature
     *          the default temperature of this type
     *
     * @return  the full name as produced by the mixture ingredient name
     *          | result.equals(((MixtureIngredientName) getName())
     *          |       .getFullName(temperature, defaultTemperature))
     *
     * @throws  IllegalArgumentException
     *          one or both temperatures are null
     *          | temperature == null || defaultTemperature == null
     */
    public String getFullName(Temperature temperature, Temperature defaultTemperature) {
        return ((MixtureIngredientName) getName()).getFullName(temperature, defaultTemperature);
    }

    /**********************************************************
     *                        state
     **********************************************************/

    /**
     * Calculate the default state for a mixture of the given ingredient types.
     *
     * The default state is the default state of the ingredient type whose default
     * temperature is closest to [0, 20] (i.e. hotness 20). In case of a tie,
     * LIQUID is preferred over POWDER.
     *
     * @param   types
     *          the set of ingredient types to derive the default state from
     *
     * @pre     types is not null and not empty
     *          | types != null && !types.isEmpty()
     *
     * @return  the default state of the ingredient type with a default temperature
     *          closest to [0, 20]; LIQUID wins ties
     *          | for each type in types:
     *          |   result == the defaultState of the type with the smallest
     *          |             distanceToInterval(), with LIQUID preferred on ties
     */
    private static STATE calculateDefaultState(Set<IngredientType> types) {
        IngredientType best = null;
        for (IngredientType type : types) {
            if (best == null) {
                best = type;
            } else if (type.distanceToInterval() < best.distanceToInterval()) {
                best = type;
            } else if (type.distanceToInterval() == best.distanceToInterval()
                    && type.getDefaultState() == STATE.LIQUID) {
                best = type;
            }
        }
        return best.getDefaultState();
    }

    /**********************************************************
     *                     temperature
     **********************************************************/

    /**
     * Calculate the default temperature for a mixture of the given ingredient types.
     *
     * The default temperature is the default temperature of the ingredient type whose
     * default temperature is closest to [0, 20]. In case of a tie, the warmer
     * temperature (i.e. greater hotness) is preferred.
     *
     * @param   types
     *          the set of ingredient types to derive the default temperature from
     *
     * @pre     types is not null and not empty
     *          | types != null && !types.isEmpty()
     *
     * @return  the default temperature of the ingredient type with a default temperature
     *          closest to [0, 20]; the warmer temperature wins ties
     *          | for each type in types:
     *          |   result == the defaultTemperature of the type with the smallest
     *          |             distanceToInterval(), with the warmer temperature
     *          |             preferred on ties
     */
    private static Temperature calculateDefaultTemperature(Set<IngredientType> types) {
        IngredientType best = null;
        for (IngredientType type : types) {
            if (best == null) {
                best = type;
            } else if (type.distanceToInterval() < best.distanceToInterval()) {
                best = type;
            } else if (type.distanceToInterval() == best.distanceToInterval()
                    && type.getDefaultTemperature().getHotness()
                    > best.getDefaultTemperature().getHotness()) {
                best = type;
            }
        }
        return best.getDefaultTemperature();
    }
}