package de.larsensmods.stl_backport.forge.block;

import de.larsensmods.stl_backport.audio.STLSoundEvents;
import de.larsensmods.stl_backport.block.STLLeafLitterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.ForgeSoundType;
import org.jetbrains.annotations.NotNull;

public class STLLeafLitterBlockForge extends STLLeafLitterBlock {

    private static final ForgeSoundType SOUND = new ForgeSoundType(1, 1,
            STLSoundEvents.LEAF_LITTER_BREAK,
            STLSoundEvents.LEAF_LITTER_STEP,
            STLSoundEvents.LEAF_LITTER_PLACE,
            STLSoundEvents.LEAF_LITTER_HIT,
            STLSoundEvents.LEAF_LITTER_FALL);

    public STLLeafLitterBlockForge(Properties properties) {
        super(properties.sound(SOUND));
    }

    @Override
    public boolean isFlammable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return 100;
    }
}
