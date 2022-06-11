package me.vexinglemons.hexicon.item.custom.spells;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;

import static me.vexinglemons.hexicon.item.custom.spells.MobPotionNames.translate;
import static me.vexinglemons.hexicon.item.custom.spells.Words.hasAdverbs;
import static me.vexinglemons.hexicon.item.custom.spells.Words.hasDirectObject;

public class GlobalWordFunctions {

    public static int checkDirectionValidity(int[] modifiers) // 0 is good, 1 means there isn't a direction, 2 means there is more than 1 direction
    {
        if (modifiers[1] == 1 && modifiers[2] == 1 && modifiers[3] == 1 && modifiers[4] == 1) {
            return 1;
        }
        boolean onemodifier = false;
        int i = 0;
        for (int modifier : modifiers)
        {
            if (i == 0){continue;}

            if (modifier > 1)
            {
                if (onemodifier)
                {
                    return 2;
                }
                onemodifier = true;
            }
            i++;
        }
        return 0;

    }
    public static int[] getUsedAdverbs(int[] modifiers, int verb)
    {
        int i = 0;
        List<Integer> newModifiers = new ArrayList<>();
        for (int modifier  : modifiers)
        {
            if (hasAdverbs[verb][i])
            {
                newModifiers.add(modifier);
            }
            i++;
        }
        int[] array = new int[newModifiers.size()];
        for(int i2 = 0; i2 < newModifiers.size(); i2++) array[i2] = newModifiers.get(i2);
        return array;
    }

    public static Entity[] chooseTarget(PlayerEntity caster, String word, boolean[] specifiers, int verb)
    {
        if (!hasDirectObject[verb])
        {
            return null;
        }
        if (word.equalsIgnoreCase("massa"))
        {
            return null;
        }
        Entity[] target = null;
        EntityType entityType = null;
        boolean isAnything = word.equalsIgnoreCase("res");
        if (translate(word) != null)
        {
            entityType = translate(word);
        }

        if (specifiers[0] || specifiers[1])
        {
            ServerWorld world = (ServerWorld) caster.getEntityWorld();

            List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(caster, caster.getBoundingBox().expand(10.0f, 10.0f, 10.0f).expand(-10.0f, -10.0f, -10.0f));
            if (entities.size() <= 0)
            {
                caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        ("No mobs are within range."), true);
                return null;
            }
            target = new Entity[]{entities.get(0)};
            for(Entity entity : entities)
            {
                boolean canPass = isAnything || entity.getType() == entityType;
                if (canPass)
                {
                    if (specifiers[1]) //If the modifier is all nearest entities, add the entity to the target array
                    {
                        if (target[0].equals(entity))
                        {
                            continue;
                        }
                        Entity[] targets = new Entity[target.length + 1];
                        int i;
                        for(i = 0; i < target.length; i++) {
                            targets[i] = target[i];
                        }
                        targets[i] = entity;
                        target = targets;
                    }
                    else if (entity.getDistance(caster) < target[0].getDistance(caster)) //Otherwise, change to the nearest entity
                    {
                        target = new Entity[]{entity};
                    }
                }
            }
            if (!isAnything && target[0].getType() != entityType)
            {
                caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        ("No mobs of this kind are within range."), true);
                return null;
            }

        }
        if (target == null && entityType == EntityType.PLAYER)
        {
            target = new Entity[]{caster};
        }
        if (target == null && hasDirectObject[verb])
        {
            if (hasDirectObject[verb])
            {
                caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        (word + " isn't a compatible noun with its verb."), true);
            }

        }

        return target;
    }

    public static BlockPos[] chooseBlockTarget(PlayerEntity caster, boolean[] specifiers, int verb)
    {
        if (!hasDirectObject[verb])
        {
            return null;
        }
        BlockPos[] target = null;
        if (specifiers[0])
        {
            BlockPos below = caster.getPosition().down();
            target = new BlockPos[]{below};
        }
        return target;
    }
}