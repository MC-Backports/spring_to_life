package de.larsensmods.stl_backport.forge;

import de.larsensmods.stl_backport.block.STLBlocks;
import de.larsensmods.stl_backport.entity.STLEntityTypes;
import de.larsensmods.stl_backport.forge.block.*;
import de.larsensmods.stl_backport.forge.item.STLFuelBlockItem;
import de.larsensmods.stl_backport.forge.register.ForgeRegistrationProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import de.larsensmods.stl_backport.SpringToLifeMod;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.function.Function;
import java.util.function.Supplier;

@Mod(SpringToLifeMod.MOD_ID)
public final class SpringToLifeModForge {

    public SpringToLifeModForge() {
        this(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public SpringToLifeModForge(FMLJavaModLoadingContext context) {
        this(context.getModEventBus());
    }

    public SpringToLifeModForge(IEventBus bus) {
        ForgeRegistrationProvider registrationProvider = new ForgeRegistrationProvider(bus);

        //Register override keys
        registrationProvider.addOverrideKey("block:leaf_litter", (Function<BlockBehaviour.Properties, Block>) STLLeafLitterBlockForge::new);
        registrationProvider.addOverrideKey("block:wildflowers", (Function<BlockBehaviour.Properties, Block>) PinkPetalsBlockForge::new);
        registrationProvider.addOverrideKey("block:bush", (Function<BlockBehaviour.Properties, Block>) STLBushBlockForge::new);
        registrationProvider.addOverrideKey("block:firefly_bush", (Function<BlockBehaviour.Properties, Block>) STLFireflyBushBlockForge::new);
        registrationProvider.addOverrideKey("block:short_dry_grass", (Function<BlockBehaviour.Properties, Block>) STLShortDryGrassBlockForge::new);
        registrationProvider.addOverrideKey("block:tall_dry_grass", (Function<BlockBehaviour.Properties, Block>) STLTallDryGrassBlockForge::new);
        registrationProvider.addOverrideKey("block:cactus_flower", (Function<BlockBehaviour.Properties, Block>) STLCactusFlowerBlockForge::new);

        registrationProvider.addOverrideKey("item:warm_chicken_spawn_egg", (Supplier<Item>) () -> new ForgeSpawnEggItem(STLEntityTypes.WARM_CHICKEN, 0xFFAA00, 0xE5B54E, new Item.Properties()));
        registrationProvider.addOverrideKey("item:cold_chicken_spawn_egg", (Supplier<Item>) () -> new ForgeSpawnEggItem(STLEntityTypes.COLD_CHICKEN, 0xADACAC, 0x696969, new Item.Properties()));
        registrationProvider.addOverrideKey("item:warm_pig_spawn_egg", (Supplier<Item>) () -> new ForgeSpawnEggItem(STLEntityTypes.WARM_PIG, 0x914304, 0xE1B88C, new Item.Properties()));
        registrationProvider.addOverrideKey("item:cold_pig_spawn_egg", (Supplier<Item>) () -> new ForgeSpawnEggItem(STLEntityTypes.COLD_PIG, 0xD8C17C, 0xF1D0AC, new Item.Properties()));
        registrationProvider.addOverrideKey("item:warm_cow_spawn_egg", (Supplier<Item>) () -> new ForgeSpawnEggItem(STLEntityTypes.WARM_COW, 0x994122, 0xBE826C, new Item.Properties()));
        registrationProvider.addOverrideKey("item:cold_cow_spawn_egg", (Supplier<Item>) () -> new ForgeSpawnEggItem(STLEntityTypes.COLD_COW, 0xD8C17C, 0xF1D0AC, new Item.Properties()));

        registrationProvider.addOverrideKey("item:leaf_litter", (Supplier<Item>) () -> new STLFuelBlockItem(STLBlocks.LEAF_LITTER.get(), new Item.Properties(), 100));
        registrationProvider.addOverrideKey("item:short_dry_grass", (Supplier<Item>) () -> new STLFuelBlockItem(STLBlocks.SHORT_DRY_GRASS.get(), new Item.Properties(), 100));
        registrationProvider.addOverrideKey("item:tall_dry_grass", (Supplier<Item>) () -> new STLFuelBlockItem(STLBlocks.TALL_DRY_GRASS.get(), new Item.Properties(), 100));

        // Run our common setup.
        SpringToLifeMod.init(registrationProvider, FMLEnvironment.dist.isClient());
    }
}
