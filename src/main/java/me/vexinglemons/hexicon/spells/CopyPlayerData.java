package me.vexinglemons.hexicon.spells;

import me.vexinglemons.hexicon.capabilities.PlayerData.IPlayerData;
import me.vexinglemons.hexicon.capabilities.PlayerData.PlayerDataProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CopyPlayerData {
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
        PlayerEntity player = event.getPlayer();
        LazyOptional<IPlayerData> data = player.getCapability(PlayerDataProvider.capability, null);

        data.ifPresent(newPlayer ->{
            LazyOptional<IPlayerData> oldData = event.getOriginal().getCapability(PlayerDataProvider.capability, null);
            oldData.ifPresent(oldPlayer -> {
                newPlayer.setMana(oldPlayer.getMana());
            });
        });
    }
}
