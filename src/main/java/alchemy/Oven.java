package alchemy;

public class Oven extends Device{
    private long targetHotness;

    public Oven(long targetHotness) {
        this.targetHotness = targetHotness;
    }

    public void setTargetHotness(long targetHotness) {
        this.targetHotness = targetHotness;
    }

    public void executeOperation(){
        private long hotness = Temperature.getHotness();
        private long coldness = Temperature.getColdness();
        if (hotness == 0){
            Temperature.heat(this.targetHotness + coldness);
        }
        else{
            Temperature.heat(targetHotness - this.targetHotness);
        }
    }
}