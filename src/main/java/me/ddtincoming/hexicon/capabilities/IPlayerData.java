package me.ddtincoming.hexicon.capabilities;


public interface IPlayerData {

    float getTicks();
    void setTicks(float amount);

    boolean getInOrb();
    int getIntelligence();


    void setInOrb(boolean amount);
    void setIntelligence(int amount);


}