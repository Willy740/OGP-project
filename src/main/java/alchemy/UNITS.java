package alchemy;

import be.kuleuven.cs.som.annotate.*;

public enum UNITS {
        DROP(1),
        SPOON(8),
        VIAL(40),
        BOTTLE(120),
        JUG(840),
        BARREL(10080),
        STOREROOM(50400);

        private final int drops;

        private UNITS(int drops){
                this.drops = drops;
        }

        public int toDrops(){
                return this.drops;
        }

        public float toUnit(UNITS unit){
                return ((float) unit.toDrops() /this.drops);
        }
}
