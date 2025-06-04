package de.larsensmods.stl_backport.block;

import com.google.common.collect.Maps;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.function.Function;

public interface SegmentableBlock {

    IntegerProperty SEGMENT_AMOUNT = IntegerProperty.create("segment_amount", 1, 4);

    IntegerProperty AMOUNT = SEGMENT_AMOUNT;

    default Function<BlockState, VoxelShape> getShapeCalculator(EnumProperty<Direction> directionProperty, IntegerProperty segments) {
        Map<Direction, VoxelShape> shapes = Maps.newEnumMap(
                Map.of(
                        Direction.NORTH, Block.box(0.0, 0.0, 0.0, 8.0, this.getShapeHeight(), 8.0),
                        Direction.EAST, Block.box(8.0, 0.0, 0.0, 16.0, this.getShapeHeight(), 8.0),
                        Direction.SOUTH, Block.box(8.0, 0.0, 8.0, 16.0, this.getShapeHeight(), 16.0),
                        Direction.WEST, Block.box(0.0, 0.0, 8.0, 8.0, this.getShapeHeight(), 16.0)
                )
        );

        return state -> {
            VoxelShape resultShape = Shapes.empty();
            Direction resultDirection = state.getValue(directionProperty);
            int segmentCount = state.getValue(segments);

            for (int i = 0; i < segmentCount; i++) {
                resultShape = Shapes.or(resultShape, shapes.get(resultDirection));
                resultDirection = resultDirection.getCounterClockWise();
            }

            return resultShape.isEmpty()
                    ? Shapes.empty()
                    : Shapes.box(
                    resultShape.min(Direction.Axis.X),
                    resultShape.min(Direction.Axis.Y),
                    resultShape.min(Direction.Axis.Z),
                    resultShape.max(Direction.Axis.X),
                    resultShape.max(Direction.Axis.Y),
                    resultShape.max(Direction.Axis.Z)
            );
        };
    }

    default IntegerProperty getSegmentAmountProperty() {
        return AMOUNT;
    }

    default double getShapeHeight() {
        return 1.0;
    }

    default boolean canBeReplaced(BlockState state, BlockPlaceContext placeContext, IntegerProperty segments) {
        return !placeContext.isSecondaryUseActive() && placeContext.getItemInHand().is(state.getBlock().asItem()) && state.getValue(segments) < 4;
    }

    default BlockState getStateForPlacement(BlockPlaceContext placeContext, Block block, IntegerProperty segments, EnumProperty<Direction> direction) {
        BlockState state = placeContext.getLevel().getBlockState(placeContext.getClickedPos());
        return state.is(block)
                ? state.setValue(segments, Math.min(4, state.getValue(segments) + 1))
                : block.defaultBlockState().setValue(direction, placeContext.getHorizontalDirection().getOpposite());
    }

}
