package de.larsensmods.stl_backport.util;

import de.larsensmods.stl_backport.SpringToLifeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.LegacyStuffWrapper;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;

public class DryFoliageColor {

    private static int[] pixels = new int[65536];
    private static boolean hasInitialized = false;

    public static int get(double tempParam, double downfallParam) {
        return get(tempParam, downfallParam, pixels, -10732494);
    }

    private static int get(double tempParam, double downfallParam, int[] pixelValues, int defaultColor) {
        if(!hasInitialized){
            try {
                //noinspection deprecation
                pixels = LegacyStuffWrapper.getPixels(Minecraft.getInstance().getResourceManager(), ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "textures/colormap/dry_foliage.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            hasInitialized = true;
        }

        downfallParam *= tempParam;
        int var0 = (int)((1.0 - tempParam) * 255.0);
        int var1 = (int)((1.0 - downfallParam) * 255.0);
        int var2 = var1 << 8 | var0;
        return var2 >= pixelValues.length ? defaultColor : pixelValues[var2];
    }

}
