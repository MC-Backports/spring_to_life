package de.larsensmods.stl_backport.entity.client;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.entity.ColdCow;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ColdCowRenderer extends MobRenderer<ColdCow, ColdCowModel> {

    private static final ResourceLocation COW_LOCATION = ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "textures/entity/cold_cow.png");

    public ColdCowRenderer(EntityRendererProvider.Context context) {
        super(context, new ColdCowModel(context.bakeLayer(STLModelLayers.COLD_COW)), 0.7F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(ColdCow entity) {
        return COW_LOCATION;
    }

}
