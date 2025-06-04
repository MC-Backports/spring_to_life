package de.larsensmods.stl_backport.forge.event;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.block.STLBlocks;
import de.larsensmods.stl_backport.entity.STLEntityTypes;
import de.larsensmods.stl_backport.entity.client.*;
import de.larsensmods.stl_backport.item.STLItems;
import de.larsensmods.stl_backport.particles.STLParticleTypes;
import de.larsensmods.stl_backport.particles.client.FallingLeavesParticle;
import de.larsensmods.stl_backport.particles.client.FireflyParticle;
import de.larsensmods.stl_backport.util.ClientColorUtils;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.GrassColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SpringToLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(STLEntityTypes.WARM_CHICKEN.get(), WarmChickenRenderer::new);
        EntityRenderers.register(STLEntityTypes.COLD_CHICKEN.get(), ColdChickenRenderer::new);
        EntityRenderers.register(STLEntityTypes.WARM_PIG.get(), WarmPigRenderer::new);
        EntityRenderers.register(STLEntityTypes.COLD_PIG.get(), ColdPigRenderer::new);
        EntityRenderers.register(STLEntityTypes.WARM_COW.get(), WarmCowRenderer::new);
        EntityRenderers.register(STLEntityTypes.COLD_COW.get(), ColdCowRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(STLModelLayers.WARM_CHICKEN, WarmChickenModel::createBodyLayer);
        event.registerLayerDefinition(STLModelLayers.COLD_CHICKEN, ColdChickenModel::createBodyLayer);
        event.registerLayerDefinition(STLModelLayers.WARM_PIG, () -> WarmPigModel.createBodyLayer(CubeDeformation.NONE));
        event.registerLayerDefinition(STLModelLayers.COLD_PIG, ColdPigModel::createBodyLayer);
        event.registerLayerDefinition(STLModelLayers.WARM_COW, WarmCowModel::createBodyLayer);
        event.registerLayerDefinition(STLModelLayers.COLD_COW, ColdCowModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerColorResolvers(RegisterColorHandlersEvent.ColorResolvers event){
        event.register(ClientColorUtils.DRY_FOLIAGE_COLOR_RESOLVER);
    }

    @SubscribeEvent
    public static void registerBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> level != null && pos != null
                        ? SpringToLifeMod.getColorUtils().getAverageDryFoliageColor(level, pos)
                        : -10732494,
                STLBlocks.LEAF_LITTER.get());
        event.register((state, level, pos, tintIndex) -> {
            if(tintIndex != 0){
                return level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor();
            }else{
                return -1;
            }
        }, STLBlocks.WILDFLOWERS.get());
        event.register((state, level, pos, tintIndex) -> level != null && pos != null
                        ? BiomeColors.getAverageGrassColor(level, pos)
                        : GrassColor.getDefaultColor(),
                STLBlocks.BUSH.get());
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> GrassColor.getDefaultColor(),
                STLItems.BUSH.get());
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(STLParticleTypes.TINTED_LEAVES.get(), FallingLeavesParticle.TintedLeavesProvider::new);
        event.registerSpriteSet(STLParticleTypes.FIREFLY.get(), FireflyParticle.FireflyProvider::new);
    }

}
