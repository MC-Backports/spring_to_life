package de.larsensmods.stl_backport.entity.client;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.entity.ColdChicken;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class ColdChickenRenderer extends MobRenderer<ColdChicken, ColdChickenModel> {

    private static final ResourceLocation CHICKEN_LOCATION = ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "textures/entity/cold_chicken.png");

    public ColdChickenRenderer(EntityRendererProvider.Context context) {
        super(context, new ColdChickenModel(context.bakeLayer(STLModelLayers.COLD_CHICKEN)), 0.3F);
    }

    /**
     * Returns the location of an entity's texture.
     */
    public @NotNull ResourceLocation getTextureLocation(ColdChicken entity) {
        return CHICKEN_LOCATION;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float getBob(ColdChicken livingBase, float partialTicks) {
        float f = Mth.lerp(partialTicks, livingBase.oFlap, livingBase.flap);
        float g = Mth.lerp(partialTicks, livingBase.oFlapSpeed, livingBase.flapSpeed);
        return (Mth.sin(f) + 1.0F) * g;
    }
}
