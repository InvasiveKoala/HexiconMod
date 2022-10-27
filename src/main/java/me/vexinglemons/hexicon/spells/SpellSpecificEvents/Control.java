package me.vexinglemons.hexicon.spells.SpellSpecificEvents;

import me.vexinglemons.hexicon.capabilities.MobData.IMobData;
import me.vexinglemons.hexicon.capabilities.MobData.MobDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class Control
{
    @SubscribeEvent
    public void control(LivingSetAttackTargetEvent event)
    {
        LazyOptional<IMobData> entitything = event.getEntity().getCapability(MobDataProvider.capability,null);
        entitything.ifPresent(entity ->
        {
            if(entity.getCharmer() != null)
            {
                PlayerEntity charmer = event.getEntity().getEntityWorld().getPlayerByUuid(entity.getCharmer());
                if (event.getTarget() instanceof PlayerEntity)
                {
                    PlayerEntity playerTarget = (PlayerEntity) event.getTarget();

                    if (playerTarget.equals(charmer))
                    {
                        MobEntity mobEntity = (MobEntity) event.getEntityLiving();
                        mobEntity.setAttackTarget(charmer.getLastAttackedEntity());
                    }
                }
            }
        });


    }

    @SubscribeEvent
    public void attack(LivingHurtEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
            {
                PlayerEntity caster = (PlayerEntity) event.getSource().getTrueSource();
                List<Entity> entities = caster.getEntityWorld().getEntitiesWithinAABBExcludingEntity(caster, caster.getBoundingBox().expand(15.0f, 15.0f, 15.0f).expand(-15.0f, -15.0f, -15.0f));
                for(Entity currentEntity : entities)
                {
                    LazyOptional<IMobData> entitything = currentEntity.getCapability(MobDataProvider.capability,null);
                    entitything.ifPresent(entity -> {
                        MobEntity mob = (MobEntity) currentEntity;
                        if (entity.getCharmer().equals(caster.getUniqueID()))
                        {
                            mob.setAttackTarget((MobEntity) event.getEntity());
                        }
                    });
                }
            }
    }
}
