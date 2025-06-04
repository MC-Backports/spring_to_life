package de.larsensmods.stl_backport.entity;

import de.larsensmods.regutil.IRegistrationProvider;
import de.larsensmods.stl_backport.SpringToLifeMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class STLEntityTypes {

    public static Supplier<EntityType<WarmChicken>> WARM_CHICKEN;
    public static Supplier<EntityType<ColdChicken>> COLD_CHICKEN;

    public static Supplier<EntityType<WarmPig>> WARM_PIG;
    public static Supplier<EntityType<ColdPig>> COLD_PIG;

    public static Supplier<EntityType<WarmCow>> WARM_COW;
    public static Supplier<EntityType<ColdCow>> COLD_COW;

    public static void initEntityTypes(IRegistrationProvider registrationProvider){
        SpringToLifeMod.LOGGER.info("Initializing entity types");

        WARM_CHICKEN = registrationProvider.registerEntityType(
                "warm_chicken",
                () -> EntityType.Builder.of(WarmChicken::new, MobCategory.CREATURE)
                        .sized(0.4F, 0.7F)
                        .clientTrackingRange(10)
        );
        COLD_CHICKEN = registrationProvider.registerEntityType(
                "cold_chicken",
                () -> EntityType.Builder.of(ColdChicken::new, MobCategory.CREATURE)
                        .sized(0.4F, 0.7F)
                        .clientTrackingRange(10)
        );

        WARM_PIG = registrationProvider.registerEntityType(
                "warm_pig",
                () -> EntityType.Builder.of(WarmPig::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10)
        );
        COLD_PIG = registrationProvider.registerEntityType(
                "cold_pig",
                () -> EntityType.Builder.of(ColdPig::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10)
        );

        WARM_COW = registrationProvider.registerEntityType(
                "warm_cow",
                () -> EntityType.Builder.of(WarmCow::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10)
        );
        COLD_COW = registrationProvider.registerEntityType(
                "cold_cow",
                () -> EntityType.Builder.of(ColdCow::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10)
        );

        registrationProvider.finalizeRegistrationStage(IRegistrationProvider.RegistrationStage.ENTITY_TYPES);
    }

}
