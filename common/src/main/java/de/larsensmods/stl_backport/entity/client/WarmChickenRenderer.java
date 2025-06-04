package de.larsensmods.stl_backport.entity.client;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.entity.WarmChicken;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class WarmChickenRenderer extends MobRenderer<WarmChicken, WarmChickenModel> {

    private static final ResourceLocation CHICKEN_LOCATION = ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "textures/entity/warm_chicken.png");

    public WarmChickenRenderer(EntityRendererProvider.Context context) {
        super(context, new WarmChickenModel(context.bakeLayer(ModelLayers.CHICKEN)), 0.3F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(WarmChicken entity) {
        return CHICKEN_LOCATION;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float getBob(WarmChicken livingBase, float partialTicks) {
        float f = Mth.lerp(partialTicks, livingBase.oFlap, livingBase.flap);
        float g = Mth.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.flapSpeed);
        return (Mth.sin(f) + 1.0F) * g;
    }
}
