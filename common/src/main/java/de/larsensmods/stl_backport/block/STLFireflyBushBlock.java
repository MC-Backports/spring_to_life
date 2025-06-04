package de.larsensmods.stl_backport.block;

import de.larsensmods.stl_backport.audio.STLSoundEvents;
import de.larsensmods.stl_backport.particles.STLParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.List;
import java.util.Optional;

public class STLFireflyBushBlock extends BushBlock implements BonemealableBlock {

    public STLFireflyBushBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(30) == 0 && isMoonVisible(level) && level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) <= pos.getY()) {
            level.playLocalSound(pos, STLSoundEvents.FIREFLY_BUSH_IDLE.get(), SoundSource.AMBIENT, 0.6F, 1.0F, false);
        }

        level.updateSkyBrightness();

        if (level.getMaxLocalRawBrightness(pos) <= 13 && random.nextDouble() <= 0.7) {
            double partX = (double)pos.getX() + random.nextDouble() * 10.0 - 5.0;
            double partY = (double)pos.getY() + random.nextDouble() * 5.0;
            double partZ = (double)pos.getZ() + random.nextDouble() * 10.0 - 5.0;
            level.addParticle(STLParticleTypes.FIREFLY.get(), partX, partY, partZ, 0.0, 0.0, 0.0);
        }
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

    private boolean isMoonVisible(Level level) {
        if (!level.dimensionType().natural()) {
            return false;
        } else {
            int time = (int)(level.getDayTime() % 24000L);
            return time >= 12600 && time <= 23400;
        }
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
