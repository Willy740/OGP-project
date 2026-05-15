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

     /*******************************************
     *              LABORATORY                  *
     *******************************************/

    /**
     * the laboratory the devices belongs to
     */
    private Laboratory laboratory = null;

    public getLaboratory(){return this.laboratory;}

    /**
     * links the given laboratory to the device
     * @param laboratory
     */
    public linkLaboratory(Laboratory laboratory){
        this.laboratory =  laboratory;
    }

    /*******************************************
     *               CONTENTS                  *
     *******************************************/

    /**
     * The contents stored in the Device
     * Depending on which device there may be rules around maximum items
     */
    private List<IngredientContainer> contents;

    public List<IngredientContainer> getContents() {return this.contents;}

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