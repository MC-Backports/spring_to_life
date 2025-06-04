package de.larsensmods.stl_backport.quilt;

import de.larsensmods.stl_backport.SpringToLifeMod;
import de.larsensmods.stl_backport.block.STLBlocks;
import de.larsensmods.stl_backport.entity.ColdChicken;
import de.larsensmods.stl_backport.entity.STLEntityTypes;
import de.larsensmods.stl_backport.entity.WarmChicken;
import de.larsensmods.stl_backport.entity.WarmPig;
import de.larsensmods.stl_backport.item.STLItems;
import de.larsensmods.stl_backport.quilt.block.STLLeafLitterBlockQuilt;
import de.larsensmods.stl_backport.quilt.register.QuiltRegistrationProvider;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import de.larsensmods.stl_backport.fabriclike.SpringToLifeModFabricLike;
import org.quiltmc.qsl.block.content.registry.api.BlockContentRegistries;
import org.quiltmc.qsl.block.content.registry.api.FlammableBlockEntry;
import org.quiltmc.qsl.item.content.registry.api.ItemContentRegistries;
import org.quiltmc.qsl.villager.api.TradeOfferHelper;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectors;
import org.quiltmc.qsl.worldgen.biome.api.ModificationPhase;

import java.util.Set;
import java.util.function.Function;

public final class SpringToLifeModQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        QuiltRegistrationProvider registrationProvider = new QuiltRegistrationProvider();

        //Register override keys
        registrationProvider.addOverrideKey("block:leaf_litter", (Function<BlockBehaviour.Properties, Block>) STLLeafLitterBlockQuilt::new);

        // Run our common setup.
        SpringToLifeModFabricLike.init(registrationProvider);

        DefaultAttributes.SUPPLIERS.put(STLEntityTypes.WARM_CHICKEN.get(), WarmChicken.createAttributes().build());
        DefaultAttributes.SUPPLIERS.put(STLEntityTypes.COLD_CHICKEN.get(), ColdChicken.createAttributes().build());
        DefaultAttributes.SUPPLIERS.put(STLEntityTypes.WARM_PIG.get(), WarmPig.createAttributes().build());
        DefaultAttributes.SUPPLIERS.put(STLEntityTypes.COLD_PIG.get(), WarmPig.createAttributes().build());
        DefaultAttributes.SUPPLIERS.put(STLEntityTypes.WARM_COW.get(), WarmPig.createAttributes().build());
        DefaultAttributes.SUPPLIERS.put(STLEntityTypes.COLD_COW.get(), WarmPig.createAttributes().build());

        SpawnPlacements.register(STLEntityTypes.COLD_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(STLEntityTypes.WARM_CHICKEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(STLEntityTypes.COLD_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(STLEntityTypes.WARM_PIG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(STLEntityTypes.COLD_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(STLEntityTypes.WARM_COW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

        BlockContentRegistries.FLAMMABLE.put(STLBlocks.LEAF_LITTER.get(), new FlammableBlockEntry(60, 100));
        BlockContentRegistries.FLAMMABLE.put(STLBlocks.WILDFLOWERS.get(), new FlammableBlockEntry(60, 100));
        BlockContentRegistries.FLAMMABLE.put(STLBlocks.BUSH.get(), new FlammableBlockEntry(60, 100));
        BlockContentRegistries.FLAMMABLE.put(STLBlocks.FIREFLY_BUSH.get(), new FlammableBlockEntry(60, 100));
        BlockContentRegistries.FLAMMABLE.put(STLBlocks.SHORT_DRY_GRASS.get(), new FlammableBlockEntry(60, 100));
        BlockContentRegistries.FLAMMABLE.put(STLBlocks.TALL_DRY_GRASS.get(), new FlammableBlockEntry(60, 100));
        BlockContentRegistries.FLAMMABLE.put(STLBlocks.CACTUS_FLOWER.get(), new FlammableBlockEntry(60, 100));

        ItemContentRegistries.COMPOST_CHANCES.put(STLItems.LEAF_LITTER.get(), 0.3f);
        ItemContentRegistries.COMPOST_CHANCES.put(STLItems.WILDFLOWERS.get(), 0.3f);
        ItemContentRegistries.COMPOST_CHANCES.put(STLItems.BUSH.get(), 0.3f);
        ItemContentRegistries.COMPOST_CHANCES.put(STLItems.FIREFLY_BUSH.get(), 0.3f);
        ItemContentRegistries.COMPOST_CHANCES.put(STLItems.SHORT_DRY_GRASS.get(), 0.3f);
        ItemContentRegistries.COMPOST_CHANCES.put(STLItems.TALL_DRY_GRASS.get(), 0.3f);
        ItemContentRegistries.COMPOST_CHANCES.put(STLItems.CACTUS_FLOWER.get(), 0.3f);

        ItemContentRegistries.FUEL_TIMES.put(STLItems.LEAF_LITTER.get(), 5 * 20);
        ItemContentRegistries.FUEL_TIMES.put(STLItems.SHORT_DRY_GRASS.get(), 5 * 20);
        ItemContentRegistries.FUEL_TIMES.put(STLItems.TALL_DRY_GRASS.get(), 5 * 20);

        VillagerTrades.ItemListing dryGrassTrade = (trader, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(STLBlocks.TALL_DRY_GRASS.get()), 12, 0, 0);
        VillagerTrades.ItemListing wildflowersTrade = (trader, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(STLBlocks.WILDFLOWERS.get()), 12, 0, 0);
        VillagerTrades.ItemListing fireflyBushTrade = (trader, random) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(STLBlocks.FIREFLY_BUSH.get()), 12, 0, 0);

        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.addAll(Set.of(dryGrassTrade, wildflowersTrade)));
        TradeOfferHelper.registerWanderingTraderOffers(2, factories -> factories.add(fireflyBushTrade));

        this.applyBiomeModifications();
        this.applyLootTableModifications();
    }

    private void applyBiomeModifications() {
        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "add_leaf_litter_patches"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.DARK_FOREST),
                        context -> context.getGenerationSettings()
                                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "patch_leaf_litter"))));

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "add_wildflowers"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
                        context -> context.getGenerationSettings()
                                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "wildflowers_birch_forest"))))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.MEADOW),
                        context -> context.getGenerationSettings()
                                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "wildflowers_meadow"))));

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "replace_forest_tree_feature"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.FOREST),
                        context -> {
                            context.getGenerationSettings().removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_BIRCH_AND_OAK);
                            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "trees_birch_and_oak_leaf_litter")));
                        });

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "add_bushes"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.isIn(TagKey.create(Registries.BIOME, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "bush_biomes"))),
                        context -> context.getGenerationSettings()
                                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "patch_bush"))));

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "add_firefly_bushes_default"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.isIn(TagKey.create(Registries.BIOME, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "default_extra_vegetation_biomes"))),
                        context -> context.getGenerationSettings()
                                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "patch_firefly_bush_water"))));

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "add_firefly_bushes_swamp"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.SWAMP),
                        context -> {
                            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "patch_firefly_bush_swamp")));
                            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "patch_firefly_bush_swamp_water")));
                        });

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "add_firefly_bushes_badlands"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.isIn(BiomeTags.IS_BADLANDS),
                        context -> context.getGenerationSettings()
                                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "patch_firefly_bush_water"))));

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "add_firefly_bushes_mangrove_swamp"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.MANGROVE_SWAMP),
                        context -> context.getGenerationSettings()
                                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "patch_firefly_bush_water"))));

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "replace_warm_animals"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.isIn(TagKey.create(Registries.BIOME, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "warm_animal_biomes"))),
                        context -> {
                            context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW);
                            context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.PIG);
                            context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.CHICKEN);
                            context.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(STLEntityTypes.WARM_PIG.get(), 10, 4, 4));
                            context.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(STLEntityTypes.WARM_CHICKEN.get(), 10, 4, 4));
                            context.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(STLEntityTypes.WARM_COW.get(), 8, 4, 4));
                        })
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.SPARSE_JUNGLE),
                        context -> context.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(STLEntityTypes.WARM_CHICKEN.get(), 10, 4, 4)));

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "replace_cold_animals"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.isIn(TagKey.create(Registries.BIOME, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "cold_animal_biomes"))),
                        context -> {
                            context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.COW);
                            context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.PIG);
                            context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.CHICKEN);
                            context.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(STLEntityTypes.COLD_PIG.get(), 10, 4, 4));
                            context.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(STLEntityTypes.COLD_CHICKEN.get(), 10, 4, 4));
                            context.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(STLEntityTypes.COLD_COW.get(), 8, 4, 4));
                        });

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "camel_desert_spawns"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.DESERT),
                        context -> context.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CAMEL, 1, 1, 1)));

        BiomeModifications.create(ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "add_dry_grass"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.isIn(BiomeTags.IS_BADLANDS),
                        context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "patch_dry_grass_badlands"))))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.includeByKey(Biomes.DESERT),
                        context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, "patch_dry_grass_desert"))));
    }

    private void applyLootTableModifications(){
        LootTableEvents.MODIFY.register((resourceManager, lootDataManager, resourceLocation, builder, lootTableSource) -> {
            if (BuiltInLootTables.RUINED_PORTAL.equals(resourceLocation)) {
                LootPool.Builder newPool = LootPool.lootPool()
                        .with(EmptyLootItem.emptyItem().build())
                        .with(LootItem.lootTableItem(Items.LODESTONE)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 2.0f)))
                                .setWeight(2)
                                .build()
                        );

                builder.pool(newPool.build());
            }
        });
    }
}
