package de.larsensmods.stl_backport.entity.client;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.entity.WarmCow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WarmCowRenderer extends MobRenderer<WarmCow, WarmCowModel> {

    private static final ResourceLocation COW_LOCATION = ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "textures/entity/warm_cow.png");

    public WarmCowRenderer(EntityRendererProvider.Context context) {
        super(context, new WarmCowModel(context.bakeLayer(STLModelLayers.WARM_COW)), 0.7F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(WarmCow entity) {
        return COW_LOCATION;
    }

}
