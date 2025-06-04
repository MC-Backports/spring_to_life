package de.larsensmods.stl_backport.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class ColorParticleOption implements ParticleOptions {

    private final ParticleType<ColorParticleOption> type;
    private final Vector3f color;

    public ColorParticleOption(ParticleType<ColorParticleOption> type, Vector3f color) {
        this.type = type;
        this.color = color;
    }

    public float getRed() {
        return this.color.x;
    }

    public float getGreen() {
        return this.color.y;
    }

    public float getBlue() {
        return this.color.z;
    }

    @Override
    public @NotNull ParticleType<ColorParticleOption> getType() {
        return this.type;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.color.x);
        buffer.writeFloat(this.color.y);
        buffer.writeFloat(this.color.z);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("ColorParticleOption{type=%s, color=%s}", this.type, this.color);
    }

    public static ColorParticleOption create(ParticleType<ColorParticleOption> type, int color) {
        return new ColorParticleOption(type, new Vector3f(
                FastColor.ARGB32.red(color) / 255.0f,
                FastColor.ARGB32.green(color) / 255.0f,
                FastColor.ARGB32.blue(color) / 255.0f
        ));
    }

    public static ColorParticleOption create(ParticleType<ColorParticleOption> type, float red, float green, float blue) {
        return new ColorParticleOption(type, new Vector3f(red, green, blue));
    }

    public static Codec<ColorParticleOption> codec(ParticleType<ColorParticleOption> type) {
        return RecordCodecBuilder.create((instance) -> instance.group(ExtraCodecs.VECTOR3F.fieldOf("color").forGetter((dustParticleOptions) -> dustParticleOptions.color)).apply(instance, vector3f -> new ColorParticleOption(type, vector3f)));
    }

    public static ParticleOptions.Deserializer<ColorParticleOption> deserializer() {
        return new Deserializer<>() {
            @Override
            public @NotNull ColorParticleOption fromCommand(ParticleType<ColorParticleOption> particleType, StringReader reader) throws CommandSyntaxException {
                Vector3f vector3f = readVector3f(reader);
                return new ColorParticleOption(particleType, vector3f);
            }

            @Override
            public @NotNull ColorParticleOption fromNetwork(ParticleType<ColorParticleOption> particleType, FriendlyByteBuf buffer) {
                return new ColorParticleOption(particleType, readVector3f(buffer));
            }

            private Vector3f readVector3f(StringReader reader) throws CommandSyntaxException {
                float x = reader.readFloat();
                reader.expect(' ');
                float y = reader.readFloat();
                reader.expect(' ');
                float z = reader.readFloat();
                return new Vector3f(x, y, z);
            }

            private Vector3f readVector3f(FriendlyByteBuf buffer) {
                float x = buffer.readFloat();
                float y = buffer.readFloat();
                float z = buffer.readFloat();
                return new Vector3f(x, y, z);
            }
        };
    }
}
