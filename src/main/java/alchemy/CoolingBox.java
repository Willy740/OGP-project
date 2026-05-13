package alchemy;

public class CoolingBox extends Device{

    private long targetColdness;

    public CoolingBox(long targetColdness) {
        this.targetColdness = targetColdness;
    }

    public void setColdness(long target){
        this.targetColdness = target;
    }

    public void executeOperation(){

        long hotness = Temperature.getHotness();
        long coldness = Temperature.getColdness();

        if (coldness == 0){
            Temperature.cool(hotness + this.targetColdness);
        }
        else{
            Temperature.cool(this.targetColdness - coldness);
        }
    }
}