package de.larsensmods.stl_backport.forge.event;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.entity.*;
import de.larsensmods.stl_backport.item.STLItems;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = SpringToLifeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        ComposterBlock.COMPOSTABLES.put(STLItems.LEAF_LITTER.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(STLItems.WILDFLOWERS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(STLItems.BUSH.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(STLItems.FIREFLY_BUSH.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(STLItems.SHORT_DRY_GRASS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(STLItems.TALL_DRY_GRASS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(STLItems.CACTUS_FLOWER.get(), 0.3F);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(STLEntityTypes.WARM_CHICKEN.get(), WarmChicken.createAttributes().build());
        event.put(STLEntityTypes.COLD_CHICKEN.get(), ColdChicken.createAttributes().build());
        event.put(STLEntityTypes.WARM_PIG.get(), WarmPig.createAttributes().build());
        event.put(STLEntityTypes.COLD_PIG.get(), ColdPig.createAttributes().build());
        event.put(STLEntityTypes.WARM_COW.get(), WarmCow.createAttributes().build());
        event.put(STLEntityTypes.COLD_COW.get(), ColdCow.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event){
        event.register(STLEntityTypes.COLD_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(STLEntityTypes.WARM_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(STLEntityTypes.COLD_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(STLEntityTypes.WARM_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(STLEntityTypes.COLD_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(STLEntityTypes.WARM_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

}
