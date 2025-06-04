package de.larsensmods.stl_backport.worldgen.decorators;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlaceOnGroundDecorator extends TreeDecorator {

    public static final Codec<PlaceOnGroundDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(decorator -> decorator.tries),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("radius").orElse(2).forGetter(decorator -> decorator.radius),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("height").orElse(1).forGetter(decorator -> decorator.height),
                    BlockStateProvider.CODEC.fieldOf("block_state_provider").forGetter(decorator -> decorator.blockStateProvider)
            ).apply(instance, PlaceOnGroundDecorator::new)
    );

    private final int tries;
    private final int radius;
    private final int height;
    private final BlockStateProvider blockStateProvider;

    public PlaceOnGroundDecorator(int tries, int radius, int height, BlockStateProvider blockStateProvider) {
        this.tries = tries;
        this.radius = radius;
        this.height = height;
        this.blockStateProvider = blockStateProvider;
    }

    @Override
    public void place(TreeDecorator.Context decoratorContext) {
        List<BlockPos> possiblePositions = getLowestTrunkOrRootOfTree(decoratorContext);
        if (!possiblePositions.isEmpty()) {
            BlockPos firstPos = possiblePositions.get(0);
            int firstPosY = firstPos.getY();
            int firstPosX = firstPos.getX();
            int firstPosX1 = firstPos.getX();
            int firstPosZ = firstPos.getZ();
            int firstPosZ1 = firstPos.getZ();

            for (BlockPos pos : possiblePositions) {
                if (pos.getY() == firstPosY) {
                    firstPosX = Math.min(firstPosX, pos.getX());
                    firstPosX1 = Math.max(firstPosX1, pos.getX());
                    firstPosZ = Math.min(firstPosZ, pos.getZ());
                    firstPosZ1 = Math.max(firstPosZ1, pos.getZ());
                }
            }

            RandomSource random = decoratorContext.random();
            BoundingBox boundingBox = new BoundingBox(firstPosX, firstPosY, firstPosZ, firstPosX1, firstPosY, firstPosZ1).inflatedBy(Math.max(this.height, this.radius));

            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

            for (int i = 0; i < this.tries; i++) {
                mutableBlockPos.set(
                        random.nextIntBetweenInclusive(boundingBox.minX(), boundingBox.maxX()),
                        random.nextIntBetweenInclusive(boundingBox.minY(), boundingBox.maxY()),
                        random.nextIntBetweenInclusive(boundingBox.minZ(), boundingBox.maxZ())
                );
                this.attemptToPlaceBlockAbove(decoratorContext, mutableBlockPos);
            }
        }
    }

    private void attemptToPlaceBlockAbove(TreeDecorator.Context decoratorContext, BlockPos pos) {
        BlockPos abovePos = pos.above();
        if (decoratorContext.level().isStateAtPosition(abovePos, state -> state.isAir() || state.is(Blocks.VINE))
                && decoratorContext.level().isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isSolid)
                && decoratorContext.level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos).getY() <= abovePos.getY()) {
            decoratorContext.setBlock(abovePos, this.blockStateProvider.getState(decoratorContext.random(), abovePos));
        }
    }

    public List<BlockPos> getLowestTrunkOrRootOfTree(TreeDecorator.Context decoratorContext) {
        List<BlockPos> lowestTrunk = Lists.newArrayList();
        List<BlockPos> roots = decoratorContext.roots();
        List<BlockPos> logs = decoratorContext.logs();
        if (roots.isEmpty()) {
            lowestTrunk.addAll(logs);
        } else if (!logs.isEmpty() && roots.get(0).getY() == logs.get(0).getY()) {
            lowestTrunk.addAll(logs);
            lowestTrunk.addAll(roots);
        } else {
            lowestTrunk.addAll(roots);
        }

        return lowestTrunk;
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return STLDecoratorTypes.PLACE_ON_GROUND.get();
    }
}