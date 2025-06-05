package de.larsensmods.stl_backport.block;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.audio.STLSoundEvents;
import de.larsensmods.stl_backport.util.SoundUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class STLShortDryGrassBlock extends STLDryVegetationBlock implements BonemealableBlock {

    private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);

    protected STLShortDryGrassBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(200) == 0){
            if(
                    level.getBlockState(pos.below()).is(TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "desert_dry_vegetation_sound_trigger"))) &&
                            level.getBlockState(pos.below().below()).is(TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "desert_dry_vegetation_sound_trigger"))))
            {
                if (level.isClientSide()) {
                    SoundUtil.playPlayerSoundEffect(STLSoundEvents.DRY_GRASS.get(), SoundSource.AMBIENT, 1.0F, 1.0F, random.nextLong());
                }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.setBlockAndUpdate(pos, STLBlocks.TALL_DRY_GRASS.get().defaultBlockState());
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
        return SHAPE;
    }

}
