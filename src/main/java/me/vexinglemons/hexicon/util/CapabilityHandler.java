package me.vexinglemons.hexicon.util;

import me.vexinglemons.hexicon.Hexicon;
import me.vexinglemons.hexicon.capabilities.BookData.BookDataProvider;
import me.vexinglemons.hexicon.capabilities.PlayerData.PlayerDataProvider;
import me.vexinglemons.hexicon.capabilities.MobData.MobDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Hexicon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityHandler {
    public static final ResourceLocation PLAYER_DATA = new ResourceLocation(Hexicon.MOD_ID, "playerdata");
    public static final ResourceLocation MOB_DATA = new ResourceLocation(Hexicon.MOD_ID, "mobdata");
    public static final ResourceLocation BOOK_DATA = new ResourceLocation(Hexicon.MOD_ID, "bookdata");
    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if((event.getObject() instanceof PlayerEntity))
        {
            event.addCapability(PLAYER_DATA, new PlayerDataProvider());
        }
        else if ((event.getObject() instanceof LivingEntity))
        {
            event.addCapability(MOB_DATA, new MobDataProvider());
        }
    }
    @SubscribeEvent
    public static void attachBookCapability(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() == Items.WRITTEN_BOOK)
        {
            event.addCapability(BOOK_DATA, new BookDataProvider());
        }
    }
}
