package alchemy;

import be.kuleuven.cs.som.annotate.*;

/**
 * a class representing the name of a mixture ingredient type
 *
 * @invar   the simple name of this mixture ingredient name must be a valid mixture name
 *          | isValidSimpleName(getSimpleName())
 * @invar   if the special name is effective, it must not be empty
 *          | getSpecialName() == null || !getSpecialName().isEmpty()
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class MixtureIngredientName extends IngredientName {
    /**
     * variable referencing the optional special name of this mixture ingredient name
     */
    private String specialName;

    /**********************************************************
     * constructor
     **********************************************************/

    /**
     * initialize a new mixture ingredient name with the given simple name
     * and optional special name
     *
     * @param   simpleName
     *          the simple name of this new mixture ingredient name
     * @param   specialName
     *          the optional special name of this new mixture ingredient name,
     *          or null if no special name is given
     *
     * @post    the simple name of this mixture ingredient name is set to the given name
     *          | new.getSimpleName().equals(simpleName)
     * @post    the special name of this mixture ingredient name is set to the given special name
     *          | new.getSpecialName() == specialName
     *
     * @throws  IllegalArgumentException
     *          the given simple name is not valid
     *          | !isValidSimpleName(simpleName)
     * @throws  IllegalArgumentException
     *          the given special name is effective but empty
     *          | specialName != null && specialName.isEmpty()
     */
    public MixtureIngredientName(String simpleName, String specialName) {
        super(simpleName);
        if (specialName != null && specialName.isEmpty()) {
            throw new IllegalArgumentException("specialName cannot be empty");
        }
        this.specialName = specialName;
    }

    /**
     * check whether the given string is a valid simple name for a mixture ingredient
     *
     * @param   simpleName
     *          the string to check
     *
     * @return  false if the given string is not effective or is empty
     *          | if (simpleName == null || simpleName.isEmpty())
     *          | then result == false
     * @return  false if any component part of the mixture name is not a valid
     *          simple name as defined in the superclass
     *          | if (exists part in simpleName.split(" mixed with | and |, ") :
     *          |       !super.isValidSimpleName(part))
     *          | then result == false
     */
    @Override
    public boolean isValidSimpleName(String simpleName){
        if (simpleName == null || simpleName.isEmpty()) {
            return false;
        }
        String[] parts = simpleName.split(" mixed with | and |, ");
        for (String part : parts) {
            if(!super.isValidSimpleName(part)){
                return false;
            }
        }
        return true;
    }

    /**
     * return the special name of this mixture ingredient name, or null if none is set
     */
    @Basic @Raw
    public String getSpecialName() {
        return this.specialName;
    }

    /**
     * set the special name of this mixture ingredient name to the given name
     *
     * @param   specialName
     *          the new special name, or null to clear it
     *
     * @post    The special name of this mixture ingredient name is set to the given name
     *          | new.getSpecialName() == specialName
     *
     * @throws  IllegalArgumentException
     *          the given special name is effective but empty
     *          | specialName != null && specialName.isEmpty()
     */
    public void setSpecialName(String specialName) {
        if (specialName != null && specialName.isEmpty()) {
            throw new IllegalArgumentException("specialName cannot be empty");
        }
        this.specialName = specialName;
    }

    /**
     * return the full name of this mixture ingredient name, given the current
     * and default temperature
     *
     * @param   temperature
     *          the current temperature of the ingredient
     * @param   defaultTemperature
     *          the default temperature of the ingredient type
     *
     * @return  if a special name is set, the special name followed by the full name
     *          of the superclass enclosed in parentheses is returned
     *          | if (getSpecialName() != null)
     *          | then result.equals(getSpecialName() + " ("
     *          |          + super.getFullName(temperature, defaultTemperature) + ")")
     * @return  otherwise, the full name of the superclass is returned
     *          | else result.equals(super.getFullName(temperature, defaultTemperature))
     *
     * @throws  IllegalArgumentException
     *          one or both of the given temperatures are not effective
     *          | temperature == null || defaultTemperature == null
     */
    @Override
    public String getFullName(Temperature temperature, Temperature defaultTemperature) {
        if (this.specialName != null) {
            return this.specialName + " (" + super.getFullName(temperature, defaultTemperature) + ")";
        }
        else {
            return super.getFullName(temperature, defaultTemperature);
        }
    }
}