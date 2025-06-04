package de.larsensmods.stl_backport.entity.client;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.entity.ColdPig;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ColdPigRenderer extends MobRenderer<ColdPig, ColdPigModel> {

    private static final ResourceLocation PIG_LOCATION = ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "textures/entity/cold_pig.png");

    public ColdPigRenderer(EntityRendererProvider.Context context) {
        super(context, new ColdPigModel(context.bakeLayer(STLModelLayers.COLD_PIG)), 0.7F);
        this.addLayer(
                new SaddleLayer<>(
                        this, new ColdPigModel(context.bakeLayer(ModelLayers.PIG_SADDLE)), ResourceLocation.tryBuild(ResourceLocation.DEFAULT_NAMESPACE, "textures/entity/pig/pig_saddle.png")
                )
        );
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(ColdPig entity) {
        return PIG_LOCATION;
    }
}
