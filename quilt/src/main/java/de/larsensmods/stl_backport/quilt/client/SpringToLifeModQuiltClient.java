package de.larsensmods.stl_backport.quilt.client;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.block.STLBlocks;
import de.larsensmods.stl_backport.entity.STLEntityTypes;
import de.larsensmods.stl_backport.entity.client.*;
import de.larsensmods.stl_backport.item.STLItems;
import de.larsensmods.stl_backport.particles.STLParticleTypes;
import de.larsensmods.stl_backport.particles.client.FallingLeavesParticle;
import de.larsensmods.stl_backport.particles.client.FireflyParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.GrassColor;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public final class SpringToLifeModQuiltClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        EntityRendererRegistry.register(STLEntityTypes.WARM_CHICKEN.get(), WarmChickenRenderer::new);
        EntityRendererRegistry.register(STLEntityTypes.COLD_CHICKEN.get(), ColdChickenRenderer::new);
        EntityRendererRegistry.register(STLEntityTypes.WARM_PIG.get(), WarmPigRenderer::new);
        EntityRendererRegistry.register(STLEntityTypes.COLD_PIG.get(), ColdPigRenderer::new);
        EntityRendererRegistry.register(STLEntityTypes.WARM_COW.get(), WarmCowRenderer::new);
        EntityRendererRegistry.register(STLEntityTypes.COLD_COW.get(), ColdCowRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(STLModelLayers.WARM_CHICKEN, WarmChickenModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(STLModelLayers.COLD_CHICKEN, ColdChickenModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(STLModelLayers.WARM_PIG, ()-> WarmPigModel.createBodyLayer(CubeDeformation.NONE));
        EntityModelLayerRegistry.registerModelLayer(STLModelLayers.COLD_PIG, ColdPigModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(STLModelLayers.WARM_COW, WarmCowModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(STLModelLayers.COLD_COW, ColdCowModel::createBodyLayer);

        org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap.put(RenderType.cutout(),
                STLBlocks.LEAF_LITTER.get(),
                STLBlocks.WILDFLOWERS.get(),
                STLBlocks.BUSH.get(),
                STLBlocks.FIREFLY_BUSH.get(),
                STLBlocks.SHORT_DRY_GRASS.get(),
                STLBlocks.TALL_DRY_GRASS.get(),
                STLBlocks.CACTUS_FLOWER.get()
        );

        ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> level != null && pos != null
                        ? SpringToLifeMod.getColorUtils().getAverageDryFoliageColor(level, pos)
                        : -10732494,
                STLBlocks.LEAF_LITTER.get());
        ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> {
            if(tintIndex != 0){
                return level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor();
            }else{
                return -1;
            }
        }, STLBlocks.WILDFLOWERS.get());
        ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> level != null && pos != null
                        ? BiomeColors.getAverageGrassColor(level, pos)
                        : GrassColor.getDefaultColor(),
                STLBlocks.BUSH.get());

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> GrassColor.getDefaultColor(),
                STLItems.BUSH.get());

        ParticleFactoryRegistry.getInstance().register(STLParticleTypes.TINTED_LEAVES.get(), FallingLeavesParticle.TintedLeavesProvider::new);
        ParticleFactoryRegistry.getInstance().register(STLParticleTypes.FIREFLY.get(), FireflyParticle.FireflyProvider::new);
    }
}
