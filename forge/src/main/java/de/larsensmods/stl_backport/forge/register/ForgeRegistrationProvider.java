package de.larsensmods.stl_backport.forge.register;

import com.mojang.serialization.Codec;
import de.larsensmods.regutil.IRegistrationProvider;
import de.larsensmods.stl_backport.SpringToLifeMod;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ForgeRegistrationProvider implements IRegistrationProvider {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SpringToLifeMod.MOD_ID);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPE_REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, SpringToLifeMod.MOD_ID);
    private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(Registries.ITEM, SpringToLifeMod.MOD_ID);
    private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(Registries.BLOCK, SpringToLifeMod.MOD_ID);
    private static final DeferredRegister<SoundEvent> SOUND_REGISTER = DeferredRegister.create(Registries.SOUND_EVENT, SpringToLifeMod.MOD_ID);
    private static final DeferredRegister<Feature<?>> FEATURE_REGISTER = DeferredRegister.create(Registries.FEATURE, SpringToLifeMod.MOD_ID);
    private static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_REGISTER = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, SpringToLifeMod.MOD_ID);
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPE_REGISTER = DeferredRegister.create(Registries.PARTICLE_TYPE, SpringToLifeMod.MOD_ID);

    private final Map<String, Object> overrideKeys = new HashMap<>();

    private final IEventBus bus;

    public ForgeRegistrationProvider(IEventBus bus) {
        this.bus = bus;
    }

    public void addOverrideKey(String key, Object value) {
        overrideKeys.put(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Supplier<Block> registerBlock(String key, Function<BlockBehaviour.Properties, Block> constructor, BlockBehaviour.Properties properties) {
        if(overrideKeys.containsKey("block:" + key) && overrideKeys.get("block:" + key) instanceof Function<?, ?> function) {
            SpringToLifeMod.LOGGER.info("Overriding Block {}", key);
            return BLOCK_REGISTER.register(key, () -> ((Function<BlockBehaviour.Properties, Block>) function).apply(properties));
        }
        return BLOCK_REGISTER.register(key, () -> constructor.apply(properties));
    }

    @Override
    public Supplier<CreativeModeTab> registerCreativeTab(String key, Supplier<CreativeModeTab.Builder> tab) {
        return TAB_REGISTER.register(key, tab.get()::build);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntityType(String key, Supplier<EntityType.Builder<T>> entityTypeBuilder) {
        return ENTITY_TYPE_REGISTER.register(key, () -> entityTypeBuilder.get().build(key));
    }

    @Override
    public <T extends FeatureConfiguration> Supplier<Feature<T>> registerFeature(String key, Supplier<Feature<T>> feature) {
        return FEATURE_REGISTER.register(key, feature);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Supplier<Item> registerItem(String key, Supplier<Item> item) {
        if(overrideKeys.containsKey("item:" + key) && overrideKeys.get("item:" + key) instanceof Supplier<?> supplier) {
            SpringToLifeMod.LOGGER.info("Overriding Item {}", key);
            return ITEM_REGISTER.register(key, (Supplier<Item>) supplier);
        }
        return ITEM_REGISTER.register(key, item);
    }

    @Override
    public <T extends ParticleOptions> Supplier<ParticleType<T>> registerParticleType(String key, Supplier<ParticleType<T>> particleType) {
        return PARTICLE_TYPE_REGISTER.register(key, particleType);
    }

    @Override
    public Supplier<SimpleParticleType> registerParticleTypeSimple(String key) {
        return PARTICLE_TYPE_REGISTER.register(key, () -> new SimpleParticleType(false));
    }

    @Override
    public Supplier<SoundEvent> registerSoundEvent(String key, Supplier<SoundEvent> soundEvent) {
        return SOUND_REGISTER.register(key, soundEvent);
    }

    @Override
    public <T extends TreeDecorator> Supplier<TreeDecoratorType<T>> registerTreeDecoratorType(String key, Codec<T> treeDecoratorTypeCodec) {
        return TREE_DECORATOR_REGISTER.register(key, () -> new TreeDecoratorType<>(treeDecoratorTypeCodec));
    }

    @Override
    public void finalizeRegistrationStage(RegistrationStage stage) {
        if (stage.equals(RegistrationStage.CREATIVE_TABS)) {
            TAB_REGISTER.register(bus);
        } else if (stage.equals(RegistrationStage.ENTITY_TYPES)) {
            ENTITY_TYPE_REGISTER.register(bus);
        } else if (stage.equals(RegistrationStage.ITEMS)) {
            ITEM_REGISTER.register(bus);
        } else if (stage.equals(RegistrationStage.BLOCKS)) {
            BLOCK_REGISTER.register(bus);
        } else if (stage.equals(RegistrationStage.SOUNDS)) {
            SOUND_REGISTER.register(bus);
        } else if (stage.equals(RegistrationStage.FEATURES)) {
            FEATURE_REGISTER.register(bus);
        } else if (stage.equals(RegistrationStage.PARTICLE_TYPES)) {
            PARTICLE_TYPE_REGISTER.register(bus);
        } else if (stage.equals(RegistrationStage.TREE_DECORATOR_TYPES)) {
            TREE_DECORATOR_REGISTER.register(bus);
        }
    }
}
