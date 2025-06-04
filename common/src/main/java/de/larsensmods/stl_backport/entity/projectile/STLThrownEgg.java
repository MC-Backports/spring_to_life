package de.larsensmods.stl_backport.entity.projectile;

import de.larsensmods.stl_backport.entity.STLEntityTypes;
import de.larsensmods.stl_backport.item.STLItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class STLThrownEgg extends ThrowableItemProjectile {

    public STLThrownEgg(EntityType<? extends STLThrownEgg> entityType, Level level) {
        super(entityType, level);
    }

    public STLThrownEgg(Level level, LivingEntity shooter) {
        super(EntityType.EGG, shooter, level);
    }

    public STLThrownEgg(Level level, double x, double y, double z) {
        super(EntityType.EGG, x, y, z, level);
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            double d = 0.08;

            for (int i = 0; i < 8; i++) {
                this.level()
                        .addParticle(
                                new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                ((double)this.random.nextFloat() - 0.5) * 0.08,
                                ((double)this.random.nextFloat() - 0.5) * 0.08,
                                ((double)this.random.nextFloat() - 0.5) * 0.08
                        );
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            if (this.random.nextInt(8) == 0) {
                int i = 1;
                if (this.random.nextInt(32) == 0) {
                    i = 4;
                }

                for (int j = 0; j < i; j++) {
                    Chicken chicken = getChickenType().create(this.level());
                    if (chicken != null) {
                        chicken.setAge(-24000);
                        chicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                        this.level().addFreshEntity(chicken);
                    }
                }
            }

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    private EntityType<? extends Chicken> getChickenType() {
        if(this.getItem().is(STLItems.BLUE_EGG.get())){
            return STLEntityTypes.COLD_CHICKEN.get();
        } else if(this.getItem().is(STLItems.BROWN_EGG.get())){
            return STLEntityTypes.WARM_CHICKEN.get();
        }else {
            return EntityType.CHICKEN;
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.EGG;
    }

}
