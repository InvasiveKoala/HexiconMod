package me.vexinglemons.hexicon.spells;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static me.vexinglemons.hexicon.spells.MobPotionNames.translate;
import static me.vexinglemons.hexicon.spells.Words.*;

public class GlobalWordFunctions {

    public static int checkDirectionValidity(ArrayList<Integer> modifiers) // 0 is good, 1 means there isn't a direction, 2 means there is more than 1 direction
    {
        if (modifiers.get(1) > 1 && modifiers.get(2) > 1 && modifiers.get(3) > 1 && modifiers.get(4) > 1) {
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
    public static ArrayList<Integer> getUsedAdverbs(HashMap<String,Integer> modifiers, String verb)
    {
        int i = 0;
        ArrayList<Integer> newModifiers = new ArrayList<>();
        ArrayList<String> modifiersList = new ArrayList<>(modifiers.keySet());
        ArrayList<String> adverbs = verbAdverbs.get(verb);
        boolean potionAllowed = potionVerbs.contains(verb);;
        boolean advAllowed;
        if (adverbs == null && !potionVerbs.contains(verb))
        {
            return null;
        }
        else if (potionAllowed)
        {
            for (String effect : potions)
            {
                newModifiers.add(modifiers.get(effect));
            }
        }
        for (String modifier  : modifiersList)
        {
            try {advAllowed = adverbs.contains(modifier);} catch(NullPointerException e) {advAllowed = false;}
            if (advAllowed)
            {
                newModifiers.add(modifiers.get(modifier));
            }
            i++;
        }
        ArrayList<Integer> array = new ArrayList<>();
        for(int i2 = 0; i2 < newModifiers.size(); i2++) array.add(newModifiers.get(i2));
        return array;
    }

    public static ArrayList<Entity> chooseTarget(PlayerEntity caster, String word, ArrayList<Boolean> specifiers, boolean hasDirectObject)
    {
        if (!hasDirectObject)
        {
            return null;
        }
        if (word.equalsIgnoreCase("massa"))
        {
            return null;
        }
        ArrayList<Entity> target = null;
        EntityType entityType = null;
        boolean isAnything = word.equalsIgnoreCase("res");
        if (translate(word) != null)
        {
            entityType = translate(word);
        }

        if (specifiers.get(0) || specifiers.get(1))
        {
            ServerWorld world = (ServerWorld) caster.getEntityWorld();

            List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(caster, caster.getBoundingBox().expand(10.0f, 10.0f, 10.0f).expand(-10.0f, -10.0f, -10.0f));
            if (entities.size() <= 0)
            {
                caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        ("No mobs are within range."), true);
                return null;
            }
            target = new ArrayList<>(Arrays.asList(entities.get(0)));
            for(Entity entity : entities)
            {
                boolean canPass = isAnything || entity.getType() == entityType;
                if (canPass)
                {
                    if (specifiers.get(1)) //If the modifier is all nearest entities, add the entity to the target array
                    {
                        if (target.get(0).equals(entity))
                        {
                            continue;
                        }
                        target.add(entity);
                    }
                    else if (entity.getDistance(caster) < target.get(0).getDistance(caster)) //Otherwise, change to the nearest entity
                    {
                        target = new ArrayList<>(Arrays.asList(entity));
                    }
                }
            }
            try {
                if (!isAnything && target.get(0).getType() != entityType) {
                    caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                            ("No mobs of this kind are within range."), true);
                    return null;
                }
            } catch(NullPointerException e)   {return null;}

        }
        if (target == null && entityType == EntityType.PLAYER)
        {
            target = new ArrayList<>(Arrays.asList(caster));
        }
        else if (target == null && hasDirectObject)
        {
            caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                    (word + " isn't a compatible noun with its verb."), true);

        }

        return target;
    }

    public static ArrayList<BlockPos> chooseBlockTarget(PlayerEntity caster, HashMap<String, Integer> specifiers, boolean hasDirectObject)
    {
        if (!hasDirectObject)
        {
            return null;
        }
        boolean inclusive = specifiers.get("comprehendo") > 1 ; // hi po0opybutt lickers this finds if its inclusive of all blocks
        ArrayList<BlockPos> target = new ArrayList<>();
        ArrayList<String> directions = new ArrayList<>(specifiers.keySet()); // down, up, east, west
        int i = 0;
        BlockPos currentBlock = caster.getPosition();
        for (String direction : directions)
        {
            for (int amt = 1; amt < specifiers.get(direction); amt++) {
                if (direction.equals("sub")) {currentBlock = currentBlock.down(); } //Down
                else if (direction.equals("supra")) {currentBlock = currentBlock.up(); } //Up
                else if (direction.equals("orientem")) {currentBlock = currentBlock.east(); } //East
                else if (direction.equals("occidens")) {currentBlock = currentBlock.west(); } //West

                target.add(currentBlock);
            }
            i++;
        }
        if (inclusive) {
            return target;
        } else {
            return new ArrayList<>(Arrays.asList(currentBlock));}
    }
}