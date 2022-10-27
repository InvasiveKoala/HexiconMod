package me.vexinglemons.hexicon.capabilities.PlayerData;



public class PlayerDataFactory implements IPlayerData{

    private float ticks = 0.0F;
    private boolean isInOrb = false;
    private int intelligence = 0;
    private String spellString = "";
    private int mana = 0;

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
    public void setSpellString(String spell) {this.spellString = spell;}

    @Override
    public boolean getInOrb() {
        return isInOrb;
    }


    @Override
    public int getIntelligence() {return intelligence;}

    @Override
    public String getSpellString() {return this.spellString;}

    @Override
    public void setMana(int mana) {this.mana = mana;}
    @Override
    public void removeMana(int mana) {this.mana -= mana;}

    @Override
    public int getMana() {return this.mana;}
    @Override
    public void setInOrb(boolean amount) {this.isInOrb = amount;}


    @Override
    public void setIntelligence(int amount) {
        this.intelligence = amount;
    }



}
