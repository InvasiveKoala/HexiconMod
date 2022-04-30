package me.ddtincoming.jillingots.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {

    public static final ItemGroup JILL_GROUP = new ItemGroup("jillIngotsTab")
    {
        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(ModItems.SUSSY.get());
        }
    };

}
