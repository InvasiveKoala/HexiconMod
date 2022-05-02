package me.vexinglemons.hexicon.capabilities.MobData;

import java.util.UUID;

public interface IMobData {


    boolean getGen();
    UUID getCharmer();

    void setGen(boolean amount);
    void setCharmer(UUID uuid);

}