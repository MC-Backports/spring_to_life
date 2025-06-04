package de.larsensmods.stl_backport.worldgen.decorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AttachedToLogDecorator extends TreeDecorator {

    public static final Codec<AttachedToLogDecorator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(decorator -> decorator.probability),
            BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(decorator -> decorator.blockProvider),
            ExtraCodecs.nonEmptyList(Direction.CODEC.listOf()).fieldOf("directions").forGetter(decorator -> decorator.directions)
    ).apply(instance, AttachedToLogDecorator::new));

    private final float probability;
    private final BlockStateProvider blockProvider;
    private final List<Direction> directions;

    public AttachedToLogDecorator(float probability, BlockStateProvider blockProvider, List<Direction> directions) {
        this.probability = probability;
        this.blockProvider = blockProvider;
        this.directions = directions;
    }

    @Override
    public void place(TreeDecorator.Context decoratorContext) {
        RandomSource randomSource = decoratorContext.random();

        for(BlockPos pos : Util.shuffledCopy(decoratorContext.logs(), randomSource)) {
            Direction direction = Util.getRandom(this.directions, randomSource);
            BlockPos changePos = pos.relative(direction);
            if (randomSource.nextFloat() <= this.probability && decoratorContext.isAir(changePos)) {
                decoratorContext.setBlock(changePos, this.blockProvider.getState(randomSource, changePos));
            }
        }
    }

    @Override
    protected @NotNull TreeDecoratorType<?> type() {
        return STLDecoratorTypes.ATTACHED_TO_LOG.get();
    }
    
}
