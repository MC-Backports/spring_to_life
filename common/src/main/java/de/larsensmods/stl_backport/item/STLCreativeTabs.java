package de.larsensmods.stl_backport.item;

import de.larsensmods.regutil.IRegistrationProvider;
import de.larsensmods.stl_backport.SpringToLifeMod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class STLCreativeTabs {

    public static Supplier<CreativeModeTab> SPRING_TO_LIFE_TAB;

    public static void initCreativeTabs(IRegistrationProvider provider) {
        SpringToLifeMod.LOGGER.info("Initializing creative tabs");

        SPRING_TO_LIFE_TAB = provider.registerCreativeTab("spring_to_life_tab", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .title(Component.translatable("itemGroup.spring_to_life"))
                .icon(() -> new ItemStack(STLItems.BROWN_EGG.get()))
                .displayItems((displayParameters, output) -> {
                    output.accept(STLItems.WARM_CHICKEN_SPAWN_EGG.get());
                    output.accept(STLItems.COLD_CHICKEN_SPAWN_EGG.get());
                    output.accept(STLItems.WARM_PIG_SPAWN_EGG.get());
                    output.accept(STLItems.COLD_PIG_SPAWN_EGG.get());
                    output.accept(STLItems.WARM_COW_SPAWN_EGG.get());
                    output.accept(STLItems.COLD_COW_SPAWN_EGG.get());

                    output.accept(STLItems.BLUE_EGG.get());
                    output.accept(STLItems.BROWN_EGG.get());

                    output.accept(STLItems.LEAF_LITTER.get());
                    output.accept(STLItems.WILDFLOWERS.get());
                    output.accept(STLItems.BUSH.get());
                    output.accept(STLItems.FIREFLY_BUSH.get());
                    output.accept(STLItems.SHORT_DRY_GRASS.get());
                    output.accept(STLItems.TALL_DRY_GRASS.get());
                    output.accept(STLItems.CACTUS_FLOWER.get());
                }));

        provider.finalizeRegistrationStage(IRegistrationProvider.RegistrationStage.CREATIVE_TABS);
    }

}
