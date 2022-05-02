package me.vexinglemons.hexicon.capabilities.PlayerData;


public interface IPlayerData {

    float getTicks();
    void setTicks(float amount);
    void setSpellString(String spell);


    boolean getInOrb();
    int getIntelligence();
    String getSpellString();

    void setInOrb(boolean amount);
    void setIntelligence(int amount);


}