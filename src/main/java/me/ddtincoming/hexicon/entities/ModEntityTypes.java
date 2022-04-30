package me.ddtincoming.hexicon.entities;

import me.ddtincoming.hexicon.Hexicon;
import me.ddtincoming.hexicon.entities.custom.MagicProjectileEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Hexicon.MOD_ID);

    public static final RegistryObject<EntityType<MagicProjectileEntity>> MAGIC_PROJECTILE =
            ENTITY_TYPES.register("projectile",
                    () -> EntityType.Builder.create(MagicProjectileEntity::new,
                            EntityClassification.MISC).setTrackingRange(64).setUpdateInterval(1).size(0.25f, 0.25f)
                            .build(new ResourceLocation(Hexicon.MOD_ID, "projectile").toString()));

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}
