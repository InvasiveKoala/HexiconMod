package me.ddtincoming.hexicon.item.custom;
import me.ddtincoming.hexicon.capabilities.IPlayerData;
import me.ddtincoming.hexicon.capabilities.PlayerDataProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WaterstoneTick {
    @SubscribeEvent
    public void waterstoneTick(TickEvent.PlayerTickEvent event)
    {
        if (event.player.getEntityWorld().isRemote()){
            LazyOptional<IPlayerData> charge = event.player.getCapability(PlayerDataProvider.capability,null);
            charge.ifPresent(player -> {
                PlayerEntity playerIn = event.player;
                if (player.getInOrb() == true) {
                    if (player.getTicks() >= 200) {
                        player.setTicks(0);
                        player.setInOrb(false);
                    } else {
                        player.setTicks(player.getTicks() + 1);
                        Vector3d lookVec = playerIn.getLookVec();
                        lookVec.add(0.1,0.0001,0.1);
                        playerIn.setVelocity(lookVec.x,lookVec.y,lookVec.z);
                    }
                }
            });
        }
    }
}
