package de.larsensmods.stl_backport.worldgen;

import de.larsensmods.regutil.IRegistrationProvider;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.function.Supplier;

public class STLFeatures {

    public static Supplier<Feature<FallenTreeFeatureConfig>> FALLEN_TREE;

    public static void initFeatures(IRegistrationProvider provider){
        FALLEN_TREE = provider.registerFeature("fallen_tree", () -> new FallenTreeFeature(FallenTreeFeatureConfig.CODEC));

        provider.finalizeRegistrationStage(IRegistrationProvider.RegistrationStage.FEATURES);
    }

}
