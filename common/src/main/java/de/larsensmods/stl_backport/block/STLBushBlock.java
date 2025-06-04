package de.larsensmods.stl_backport.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class STLBushBlock extends BushBlock implements BonemealableBlock {

    private static final VoxelShape SHAPE = Block.box(8.0 - 16.0 / 2.0, 0.0, 8.0 - 16.0 / 2.0, 8.0 + 16.0 / 2.0, 13.0, 8.0 + 16.0 / 2.0);

    protected STLBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return getValidSpreadPos(Direction.Plane.HORIZONTAL.stream().toList(), level, pos, state).isPresent();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos blockPos, BlockState state) {
        Optional<BlockPos> neighbourPos = getValidSpreadPos(Direction.Plane.HORIZONTAL.shuffledCopy(level.random), level, blockPos, state);
        neighbourPos.ifPresent(pos -> level.setBlockAndUpdate(pos, this.defaultBlockState()));
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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
