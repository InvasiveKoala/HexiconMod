package me.ddtincoming.jillingots.item;

import me.ddtincoming.jillingots.JillIngots;
import me.ddtincoming.jillingots.item.custom.Firestone;
import me.ddtincoming.jillingots.item.custom.Waterstone;
import me.ddtincoming.jillingots.item.custom.spells.Spellbook;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, JillIngots.MOD_ID);


    public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst",
            () -> new Item(new Item.Properties().group(ModItemGroup.JILL_GROUP)));
    public static final RegistryObject<Item> SUSSY = ITEMS.register("sussy",
            () -> new Item(new Item.Properties().group(ModItemGroup.JILL_GROUP)));
    public static final RegistryObject<Item> FIRESTONE = ITEMS.register("firestone",
            () -> new Firestone(new Item.Properties().group(ModItemGroup.JILL_GROUP).maxDamage(8)));
    public static final RegistryObject<Item> WATERSTONE = ITEMS.register("waterstone",
            () -> new Waterstone(new Item.Properties().group(ModItemGroup.JILL_GROUP).maxDamage(8)));
    public static final RegistryObject<Item> AMETHYST_SWORD = ITEMS.register("amethyst_sword",
            () -> new SwordItem(ModItemTier.AMETHYST, 2, 3f, new Item.Properties().group(ModItemGroup.JILL_GROUP)));

    public static final RegistryObject<Item> AMETHYST_PICKAXE = ITEMS.register("amethyst_pickaxe",
            () -> new PickaxeItem(ModItemTier.AMETHYST, 0, 0f, new Item.Properties().group(ModItemGroup.JILL_GROUP)));

    public static final RegistryObject<Item> AMETHYST_SHOVEL = ITEMS.register("amethyst_shovel",
            () -> new ShovelItem(ModItemTier.AMETHYST, 0, 1f, new Item.Properties().group(ModItemGroup.JILL_GROUP)));

    public static final RegistryObject<Item> AMETHYST_AXE = ITEMS.register("amethyst_axe",
            () -> new AxeItem(ModItemTier.AMETHYST, 4, 1f, new Item.Properties().group(ModItemGroup.JILL_GROUP)));

    public static final RegistryObject<Item> AMETHYST_HOE = ITEMS.register("amethyst_hoe",
            () -> new HoeItem(ModItemTier.AMETHYST, 0, 2f, new Item.Properties().group(ModItemGroup.JILL_GROUP)));

    public static final RegistryObject<Item> AMETHYST_BOOTS = ITEMS.register("amethyst_boots",
            () -> new ArmorItem(ModArmorMaterial.AMETHYST, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroup.JILL_GROUP)));
    public static final RegistryObject<Item> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate",
            () -> new ArmorItem(ModArmorMaterial.AMETHYST, EquipmentSlotType.CHEST, new Item.Properties().group(ModItemGroup.JILL_GROUP)));
    public static final RegistryObject<Item> AMETHYST_LEGGINGS = ITEMS.register("amethyst_leggings",
            () -> new ArmorItem(ModArmorMaterial.AMETHYST, EquipmentSlotType.LEGS, new Item.Properties().group(ModItemGroup.JILL_GROUP)));
    public static final RegistryObject<Item> AMETHYST_HELMET = ITEMS.register("amethyst_helmet",
            () -> new ArmorItem(ModArmorMaterial.AMETHYST, EquipmentSlotType.HEAD, new Item.Properties().group(ModItemGroup.JILL_GROUP)));


    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
