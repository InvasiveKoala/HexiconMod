package me.vexinglemons.hexicon;

import me.vexinglemons.hexicon.block.ModBlocks;
import me.vexinglemons.hexicon.capabilities.BookData.BookDataFactory;
import me.vexinglemons.hexicon.capabilities.BookData.BookDataStorage;
import me.vexinglemons.hexicon.capabilities.BookData.IBookData;
import me.vexinglemons.hexicon.capabilities.MobData.IMobData;
import me.vexinglemons.hexicon.capabilities.MobData.MobDataFactory;
import me.vexinglemons.hexicon.capabilities.MobData.MobDataStorage;
import me.vexinglemons.hexicon.capabilities.PlayerData.IPlayerData;
import me.vexinglemons.hexicon.capabilities.PlayerData.PlayerDataFactory;
import me.vexinglemons.hexicon.capabilities.PlayerData.PlayerDataStorage;
import me.vexinglemons.hexicon.container.ModContainers;
import me.vexinglemons.hexicon.entities.ModEntityTypes;
import me.vexinglemons.hexicon.item.ModItems;
import me.vexinglemons.hexicon.item.custom.WaterstoneTick;
import me.vexinglemons.hexicon.item.custom.spells.SayingSpells;
import me.vexinglemons.hexicon.item.custom.spells.Spellbook;
import me.vexinglemons.hexicon.screen.LightningChannelerScreen;
import me.vexinglemons.hexicon.tileentity.ModTileEntities;
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
@Mod(Hexicon.MOD_ID)
public class Hexicon {
    public static final String MOD_ID = "hexicon";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Hexicon() {
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
        InterModComms.sendTo("hexicon", "helloworld", () -> {
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
