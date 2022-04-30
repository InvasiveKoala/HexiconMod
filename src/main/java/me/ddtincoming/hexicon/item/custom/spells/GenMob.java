package me.ddtincoming.hexicon.item.custom.spells;

import me.ddtincoming.hexicon.Hexicon;
import me.ddtincoming.hexicon.capabilities.IMobData;
import me.ddtincoming.hexicon.capabilities.MobDataProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Hexicon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GenMob {
    @SubscribeEvent
    public static void genDeath(LivingDropsEvent event)
    {
        LazyOptional<IMobData> entitything = event.getEntity().getCapability(MobDataProvider.capability,null);
        entitything.ifPresent((entity) -> {
            if (entity.getGen())
            {
                event.setCanceled(true);
            }

        });
    }
}
