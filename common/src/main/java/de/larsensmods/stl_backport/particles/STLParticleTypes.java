package de.larsensmods.stl_backport.particles;

import com.mojang.serialization.Codec;
import de.larsensmods.regutil.IRegistrationProvider;
import de.larsensmods.stl_backport.SpringToLifeMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class STLParticleTypes {

    public static Supplier<ParticleType<ColorParticleOption>> TINTED_LEAVES;
    public static Supplier<SimpleParticleType> FIREFLY;

    public static void initParticleTypes(IRegistrationProvider provider){
        SpringToLifeMod.LOGGER.info("Initializing particle types");

        TINTED_LEAVES = provider.registerParticleType("tinted_leaves", () -> new ParticleType<>(false, ColorParticleOption.deserializer()) {
            @Override
            public @NotNull Codec<ColorParticleOption> codec() {
                return ColorParticleOption.codec(this);
            }
        });

        FIREFLY = provider.registerParticleTypeSimple("firefly");

        provider.finalizeRegistrationStage(IRegistrationProvider.RegistrationStage.PARTICLE_TYPES);
    }

}
