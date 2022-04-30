package me.ddtincoming.hexicon.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class BookDataStorage implements Capability.IStorage<IBookData> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IBookData> capability, IBookData instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putBoolean("Spellbook", instance.getIsSpellbook());
        return tag;
    }

    @Override
    public void readNBT(Capability<IBookData> capability, IBookData instance, Direction side, INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        instance.setIsSpellbook(tag.getBoolean("Spellbook"));
    }
}