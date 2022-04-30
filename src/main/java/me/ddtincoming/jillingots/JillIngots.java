package me.ddtincoming.jillingots;

import me.ddtincoming.jillingots.block.ModBlocks;
import me.ddtincoming.jillingots.capabilities.*;
import me.ddtincoming.jillingots.capabilities.BookDataFactory;
import me.ddtincoming.jillingots.capabilities.BookDataStorage;
import me.ddtincoming.jillingots.capabilities.IBookData;
import me.ddtincoming.jillingots.container.ModContainers;
import me.ddtincoming.jillingots.entities.ModEntityTypes;
import me.ddtincoming.jillingots.item.ModItems;
import me.ddtincoming.jillingots.item.custom.WaterstoneTick;
import me.ddtincoming.jillingots.item.custom.spells.SayingSpells;
import me.ddtincoming.jillingots.item.custom.spells.Spellbook;
import me.ddtincoming.jillingots.screen.LightningChannelerScreen;
import me.ddtincoming.jillingots.tileentity.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JillIngots.MOD_ID)
public class JillIngots {
    public static final String MOD_ID = "jillingots";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public JillIngots() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModContainers.register(eventBus);
        ModTileEntities.register(eventBus);
        ModEntityTypes.register(eventBus);
        forgeEventBus.register(new WaterstoneTick());
        forgeEventBus.register(new SayingSpells());
        forgeEventBus.register(new Spellbook());
        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        CapabilityManager.INSTANCE.register(IPlayerData.class, new PlayerDataStorage(), PlayerDataFactory::new);
        CapabilityManager.INSTANCE.register(IMobData.class, new MobDataStorage(), MobDataFactory::new);
        CapabilityManager.INSTANCE.register(IBookData.class, new BookDataStorage(), BookDataFactory::new);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
        ScreenManager.registerFactory(ModContainers.LIGHTNING_CHANNELER_CONTAINER.get(), LightningChannelerScreen::new);


    }


    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("jillingots", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }
}
