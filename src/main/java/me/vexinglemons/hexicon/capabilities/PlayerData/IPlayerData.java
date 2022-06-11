package me.vexinglemons.hexicon.capabilities.PlayerData;


public interface IPlayerData {

    float getTicks();
    void setTicks(float amount);
    void setSpellString(String spell);
    void setMana(int mana);

    boolean getInOrb();
    int getIntelligence();
    String getSpellString();


    int getMana();
    void setInOrb(boolean amount);
    void setIntelligence(int amount);


}