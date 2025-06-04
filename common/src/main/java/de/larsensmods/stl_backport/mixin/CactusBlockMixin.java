package de.larsensmods.stl_backport.mixin;

import de.larsensmods.stl_backport.block.STLBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin {

    @Shadow @Final public static IntegerProperty AGE;
    @Shadow
    public abstract boolean canSurvive(BlockState state, LevelReader level, BlockPos pos);

    @Inject(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;", shift = At.Shift.AFTER))
    protected void randomTickIf(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        CactusBlock cactus = (CactusBlock)(Object)this;

        if (state.getValue(AGE) == 8 && canSurvive(cactus.defaultBlockState(), level, pos.above())) {
            if (random.nextDouble() <= 0.1) {
                level.setBlockAndUpdate(pos.above(), STLBlocks.CACTUS_FLOWER.get().defaultBlockState());
            }
        }
    }

    @Inject(method = "randomTick", at = @At("RETURN"))
    protected void randomTickTail(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        CactusBlock cactus = (CactusBlock)(Object)this;

        BlockPos blockPos = pos.above();
        if (level.isEmptyBlock(blockPos)) {
            int i = 1;
            int age = state.getValue(AGE);
            while (level.getBlockState(pos.below(i)).is(cactus)) {
                if(++i == 3 && age == 15) {
                    return;
                }
            }

            if(i == 3 && age == 8 && canSurvive(cactus.defaultBlockState(), level, pos.above())) {
                if (random.nextDouble() <= 0.25) {
                    level.setBlockAndUpdate(pos.above(), STLBlocks.CACTUS_FLOWER.get().defaultBlockState());
                }
            }

            if(i == 3 && age < 15){
                level.setBlock(pos, state.setValue(AGE, age + 1), 4);
            }
        }
    }

}
