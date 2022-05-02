package me.vexinglemons.hexicon.capabilities.MobData;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class MobDataStorage implements Capability.IStorage<IMobData> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IMobData> capability, IMobData instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putBoolean("Gen", instance.getGen());
        tag.putUniqueId("Charmer", instance.getCharmer());
        return tag;
    }

    @Override
    public void readNBT(Capability<IMobData> capability, IMobData instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setGen(tag.getBoolean("Gen"));
        instance.setCharmer(tag.getUniqueId("Charmer"));
    }
}