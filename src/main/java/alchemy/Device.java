package alchemy;

import java.util.List;

/**
 * class representing a device used to process alchemic ingredients
 *
 * @invar a device can never return more than one IngredientContainer to the user,
 * any extra is discarded
 *
 */
public class Device {

    private Laboratory laboratory;

    private List<IngredientContainer> contents;

    public List<IngredientContainer> getContents() {return contents;}

    /**
     * method that adds ingredients to the device
     *
     * @param ingredient
     *
     * @post the IngredientContainer is emptied and discarded
     */
    public void addIngredient(IngredientContainer ingredient){
        contents.add(ingredient);
        ingredient.destroy();
    }
}