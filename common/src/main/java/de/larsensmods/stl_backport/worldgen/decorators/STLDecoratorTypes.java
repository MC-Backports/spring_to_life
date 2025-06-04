package de.larsensmods.stl_backport.worldgen.decorators;

import de.larsensmods.regutil.IRegistrationProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.function.Supplier;

public class STLDecoratorTypes {

    public static Supplier<TreeDecoratorType<AttachedToLogDecorator>> ATTACHED_TO_LOG;
    public static Supplier<TreeDecoratorType<PlaceOnGroundDecorator>> PLACE_ON_GROUND;

    public static void initDecoratorTypes(IRegistrationProvider provider) {
        ATTACHED_TO_LOG = provider.registerTreeDecoratorType("attached_to_log", AttachedToLogDecorator.CODEC);
        PLACE_ON_GROUND = provider.registerTreeDecoratorType("place_on_ground", PlaceOnGroundDecorator.CODEC);

        provider.finalizeRegistrationStage(IRegistrationProvider.RegistrationStage.TREE_DECORATOR_TYPES);
    }

}
