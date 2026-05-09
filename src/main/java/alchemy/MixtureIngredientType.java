package alchemy

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

///  temperature is weighted average of ingredients

/// quantity is sum ( rounding loss with Stateconversion)

// had deze eerst verkeerd gezet is een begin om gewogen gemiddelde te berekenen van temperatuur

// helpmethod to calculate temperature and quantity
public List<String> getIngredientsAsList() {
    simpleName = type.getSimpleName();      //// bv Garlic mixed with Imp Gas, Mercurial Acid and Water

    // De regex splitst op: " mixed with " OF ", " OF " and "
    String regex = " mixed with |, | and ";
    String[] splitArray = simpleName.split(regex);

    // We zetten de array hier om naar een List<String>
    List<String> ingredientenLijst = Arrays.asList(splitArray);

    return ingredientenLijst;
}

public long getTemperature() {
    ingredients = getIngredientAsList();
    long temp = 0
    for (String ingredient : ingredients){

    }
}