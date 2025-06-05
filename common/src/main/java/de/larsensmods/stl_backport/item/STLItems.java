package de.larsensmods.stl_backport.item;

import de.larsensmods.regutil.IRegistrationProvider;
import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.block.STLBlocks;
import de.larsensmods.stl_backport.entity.STLEntityTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;

import java.util.function.Supplier;

public class STLItems {

    //SPAWN EGGS
    public static Supplier<Item> WARM_CHICKEN_SPAWN_EGG;
    public static Supplier<Item> COLD_CHICKEN_SPAWN_EGG;
    public static Supplier<Item> WARM_PIG_SPAWN_EGG;
    public static Supplier<Item> COLD_PIG_SPAWN_EGG;
    public static Supplier<Item> WARM_COW_SPAWN_EGG;
    public static Supplier<Item> COLD_COW_SPAWN_EGG;

    //ITEMS
    public static Supplier<Item> BLUE_EGG;
    public static Supplier<Item> BROWN_EGG;

    //BLOCK ITEMS
    public static Supplier<Item> LEAF_LITTER;
    public static Supplier<Item> WILDFLOWERS;
    public static Supplier<Item> BUSH;
    public static Supplier<Item> FIREFLY_BUSH;
    public static Supplier<Item> SHORT_DRY_GRASS;
    public static Supplier<Item> TALL_DRY_GRASS;
    public static Supplier<Item> CACTUS_FLOWER;

    public static void initItems(IRegistrationProvider registrationProvider){
        SpringToLifeMod.LOGGER.info("Initializing items");

        WARM_CHICKEN_SPAWN_EGG = registrationProvider.registerItem(
                "warm_chicken_spawn_egg",
                () -> new SpawnEggItem(STLEntityTypes.WARM_CHICKEN.get(), 0xFFAA00, 0xE5B54E, new Item.Properties())
        );
        COLD_CHICKEN_SPAWN_EGG = registrationProvider.registerItem(
                "cold_chicken_spawn_egg",
                () -> new SpawnEggItem(STLEntityTypes.COLD_CHICKEN.get(), 0xADACAC, 0x696969, new Item.Properties())
        );
        WARM_PIG_SPAWN_EGG = registrationProvider.registerItem(
                "warm_pig_spawn_egg",
                () -> new SpawnEggItem(STLEntityTypes.WARM_PIG.get(), 0x914304, 0xE1B88C, new Item.Properties())
        );
        COLD_PIG_SPAWN_EGG = registrationProvider.registerItem(
                "cold_pig_spawn_egg",
                () -> new SpawnEggItem(STLEntityTypes.COLD_PIG.get(), 0xD8C17C, 0xF1D0AC, new Item.Properties())
        );
        WARM_COW_SPAWN_EGG = registrationProvider.registerItem(
                "warm_cow_spawn_egg",
                () -> new SpawnEggItem(STLEntityTypes.WARM_COW.get(), 0x994122, 0xBE826C, new Item.Properties())
        );
        COLD_COW_SPAWN_EGG = registrationProvider.registerItem(
                "cold_cow_spawn_egg",
                () -> new SpawnEggItem(STLEntityTypes.COLD_COW.get(), 0xBE7A31, 0xE3BA71, new Item.Properties())
        );

        BLUE_EGG = registrationProvider.registerItem(
                "blue_egg",
                () -> new STLEggItem(new Item.Properties().stacksTo(16))
        );
        BROWN_EGG = registrationProvider.registerItem(
                "brown_egg",
                () -> new STLEggItem(new Item.Properties().stacksTo(16))
        );

        LEAF_LITTER = registerBlockItem(
                registrationProvider,
                "leaf_litter",
                STLBlocks.LEAF_LITTER,
                new Item.Properties()
        );
        WILDFLOWERS = registerBlockItem(
                registrationProvider,
                "wildflowers",
                STLBlocks.WILDFLOWERS,
                new Item.Properties()
        );
        BUSH = registerBlockItem(
                registrationProvider,
                "bush",
                STLBlocks.BUSH,
                new Item.Properties()
        );
        FIREFLY_BUSH = registerBlockItem(
                registrationProvider,
                "firefly_bush",
                STLBlocks.FIREFLY_BUSH,
                new Item.Properties()
        );
        SHORT_DRY_GRASS = registerBlockItem(
                registrationProvider,
                "short_dry_grass",
                STLBlocks.SHORT_DRY_GRASS,
                new Item.Properties()
        );
        TALL_DRY_GRASS = registerBlockItem(
                registrationProvider,
                "tall_dry_grass",
                STLBlocks.TALL_DRY_GRASS,
                new Item.Properties()
        );
        CACTUS_FLOWER = registerBlockItem(
                registrationProvider,
                "cactus_flower",
                STLBlocks.CACTUS_FLOWER,
                new Item.Properties()
        );

        registrationProvider.finalizeRegistrationStage(IRegistrationProvider.RegistrationStage.ITEMS);
    }

    private static Supplier<Item> registerBlockItem(IRegistrationProvider provider, String key, Supplier<Block> block, Item.Properties properties){
        return provider.registerItem(key, () -> new BlockItem(block.get(), properties));
    }

}
