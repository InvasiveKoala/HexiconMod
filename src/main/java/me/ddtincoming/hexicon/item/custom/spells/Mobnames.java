package me.ddtincoming.hexicon.item.custom.spells;

import net.minecraft.entity.EntityType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;

import static me.ddtincoming.hexicon.util.Methods.findIndex;

public class Mobnames {

    public static EntityType[] EnglishTypes = {EntityType.PLAYER, EntityType.WOLF, EntityType.CAT, EntityType.COW,
            EntityType.HORSE, EntityType.SHEEP, EntityType.PIG, EntityType.BAT, EntityType.BEE, EntityType.OCELOT, EntityType.CHICKEN, EntityType.ARMOR_STAND, EntityType.ARROW, EntityType.COD, EntityType.DOLPHIN,
    EntityType.ENDER_PEARL, EntityType.EYE_OF_ENDER, EntityType.FIREBALL, EntityType.FOX, EntityType.SQUID, EntityType.IRON_GOLEM,
    EntityType.LLAMA, EntityType.MOOSHROOM, EntityType.MULE, EntityType.PARROT, EntityType.TNT, EntityType.TRIDENT};

    public static EntityType translate(String latin){
        int index = findIndex(Words.mobs, latin);
        if (index == -1)
        {
            return null;
        }
        return EnglishTypes[index];
    }
    public static Effect returnPotionType(int number)
    {
        switch (number)
        {
            case 0: return Effects.REGENERATION;
            case 1: return Effects.STRENGTH;
            case 2: return Effects.SPEED;
            case 3: return Effects.SLOW_FALLING;
            case 4: return Effects.SLOWNESS;
            case 5: return Effects.HASTE;
            case 6: return Effects.MINING_FATIGUE;
            case 7: return Effects.JUMP_BOOST;
            case 8: return Effects.NAUSEA;
            case 9: return Effects.FIRE_RESISTANCE;
            case 10: return Effects.WATER_BREATHING;
            case 11: return Effects.INVISIBILITY;
            case 12: return Effects.BLINDNESS;
            case 13: return Effects.NIGHT_VISION;
            case 14: return Effects.HUNGER;
            case 15: return Effects.WEAKNESS;
            case 16: return Effects.POISON;
            case 17: return Effects.WITHER;
            case 18: return Effects.GLOWING;
            case 19: return Effects.LEVITATION;
            case 20: return Effects.DOLPHINS_GRACE;
        }
        return null;
    }
}
