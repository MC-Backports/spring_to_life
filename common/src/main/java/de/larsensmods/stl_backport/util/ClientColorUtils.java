package de.larsensmods.stl_backport.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

@Environment(EnvType.CLIENT)
public class ClientColorUtils implements ColorUtils {

    private static final int AZALEA_LEAVES_COLOR = 0x70922D;

    public static final ColorResolver DRY_FOLIAGE_COLOR_RESOLVER = new ColorResolver() {
        @Override
        public int getColor(Biome biome, double d, double e) {
            return getDryFoliageColorOverride(biome).orElseGet(() -> getDryFoliageColorFromTexture(biome));
        }
    };

    @Override
    public int getAverageDryFoliageColor(BlockAndTintGetter blockAndTintGetter, BlockPos pos) {
        return blockAndTintGetter.getBlockTint(pos, DRY_FOLIAGE_COLOR_RESOLVER);
    }

    @Override
    public int getBlockColor(BlockState state, Level level, BlockPos pos, int tintIndex) {
        if(state.is(Blocks.AZALEA_LEAVES) || state.is(Blocks.FLOWERING_AZALEA_LEAVES)){
            return AZALEA_LEAVES_COLOR;
        }
        return Minecraft.getInstance().getBlockColors().getColor(state, level, pos, tintIndex);
    }

    private static Optional<Integer> getDryFoliageColorOverride(Biome biome) {
        return Optional.empty();
    }

    private static int getDryFoliageColorFromTexture(Biome biome) {
        double temp = Mth.clamp(biome.getBaseTemperature(), 0.0F, 1.0F);
        double downfall = Mth.clamp(getDownfall(biome), 0.0F, 1.0F);
        return DryFoliageColor.get(temp, downfall);
    }

    private static float getDownfall(Biome biome) {
        return 0.5f; //TODO: THERE SEEMS TO BE NO WAY TO OBTAIN THIS ?!?
    }

}
