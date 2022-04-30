package me.ddtincoming.jillingots.item.custom.spells;

import me.ddtincoming.jillingots.JillIngots;
import me.ddtincoming.jillingots.capabilities.IMobData;
import me.ddtincoming.jillingots.capabilities.MobDataProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = JillIngots.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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
