package alchemy;

import static java.lang.Math.min;
import static java.lang.Math.max;

import be.kuleuven.cs.som.annotate.*;

/**
 * a class of temperatures, consisting of a coldness and a hotness value
 *
 * @invar   the coldness of each temperature must be valid
 *          | getColdness() >= 0 && getColdness() <= getMAX()
 * @invar   the hotness of each temperature must be valid
 *          | getHotness() >= 0 && getHotness() <= getMAX()
 * @invar   a temperature cannot have both a strictly positive coldness and hotness
 *          | !(getColdness() > 0 && getHotness() > 0)
 *
 * @author  Joran Naessens
 * @author  Maxime Samyn
 */
public class Temperature {

    /**
     * variable registering the hotness of this temperature
     */
    private long hotness;

    /**
     * variable registering the coldness of this temperature
     */
    private long coldness;


    /**********************************************************
     *                  Constructor
     **********************************************************/

    /**
     * initialize a new temperature with the given coldness and hotness
     *
     * @param   coldness
     *          the coldness of this new temperature
     * @param   hotness
     *          the hotness of this new temperature
     *
     * @post    if both coldness and hotness are strictly positive, both are set to 0
     *          | if (coldness > 0 && hotness > 0)
     *          | then new.getColdness() == 0 && new.getHotness() == 0
     * @post    if coldness is negative, coldness is set to 0
     *          | if (coldness < 0)
     *          | then new.getColdness() == 0
     * @post    if hotness is negative, hotness is set to 0
     *          | if (hotness < 0)
     *          | then new.getHotness() == 0
     * @post    otherwise, both values are clamped to [0, MAX]
     *          | if (coldness >= 0 && hotness >= 0 && !(coldness > 0 && hotness > 0))
     *          | then new.getColdness() == min(max(coldness, 0), MAX)
     *          |   && new.getHotness()  == min(max(hotness,  0), MAX)
     */
    public Temperature(long coldness, long hotness) {
        // beide > 0 is niet toegelaten: zet beide op 0
        if (coldness > 0 && hotness > 0) {
            this.coldness = 0;
            this.hotness = 0;
        } 
        else if (coldness < 0 ) {
            this.coldness = 0;
        }
        else if (hotness < 0 ) {
            this.hotness = 0;
        }
        else {
            this.coldness = min(max(coldness, 0), this.getMax());
            this.hotness  = min(max(hotness,  0), this.getMax());
        }
    }

    /**
     * heat this temperature by the given amount
     *
     * @param   amount
     *          the amount by which to heat this temperature
     *
     * @post    if the current coldness is positive and amount does not exceed it,
     *          the coldness is reduced by the given amount and hotness remains 0
     *          | if (getColdness() > 0 && amount <= getColdness())
     *          | then new.getColdness() == getColdness() - amount
     *          |   && new.getHotness()  == 0
     * @post    if the current coldness is positive and amount exceeds it,
     *          coldness becomes 0 and hotness is set to the remainder, clamped to MAX
     *          | if (getColdness() > 0 && amount > getColdness())
     *          | then new.getColdness() == 0
     *          |   && new.getHotness()  == min(amount - getColdness(), MAX)
     * @post    if coldness is 0, hotness is increased by the given amount, clamped to MAX
     *          | if (getColdness() == 0)
     *          | then new.getHotness() == min(getHotness() + amount, MAX)
     *
     * @throws  IllegalArgumentException
     *          the given amount is not strictly positive
     *          | amount <= 0
     */
    public void heat(long amount) {
        if (amount <= 0) {
            throw  new IllegalArgumentException("Amount must be positive");
        }

        if (this.coldness > 0) {
            // eerst coldness wegwerken
            if (amount <= this.coldness) {
                this.coldness -= amount;
            } else {
                long rest = amount - this.coldness;
                this.coldness = 0;
                this.hotness = min(rest, getMax()); // mag niet groter dan max
            }
        } else {
            // hotness verhogen niet groter dan max
            this.hotness = min(this.hotness + amount, getMax());
        }
    }

    /**
     * cool this temperature by the given amount
     *
     * @param   amount
     *          the amount by which to cool this temperature
     *
     * @post    if the current hotness is positive and amount does not exceed it,
     *          the hotness is reduced by the given amount and coldness remains 0
     *          | if (getHotness() > 0 && amount <= getHotness())
     *          | then new.getHotness()  == getHotness() - amount
     *          |   && new.getColdness() == 0
     * @post    if the current hotness is positive and amount exceeds it,
     *          hotness becomes 0 and coldness is set to the remainder, clamped to MAX
     *          | if (getHotness() > 0 && amount > getHotness())
     *          | then new.getHotness()  == 0
     *          |   && new.getColdness() == min(amount - getHotness(), MAX)
     * @post    if hotness is 0, coldness is increased by the given amount, clamped to MAX
     *          | if (getHotness() == 0)
     *          | then new.getColdness() == min(getColdness() + amount, MAX)
     *
     */
    public void cool(long amount) {
        if (amount <= 0) {
            return;
        }
        if (this.hotness > 0) {
            if (amount <= this.hotness) {
                this.hotness -= amount;
            } else {
                long rest = amount - this.hotness;
                this.hotness = 0;
                this.coldness = min(rest, getMax());
            }
        } else {
            this.coldness = min(this.coldness + amount, getMax());
        }
    }

    /**
     * return the hotness of this temperature
     */
    @Basic @Raw
    public long getHotness() {
        return this.hotness;
    }

    /**
     * return the coldness of this temperature
     */
    @Basic @Raw
    public long getColdness() {
        return this.coldness;
    }

    /**
     * return this temperature as an array [coldness, hotness]
     *
     * @return  an array where the first element is the coldness
     *          and the second element is the hotness
     *          | result[0] == getColdness() && result[1] == getHotness()
     */
    public long[] getTemperature() {
        return new long[]{this.coldness, this.hotness};
    }

    /**
     * return the maximum allowed value for hotness and coldness
     */
    @Basic @Immutable
    public static long getMax() {
        return 10000;
    }

}