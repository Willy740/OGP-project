package alchemy;

import static java.lang.Math.min;
import static java.lang.Math.max;

import be.kuleuven.cs.som.annotate.*;

/**
 * @author Joran Naessens
 * @author Maxime Samyn
 */
public class Temperature {

    private long hotness;
    private long coldness;
    private static long MAX = 10000;

    
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
            this.coldness = min(max(coldness, 0), MAX);
            this.hotness  = min(max(hotness,  0), MAX);
        }
    }

    public void heat(long amount) {
        if (amount <= 0) return; // negatief verwarmen = geen effect

        if (this.coldness > 0) {
            // eerst coldness wegwerken
            if (amount <= this.coldness) {
                this.coldness -= amount;
            } else {
                long rest = amount - this.coldness;
                this.coldness = 0;
                this.hotness = min(rest, MAX); // mag niet groter dan max
            }
        } else {
            // hotness verhogen niet groter dan max
            this.hotness = min(this.hotness + amount, MAX);
        }
    }

    public void cool(long amount) {
        if (amount <= 0) return;

        if (this.hotness > 0) {
            // eerst hotness wegwerken
            if (amount <= this.hotness) {
                this.hotness -= amount;
            } else {
                long rest = amount - this.hotness;
                this.hotness = 0;
                this.coldness = min(rest, MAX);
            }
        } else {
            // coldness verhogen
            this.coldness = min(this.coldness + amount, MAX);
        }
    }

    public long getHotness() {
        return this.hotness;
    }

    public long getColdness() {
        return this.coldness;
    }

    public long[] getTemperature() {
        return new long[]{this.coldness, this.hotness};
    }

    public static long getMAX() {
        return MAX;
    }
}