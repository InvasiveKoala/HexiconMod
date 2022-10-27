package me.vexinglemons.hexicon.spells;

import me.vexinglemons.hexicon.capabilities.MobData.IMobData;
import me.vexinglemons.hexicon.capabilities.MobData.MobDataProvider;
import me.vexinglemons.hexicon.capabilities.PlayerData.IPlayerData;
import me.vexinglemons.hexicon.capabilities.PlayerData.PlayerDataProvider;
import me.vexinglemons.hexicon.config.config;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AcquireMana {
    @SubscribeEvent
    public void manaGet(LivingExperienceDropEvent event)
    {
        int totalExp = event.getDroppedExperience();
        if (event.getAttackingPlayer() == null)
        {
            return;
        }
        LazyOptional<IMobData> victim = event.getEntityLiving().getCapability(MobDataProvider.capability, null);
        if (victim.isPresent())
        {
            IMobData victimResolved = victim.resolve().get();
            if (victimResolved.getGen())
            {
                return;
            }

        } else {return;}

        
        LazyOptional<IPlayerData> mana = event.getAttackingPlayer().getCapability(PlayerDataProvider.capability, null);
        if (mana.isPresent())
        {
            IPlayerData resolvedMana = mana.resolve().get();
            final int futureMana = (resolvedMana.getMana() + ((totalExp) * 2));
            final int maxMana = config.COMMON.Mana.get();

            if (futureMana < maxMana) {
                event.setDroppedExperience(totalExp / 2);
                resolvedMana.setMana(futureMana);
            }
            else if (resolvedMana.getMana() == maxMana)
            {
                return; // Stops here, so it doesn't constantly send messages when killing mobs at max mana
            } else if (futureMana >= maxMana)
            {
                resolvedMana.setMana(maxMana);
            }

            event.getAttackingPlayer().sendStatusMessage(new StringTextComponent("\u00A7dYou have \u00A7l" + resolvedMana.getMana() + "\u00A7r\u00A7d mana."), false);

        }
    }
}
