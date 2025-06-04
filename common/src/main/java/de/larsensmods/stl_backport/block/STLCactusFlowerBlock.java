package de.larsensmods.stl_backport.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class STLCactusFlowerBlock extends BushBlock {

    private static final VoxelShape SHAPE = Block.box(8.0 - 14.0 / 2.0, 0.0, 8.0 - 14.0 / 2.0, 8.0 + 14.0 / 2.0, 12.0, 8.0 + 14.0 / 2.0);

    protected STLCactusFlowerBlock(Properties properties) {
        super(properties);
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        BlockState placeOn = level.getBlockState(pos);
        return placeOn.is(Blocks.CACTUS) || placeOn.is(Blocks.FARMLAND) || placeOn.isFaceSturdy(level, pos, Direction.UP, SupportType.CENTER);
    }
}
