package de.larsensmods.stl_backport.block;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.audio.STLSoundEvents;
import de.larsensmods.stl_backport.util.SoundUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

import java.util.List;
import java.util.Optional;

public class STLTallDryGrassBlock extends STLDryVegetationBlock implements BonemealableBlock {

    private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    protected STLTallDryGrassBlock(BlockBehaviour.Properties properties) {
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
        return getValidSpreadPos(Direction.Plane.HORIZONTAL.stream().toList(), level, pos, STLBlocks.SHORT_DRY_GRASS.get().defaultBlockState()).isPresent();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        getValidSpreadPos(Direction.Plane.HORIZONTAL.shuffledCopy(level.random), level, pos, STLBlocks.SHORT_DRY_GRASS.get().defaultBlockState())
                .ifPresent(param1x -> level.setBlockAndUpdate(param1x, STLBlocks.SHORT_DRY_GRASS.get().defaultBlockState()));
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext collisionContext) {
        return SHAPE;
    }

    private Optional<BlockPos> getValidSpreadPos(List<Direction> directions, LevelReader levelReader, BlockPos blockPos, BlockState state) {
        for(Direction dir : directions) {
            BlockPos pos = blockPos.relative(dir);
            if (levelReader.isEmptyBlock(pos) && state.canSurvive(levelReader, pos)) {
                return Optional.of(pos);
            }
        }

        return Optional.empty();
    }
}
