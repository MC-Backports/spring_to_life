package de.larsensmods.stl_backport.mixin;

import de.larsensmods.stl_backport.SpringToLifeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Predicate;

@Mixin(EatBlockGoal.class)
public class EatBlockGoalMixin {

    @Shadow
    private static final Predicate<BlockState> IS_TALL_GRASS = state -> state.is(TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "sheep_edible_blocks")));

}
