package de.larsensmods.stl_backport.block;

import de.larsensmods.regutil.IRegistrationProvider;
import de.larsensmods.stl_backport.SpringToLifeMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class STLBlocks {

    public static Supplier<Block> LEAF_LITTER;
    public static Supplier<Block> WILDFLOWERS;
    public static Supplier<Block> BUSH;
    public static Supplier<Block> FIREFLY_BUSH;

    public static Supplier<Block> SHORT_DRY_GRASS;
    public static Supplier<Block> TALL_DRY_GRASS;

    public static Supplier<Block> CACTUS_FLOWER;

    public static void initBlocks(IRegistrationProvider provider) {
        SpringToLifeMod.LOGGER.info("Initializing blocks");

        LEAF_LITTER = provider.registerBlock(
                "leaf_litter",
                STLLeafLitterBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_BROWN)
                        .replaceable()
                        .noCollission()
                        .pushReaction(PushReaction.DESTROY)
        );

        WILDFLOWERS = provider.registerBlock(
                "wildflowers",
                PinkPetalsBlock::new,
                BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)
        );

        BUSH = provider.registerBlock(
                "bush",
                STLBushBlock::new,
                Block.Properties.of()
                        .mapColor(MapColor.PLANT)
                        .replaceable()
                        .noCollission()
                        .instabreak()
                        .sound(SoundType.GRASS)
                        .ignitedByLava()
                        .pushReaction(PushReaction.DESTROY)
        );

        FIREFLY_BUSH = provider.registerBlock(
                "firefly_bush",
                STLFireflyBushBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.PLANT)
                        .ignitedByLava()
                        .lightLevel(state -> 2)
                        .noCollission()
                        .instabreak()
                        .sound(SoundType.SWEET_BERRY_BUSH)
                        .pushReaction(PushReaction.DESTROY)
        );

        SHORT_DRY_GRASS = provider.registerBlock(
                "short_dry_grass",
                STLShortDryGrassBlock::new,
                Block.Properties.of()
                        .mapColor(MapColor.COLOR_YELLOW)
                        .replaceable()
                        .noCollission()
                        .instabreak()
                        .sound(SoundType.GRASS)
                        .ignitedByLava()
                        .offsetType(BlockBehaviour.OffsetType.XYZ)
                        .pushReaction(PushReaction.DESTROY)
        );

        TALL_DRY_GRASS = provider.registerBlock(
                "tall_dry_grass",
                STLTallDryGrassBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_YELLOW)
                        .replaceable()
                        .noCollission()
                        .instabreak()
                        .sound(SoundType.GRASS)
                        .ignitedByLava()
                        .offsetType(BlockBehaviour.OffsetType.XYZ)
                        .pushReaction(PushReaction.DESTROY)
        );

        CACTUS_FLOWER = provider.registerBlock(
                "cactus_flower",
                STLCactusFlowerBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_PINK)
                        .noCollission()
                        .instabreak()
                        .ignitedByLava()
                        .sound(SoundType.AZALEA)
                        .pushReaction(PushReaction.DESTROY)
        );

        provider.finalizeRegistrationStage(IRegistrationProvider.RegistrationStage.BLOCKS);
    }

}
