package me.vexinglemons.hexicon.capabilities.MobData;

import java.util.UUID;

public class MobDataFactory implements IMobData {

    private boolean gen = false;
    private UUID charmer = UUID.randomUUID();
    public MobDataFactory() {
    }


    @Override
    public boolean getGen() {
        return gen;
    }
    @Override
    public UUID getCharmer() {return charmer;}


    @Override
    public void setGen(boolean amount) {this.gen = amount;}

    @Override
    public void setCharmer(UUID charmer) {this.charmer = charmer;}

}
