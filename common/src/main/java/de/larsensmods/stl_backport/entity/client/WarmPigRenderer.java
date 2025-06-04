package de.larsensmods.stl_backport.entity.client;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.entity.WarmPig;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WarmPigRenderer extends MobRenderer<WarmPig, WarmPigModel> {

    private static final ResourceLocation PIG_LOCATION = ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "textures/entity/warm_pig.png");

    public WarmPigRenderer(EntityRendererProvider.Context context) {
        super(context, new WarmPigModel(context.bakeLayer(STLModelLayers.WARM_PIG)), 0.7F);
        this.addLayer(
                new SaddleLayer<>(
                        this, new WarmPigModel(context.bakeLayer(ModelLayers.PIG_SADDLE)), ResourceLocation.tryBuild(ResourceLocation.DEFAULT_NAMESPACE, "textures/entity/pig/pig_saddle.png")
                )
        );
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(WarmPig entity) {
        return PIG_LOCATION;
    }
}
