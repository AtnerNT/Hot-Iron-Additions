package io.github.atnernt;

import io.github.atnernt.item.*;
import com.mojang.logging.LogUtils;
import net.regen.hotiron.HotIronMod;
import net.regen.hotiron.init.HotIronModMenus;
import net.regen.hotiron.init.HotIronModTabs;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HotIronAdditions.MODID)
public class HotIronAdditions
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "hot_iron_additions";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
  
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
   
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
   
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ROUGH_IRON_INGOT_ITEM = ITEMS.register("rough_iron_ingot", () -> new PolishableItem(new Item.Properties()));
    
    public static final RegistryObject<Item> ROUGH_IRON_BUCKET_ITEM = ITEMS.register("rough_iron_bucket", () -> new PolishableItem(new Item.Properties()));
    public static final RegistryObject<Item> HOT_IRON_BUCKET_ITEM = ITEMS.register("hot_iron_bucket", () -> new HotItem(new Item.Properties()));
    
    public static final RegistryObject<Item> ROUGH_IRON_PLATE_ITEM = ITEMS.register("rough_iron_plate", () -> new PolishableItem(new Item.Properties()));
    public static final RegistryObject<Item> HOT_IRON_PLATE_ITEM = ITEMS.register("hot_iron_plate", () -> new HotItem(new Item.Properties()));
    
    
    public static final RegistryObject<Item> UNFIRED_IRON_MOLD_ITEM = ITEMS.register("unfired_iron_ingot_mold", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRED_IRON_MOLD_ITEM = ITEMS.register("fired_iron_ingot_mold", () -> new FiredIronIngotMoldItem(new Item.Properties(), HotIronAdditions.ROUGH_IRON_INGOT_ITEM.get()));

    /*public static final RegistryObject<Item> B = ITEMS.register("alexandrite_helmet",
            () -> new ArmorItem(ModArmorMaterials.ALEXANDRITE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(18))));*/
    public static final RegistryObject<Item> BRIGANDINE_CHESTPLATE_ITEM = ITEMS.register("brigandine_chestplate",
            () -> new ArmorItem(ModArmourMaterials.BRIGANDINE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties()));
    public static final RegistryObject<Item> BRIGANDINE_GREAVES_ITEM = ITEMS.register("brigandine_greaves",
            () -> new ArmorItem(ModArmourMaterials.BRIGANDINE, ArmorItem.Type.LEGGINGS,
                    new Item.Properties()));
    public static final RegistryObject<Item> HEAVY_LEATHER_BOOTS_ITEM = ITEMS.register("brigandine_boots",
            () -> new ArmorItem(ModArmourMaterials.BRIGANDINE, ArmorItem.Type.BOOTS,
                    new Item.Properties()));

    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> HOT_IRON_ADDITIONS_TAB = CREATIVE_MODE_TABS.register("hot_iron_additions", () -> CreativeModeTab.builder()
            .withTabsBefore(HotIronModTabs.HOT_IRON.getId())
            .title(Component.translatable("item_group.hot_iron_additions.hot_iron_additions"))
            .icon(() -> ROUGH_IRON_INGOT_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
            	output.accept(UNFIRED_IRON_MOLD_ITEM.get());
                output.accept(FIRED_IRON_MOLD_ITEM.get());
                output.accept(HOT_IRON_BUCKET_ITEM.get());
                output.accept(HOT_IRON_PLATE_ITEM.get());
                output.accept(ROUGH_IRON_INGOT_ITEM.get());
                output.accept(ROUGH_IRON_BUCKET_ITEM.get());
                output.accept(ROUGH_IRON_PLATE_ITEM.get());
                output.accept(BRIGANDINE_CHESTPLATE_ITEM.get());
                output.accept(BRIGANDINE_GREAVES_ITEM.get());
                output.accept(HEAVY_LEATHER_BOOTS_ITEM.get());
                // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    public HotIronAdditions(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
 
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
       
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
