package de.larsensmods.stl_backport.fabric.register;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import de.larsensmods.regutil.IRegistrationProvider;
import de.larsensmods.stl_backport.SpringToLifeMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class FabricRegistrationProvider implements IRegistrationProvider {

    private final Map<String, Object> overrideKeys = new HashMap<>();

    public void addOverrideKey(String key, Object value) {
        overrideKeys.put(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Supplier<Block> registerBlock(String key, Function<BlockBehaviour.Properties, Block> constructor, BlockBehaviour.Properties properties) {
        if(overrideKeys.containsKey("block:" + key) && overrideKeys.get("block:" + key) instanceof Function<?, ?> function) {
            SpringToLifeMod.LOGGER.info("Overriding Block {}", key);
            Block regBlock = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, key), ((Function<BlockBehaviour.Properties, Block>) function).apply(properties));
            return () -> regBlock;
        }
        Block regBlock = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, key), constructor.apply(properties));
        return () -> regBlock;
    }

    @Override
    public Supplier<CreativeModeTab> registerCreativeTab(String key, Supplier<CreativeModeTab.Builder> tab) {
        CreativeModeTab creativeTab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, key), tab.get().build());
        return () -> creativeTab;
    }

    public <T extends Entity> Supplier<EntityType<T>> registerEntityType(String key, Supplier<EntityType.Builder<T>> entityTypeBuilder) {
        EntityType<T> entityType = Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, key), entityTypeBuilder.get().build(key));
        return () -> entityType;
    }

    @Override
    public <T extends FeatureConfiguration> Supplier<Feature<T>> registerFeature(String key, Supplier<Feature<T>> feature) {
        Feature<T> regFeature = Registry.register(BuiltInRegistries.FEATURE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, key), feature.get());
        return () -> regFeature;
    }

    public Supplier<Item> registerItem(String key, Supplier<Item> item) {
        Item regItem = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, key), item.get());
        return () -> regItem;
    }

    @Override
    public <T extends ParticleOptions> Supplier<ParticleType<T>> registerParticleType(String key, Supplier<ParticleType<T>> particleType) {
        ParticleType<T> regParticleType = Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, key), particleType.get());
        return () -> regParticleType;
    }

    @Override
    public Supplier<SimpleParticleType> registerParticleTypeSimple(String key) {
        SimpleParticleType simpleParticleType = Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, key), FabricParticleTypes.simple());
        return () -> simpleParticleType;
    }

    @Override
    public Supplier<SoundEvent> registerSoundEvent(String key, Supplier<SoundEvent> soundEvent) {
        SoundEvent regSoundEvent = Registry.register(BuiltInRegistries.SOUND_EVENT, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, key), soundEvent.get());
        return () -> regSoundEvent;
    }

    @Override
    public <T extends TreeDecorator> Supplier<TreeDecoratorType<T>> registerTreeDecoratorType(String key, Codec<T> treeDecoratorTypeCodec) {
        TreeDecoratorType<T> decoratorType = Registry.register(BuiltInRegistries.TREE_DECORATOR_TYPE, ResourceLocation.tryBuild(SpringToLifeMod.MOD_ID, key), new TreeDecoratorType<>(treeDecoratorTypeCodec));
        return () -> decoratorType;
    }

}
