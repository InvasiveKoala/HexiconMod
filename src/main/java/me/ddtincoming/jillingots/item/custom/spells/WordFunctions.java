package me.ddtincoming.jillingots.item.custom.spells;

import me.ddtincoming.jillingots.capabilities.IMobData;
import me.ddtincoming.jillingots.capabilities.MobDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

import static me.ddtincoming.jillingots.item.custom.spells.GlobalWordFunctions.checkDirectionValidity;

public class WordFunctions {

    public static void gen(EntityType entityToSpawn, PlayerEntity caster, int wordNumber, int[] advmodifiers, int lineNumber)
    {
        if (entityToSpawn == null)
        {
            caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                    ("Word " + (wordNumber + 1) + " of line " + lineNumber + " is not a mob."), true);
            return;
        }
        Entity entity = entityToSpawn.spawn((ServerWorld) caster.getEntityWorld(), caster.getHeldItemMainhand(), caster, caster.getPosition(), SpawnReason.SPAWN_EGG, true, false);
        LivingEntity livingEntity = null;
        boolean isLiving = entity instanceof LivingEntity;
        if (isLiving)
        {
            livingEntity = (LivingEntity) entity;
            attachGen(entity);
        }
        else if (entityToSpawn == EntityType.ENDER_PEARL)
        {
            EnderPearlEntity pearl = (EnderPearlEntity) entity;
            pearl.setShooter(caster);
        }
        int i = -1;
        for (int level : advmodifiers) {
            i++;
            if (level == 1) {
                continue;
            }
            Effect effect = Mobnames.returnPotionType(i);
            if (isLiving) {
                livingEntity.addPotionEffect(new EffectInstance(effect, 999999999, level - 1));
            }
        }
    }

    public static void attachGen(Entity entity)
    {
        LazyOptional<IMobData> entitything = entity.getCapability(MobDataProvider.capability,null);
        entitything.ifPresent((theEntity) -> {
            theEntity.setGen(true);
        });
    }

    public static void rise(Entity target, int[] modifiers)
    {
        target.velocityChanged = true;
        double modifier = (double) modifiers[0]/2;
        double ymotion = target.getMotion().y + (0.1 + (modifier));
        target.setVelocity(target.getMotion().x,ymotion,target.getMotion().z);
    }

    public static void fall(Entity target, int[] modifiers)
    {
        target.velocityChanged = true;
        double ymotion = target.getMotion().y - (2.5 * modifiers[0]);
        target.setVelocity(target.getMotion().x,ymotion,target.getMotion().z);
    }

    public static void transform(Entity target, int[] modifiers) {
        int i = -1;
        LivingEntity livingTarget = (LivingEntity) target;
        for (int level : modifiers)
        {
            i++;
            if (level == 1)
            {
                continue;
            }
            Effect effect = Mobnames.returnPotionType(i);
            livingTarget.addPotionEffect(new EffectInstance(effect, 1200 * level, 0));
        }
    }

    public static void cleanse(Entity target, int[] modifiers)
    {
        int i = -1;
        LivingEntity livingTarget = (LivingEntity) target;
        for (int level : modifiers)
        {
            i++;
            if (level == 1)
            {
                continue;
            }
            Effect effect = Mobnames.returnPotionType(i);
            livingTarget.removePotionEffect(effect);
        }
    }

    public static void push(PlayerEntity caster, Entity target, int[] modifiers)
    {
        int validity = checkDirectionValidity(modifiers);
        if (validity == 1)
        {
            caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                    ("You need a direction adverb to use this verb."), true);
            return;
        }
        else if (validity == 2)
        {
            caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                    ("You can't use multiple direction adverbs!"), true);
            return;
        }
        double xmotion = 0;
        double zmotion = 0;
        if (modifiers[1] > 1)
        {
            xmotion = caster.getLookVec().x;
            zmotion = caster.getLookVec().z;
        }
        else if (modifiers[2] > 1)
        {
            xmotion = target.getLookVec().x;
            zmotion = target.getLookVec().z;
        }
        else if (modifiers[3] > 1)
        {
            xmotion = caster.getLookVec().x * -1;
            zmotion = caster.getLookVec().z * -1;
        }
        else if (modifiers[4] > 1)
        {
            xmotion = target.getLookVec().x * -1;
            zmotion = target.getLookVec().z * -1;
        }

        target.velocityChanged = true;
        target.setVelocity(xmotion * modifiers[0],target.getMotion().y,zmotion * modifiers[0]);
    }

    public static void lightning(Entity target)
    {
        EntityType.LIGHTNING_BOLT.spawn((ServerWorld) target.getEntityWorld(), null, null, target.getPosition(), SpawnReason.SPAWN_EGG, true, false);
    }

    public static void ignite(Entity target, int[] modifiers)
    {
        target.setFire(modifiers[0] * 4);
    }

}
