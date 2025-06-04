package de.larsensmods.stl_backport.mixin;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.particles.ColorParticleOption;
import de.larsensmods.stl_backport.particles.STLParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CherryLeavesBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

    @Unique
    private float spring_to_life$leafParticleChance;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void construct(BlockBehaviour.Properties properties, CallbackInfo ci){
        this.spring_to_life$leafParticleChance = 0.01f;
    }

    @Inject(method = "animateTick", at = @At("HEAD"))
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random, CallbackInfo ci){
        LeavesBlock leavesBlock = (LeavesBlock) (Object) this;
        if(!(leavesBlock instanceof CherryLeavesBlock)){
            float f = random.nextFloat();
            if (f < this.spring_to_life$leafParticleChance) {
                ColorParticleOption colorParticleOption = ColorParticleOption.create(STLParticleTypes.TINTED_LEAVES.get(), SpringToLifeMod.getColorUtils().getBlockColor(state, level, pos, 0));
                ParticleUtils.spawnParticleBelow(level, pos, random, colorParticleOption);
            }
        }
    }

}
