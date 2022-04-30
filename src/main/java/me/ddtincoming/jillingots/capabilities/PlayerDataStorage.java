package me.ddtincoming.jillingots.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class PlayerDataStorage implements Capability.IStorage<IPlayerData> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IPlayerData> capability, IPlayerData instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("Health", instance.getTicks());
        tag.putBoolean("Strength", instance.getInOrb());
        tag.putInt("Intelligence", instance.getIntelligence());
        return tag;
    }

    @Override
    public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setTicks(tag.getFloat("Health"));
        instance.setInOrb(tag.getBoolean("Strength"));
        instance.setIntelligence(tag.getInt("Intelligence"));
    }
}