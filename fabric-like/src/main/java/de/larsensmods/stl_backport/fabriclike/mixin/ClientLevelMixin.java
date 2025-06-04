package de.larsensmods.stl_backport.fabriclike.mixin;

import de.larsensmods.stl_backport.util.ClientColorUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.Util;
import net.minecraft.client.color.block.BlockTintCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientLevel.class)
public class ClientLevelMixin {

    @Final
    @Shadow
    private Object2ObjectArrayMap<ColorResolver, BlockTintCache> tintCaches = Util.make(
            new Object2ObjectArrayMap<>(4),
            object2ObjectArrayMap -> {
                object2ObjectArrayMap.put(
                        BiomeColors.GRASS_COLOR_RESOLVER, new BlockTintCache(blockPos -> ((ClientLevel) ((Object) this)).calculateBlockTint(blockPos, BiomeColors.GRASS_COLOR_RESOLVER))
                );
                object2ObjectArrayMap.put(
                        BiomeColors.FOLIAGE_COLOR_RESOLVER, new BlockTintCache(blockPos -> ((ClientLevel) ((Object) this)).calculateBlockTint(blockPos, BiomeColors.FOLIAGE_COLOR_RESOLVER))
                );
                object2ObjectArrayMap.put(
                        BiomeColors.WATER_COLOR_RESOLVER, new BlockTintCache(blockPos -> ((ClientLevel) ((Object) this)).calculateBlockTint(blockPos, BiomeColors.WATER_COLOR_RESOLVER))
                );
                object2ObjectArrayMap.put(
                        ClientColorUtils.DRY_FOLIAGE_COLOR_RESOLVER, new BlockTintCache(blockPos -> ((ClientLevel) ((Object) this)).calculateBlockTint(blockPos, ClientColorUtils.DRY_FOLIAGE_COLOR_RESOLVER))
                );
            }
    );

}
