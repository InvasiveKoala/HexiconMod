package me.vexinglemons.hexicon.spells;

import me.vexinglemons.hexicon.capabilities.MobData.IMobData;
import me.vexinglemons.hexicon.capabilities.MobData.MobDataProvider;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;

import static me.vexinglemons.hexicon.spells.GlobalWordFunctions.checkDirectionValidity;

public class WordFunctions {

    public static void gen(EntityType entityToSpawn, PlayerEntity caster, int wordNumber, ArrayList<Integer> advmodifiers, int lineNumber)
    {
        if (entityToSpawn == null)
        {
            caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                    ("Word " + (wordNumber + 1) + " of line " + lineNumber + " is not a mob."), true);
            return;
        }
        Entity entity = entityToSpawn.spawn((ServerWorld) caster.getEntityWorld(), caster.getHeldItemMainhand(), caster, caster.getPosition(), SpawnReason.SPAWNER, true, false);
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
            Effect effect = MobPotionNames.returnPotionType(i);
            if (isLiving) {
                livingEntity.addPotionEffect(new EffectInstance(effect, 999999999, level - 1));
            }
        }
    }

    public static void attachGen(Entity entity)
    {
        LazyOptional<IMobData> entitything = entity.getCapability(MobDataProvider.capability,null);
        entitything.ifPresent((theEntity) -> theEntity.setGen(true));
    }

    public static void rise(Entity target, ArrayList<Integer> advmodifiers)
    {
        target.velocityChanged = true;
        double modifier = (double) advmodifiers.get(0)/2;
        double ymotion = target.getMotion().y + (0.1 + (modifier));
        target.setVelocity(target.getMotion().x,ymotion,target.getMotion().z);
    }

    public static void fall(Entity target, ArrayList<Integer> advmodifiers)
    {
        target.velocityChanged = true;
        double ymotion = target.getMotion().y - (2.5 * advmodifiers.get(0));
        target.setVelocity(target.getMotion().x,ymotion,target.getMotion().z);
    }

    public static void transform(Entity target, ArrayList<Integer> advmodifiers) {
        int i = -1;
        LivingEntity livingtarget = (LivingEntity) target;
        for (int level : advmodifiers)
        {
            i++;
            if (level == 1)
            {
                continue;
            }
            Effect effect = MobPotionNames.returnPotionType(i);
            livingtarget.addPotionEffect(new EffectInstance(effect, 1200 * (level - 1), 0));
        }
    }

    public static void cleanse(Entity target, ArrayList<Integer> advmodifiers)
    {
        int i = -1;
        LivingEntity livingtarget = (LivingEntity) target;
        for (int level : advmodifiers)
        {
            i++;
            if (level == 1)
            {
                continue;
            }
            Effect effect = MobPotionNames.returnPotionType(i);
            livingtarget.removePotionEffect(effect);
        }
    }

    public static void push(PlayerEntity caster, Entity target, ArrayList<Integer> advmodifiers)
    {
        int validity = checkDirectionValidity(advmodifiers);
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
        if (advmodifiers.get(2) > 1) // Deincepsme (player's forward)
        {
            xmotion = caster.getLookVec().x;
            zmotion = caster.getLookVec().z;
        }
        else if (advmodifiers.get(4) > 1) // Deinceps (Object's forward)
        {
            xmotion = target.getLookVec().x;
            zmotion = target.getLookVec().z;
        }
        else if (advmodifiers.get(1) > 1) // Retrome (Player's backward)
        {
            xmotion = caster.getLookVec().x * -1;
            zmotion = caster.getLookVec().z * -1;
        }
        else if (advmodifiers.get(3) > 1) //Retro (Object's backward)
        {
            xmotion = target.getLookVec().x * -1;
            zmotion = target.getLookVec().z * -1;
        }

        target.velocityChanged = true;
        target.setVelocity(xmotion * advmodifiers.get(0),target.getMotion().y,zmotion * advmodifiers.get(0));
    }

    public static void lightning(Entity target)
    {
        EntityType.LIGHTNING_BOLT.spawn((ServerWorld) target.getEntityWorld(), null, null, target.getPosition(), SpawnReason.SPAWN_EGG, true, false);
    }

    public static void ignite(Entity target, ArrayList<Integer> modifiers)
    {
        target.setFire(modifiers.get(0) * 4);
    }

    public static void pull(Entity target, ArrayList<Integer> modifiers, PlayerEntity caster)
    {
        double vecX;
        double vecY;
        double vecZ;
        Vector3d vector = caster.getPositionVec().subtract(target.getPositionVec()).normalize();
        if (modifiers.get(1) > 1)
        {
            vecX = vector.getX() * -1;
            vecY = vector.getY() * -1;
            vecZ = vector.getZ() * -1;
        }
        else{
            vecX = vector.getX();
            vecY = vector.getY();
            vecZ = vector.getZ();
        }
        target.setVelocity(vecX * modifiers.get(0), vecY * modifiers.get(0), vecZ * modifiers.get(0));
    }

    public static void control(Entity target, PlayerEntity caster) //The actual code for charmed things following your command is in a separate class
    {
        if (!(target instanceof MobEntity))
        {
            caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                    ("The entity must be a mob to charm it."), true);
            return;
        }
        else if(((MobEntity) target).getMaxHealth() > 100)
        {
            caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                    ("This mob is too powerful to control!"), true);
            return;
        }
        LazyOptional<IMobData> entitything = target.getCapability(MobDataProvider.capability,null);
        entitything.ifPresent(entity ->
        {
            if (entity.getCharmer() != null && entity.getCharmer().equals(caster.getUniqueID())) {
                caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        ("This mob has already been charmed by you!"), true);
                return;
            }

            ServerWorld world = (ServerWorld) target.getEntityWorld();
            world.spawnParticle(ParticleTypes.HEART, target.getPosX(), target.getPosY() + 1.0, target.getPosZ(), 5, 1.0, 0.0, 1.0, 0.0);
            entity.setCharmer(caster.getUniqueID());
            ((MobEntity) target).setAttackTarget(caster.getLastAttackedEntity());
            if (target instanceof TameableEntity)
            {
                ((TameableEntity) target).setOwnerId(caster.getUniqueID()); // Steal people's dogs
                ((TameableEntity) target).setTamed(true);
            }
            else if (target instanceof AbstractHorseEntity) // Tame horses
            {
                ((AbstractHorseEntity) target).setTamedBy(caster);
            }
        });
    }
    public static void classify(BlockPos targetBlock, PlayerEntity caster)
    {
        BlockState block = caster.getEntityWorld().getBlockState(targetBlock);
        IFormattableTextComponent text = block.getBlock().getTranslatedName();
        StringTextComponent text2 = new StringTextComponent(block.getBlock().getTranslatedName().getString() + " at x: " + targetBlock.getX() + " y: " + targetBlock.getY() + " z: " + targetBlock.getZ());
        caster.sendMessage(text2, caster.getUniqueID());

    }
}
