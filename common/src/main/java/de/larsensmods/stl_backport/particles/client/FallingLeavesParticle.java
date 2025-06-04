package de.larsensmods.stl_backport.particles.client;

import de.larsensmods.stl_backport.particles.ColorParticleOption;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import org.jetbrains.annotations.NotNull;

public class FallingLeavesParticle extends TextureSheetParticle {

    private final float spinAcceleration;
    private final float windBig;
    private final boolean swirl;
    private final boolean flowAway;
    private final double xaFlowScale;
    private final double zaFlowScale;
    private final double swirlPeriod;

    private float rotSpeed;

    protected FallingLeavesParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites, float gravity, float windBig, boolean swirl, boolean flowAway, float size, float yd) {
        super(level, x, y, z);

        this.setSprite(sprites.get(this.random.nextInt(12), 12));
        this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        float particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float)Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.windBig = windBig;
        this.swirl = swirl;
        this.flowAway = flowAway;
        this.lifetime = 300;
        this.gravity = gravity * 1.2F * 0.0025F;
        float calculatedSize = size * (this.random.nextBoolean() ? 0.05F : 0.075F);
        this.quadSize = calculatedSize;
        this.setSize(calculatedSize, calculatedSize);
        this.friction = 1.0F;
        this.yd = -yd;
        this.xaFlowScale = Math.cos(Math.toRadians(particleRandom * 60.0F)) * (double)this.windBig;
        this.zaFlowScale = Math.sin(Math.toRadians(particleRandom * 60.0F)) * (double)this.windBig;
        this.swirlPeriod = Math.toRadians(1000.0F + particleRandom * 3000.0F);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        }

        if (!this.removed) {
            float baseLifetime = (float)(300 - this.lifetime);
            float movementModifier = Math.min(baseLifetime / 300.0F, 1.0F);
            double xMovement = 0.0;
            double zMovement = 0.0;
            if (this.flowAway) {
                xMovement += this.xaFlowScale * Math.pow(movementModifier, 1.25);
                zMovement += this.zaFlowScale * Math.pow(movementModifier, 1.25);
            }

            if (this.swirl) {
                xMovement += (double)movementModifier * Math.cos((double)movementModifier * this.swirlPeriod) * (double)this.windBig;
                zMovement += (double)movementModifier * Math.sin((double)movementModifier * this.swirlPeriod) * (double)this.windBig;
            }

            this.xd += xMovement * 0.0025F;
            this.zd += zMovement * 0.0025F;
            this.yd -= this.gravity;
            this.rotSpeed += this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll += this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
            if (this.onGround || this.lifetime < 299 && (this.xd == 0.0 || this.zd == 0.0)) {
                this.remove();
            }

            if (!this.removed) {
                this.xd *= this.friction;
                this.yd *= this.friction;
                this.zd *= this.friction;
            }
        }
    }

    public static class TintedLeavesProvider implements ParticleProvider<ColorParticleOption> {
        private final SpriteSet sprites;

        public TintedLeavesProvider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Override
        public Particle createParticle(ColorParticleOption option, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            Particle particle = new FallingLeavesParticle(level, x, y, z, this.sprites, 0.07F, 10.0F, true, false, 2.0F, 0.021F);
            particle.setColor(option.getRed(), option.getGreen(), option.getBlue());
            return particle;
        }
    }
}
