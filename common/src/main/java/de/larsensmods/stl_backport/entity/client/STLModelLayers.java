package de.larsensmods.stl_backport.entity.client;

import de.larsensmods.stl_backport.SpringToLifeMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class STLModelLayers {

    public static final ModelLayerLocation WARM_CHICKEN = new ModelLayerLocation(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "warm_chicken"), "main");
    public static final ModelLayerLocation COLD_CHICKEN = new ModelLayerLocation(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "cold_chicken"), "main");

    public static final ModelLayerLocation WARM_PIG = new ModelLayerLocation(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "warm_pig"), "main");
    public static final ModelLayerLocation COLD_PIG = new ModelLayerLocation(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "cold_pig"), "main");

    public static final ModelLayerLocation WARM_COW = new ModelLayerLocation(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "warm_cow"), "main");
    public static final ModelLayerLocation COLD_COW = new ModelLayerLocation(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "cold_cow"), "main");

}
