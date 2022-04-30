package me.ddtincoming.jillingots.capabilities;



public class PlayerDataFactory implements IPlayerData{

    private float ticks = 0.0F;
    private boolean isInOrb = false;
    private int intelligence = 0;

    public PlayerDataFactory() {
    }


    @Override
    public float getTicks() {
        return ticks;
    }

    @Override
    public void setTicks(float amount) {
        this.ticks = amount;
    }

    @Override
    public boolean getInOrb() {
        return isInOrb;
    }


    @Override
    public int getIntelligence() {
        return intelligence;
    }


    @Override
    public void setInOrb(boolean amount) {this.isInOrb = amount;}


    @Override
    public void setIntelligence(int amount) {
        this.intelligence = amount;
    }



}
