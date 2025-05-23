package de.larsensmods.stl_backport.entity;

import de.larsensmods.stl_backport.item.STLItems;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class ColdChicken extends Chicken {

    private static final Set<ResourceKey<Biome>> COLD_CHICKEN_BIOMES = new HashSet<>();

    public ColdChicken(EntityType<? extends Chicken> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0, WarmChicken.class));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0, Chicken.class));
    }

    @Override
    public void aiStep() {
        boolean layEgg = false;
        if (--this.eggTime <= 0 && !this.level().isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey()) {
            layEgg = true;
            this.eggTime = this.random.nextInt(6000) + 6001;
        }else{
            this.eggTime++;
        }
        super.aiStep();
        if(layEgg){
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(STLItems.BLUE_EGG.get());
            this.gameEvent(GameEvent.ENTITY_PLACE);
        }
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if(otherAnimal == this){
            return false;
        }
        return otherAnimal instanceof Chicken && this.isInLove() && otherAnimal.isInLove();
    }

    @Override
    public @Nullable Chicken getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        if(!(otherParent instanceof Chicken otherChicken)) {
            return null;
        }
        if(otherChicken instanceof WarmChicken) {
            return Math.random() < 0.5 ? STLEntityTypes.COLD_CHICKEN.get().create(level) : STLEntityTypes.WARM_CHICKEN.get().create(level);
        }else if(otherChicken instanceof ColdChicken) {
            return STLEntityTypes.COLD_CHICKEN.get().create(level);
        }else{
            return Math.random() < 0.5 ? STLEntityTypes.COLD_CHICKEN.get().create(level) : EntityType.CHICKEN.create(level);
        }
    }

    public static boolean isValidBiome(Holder<Biome> biome) {
        for (ResourceKey<Biome> key : COLD_CHICKEN_BIOMES) {
            if (biome.is(key)) {
                return true;
            }
        }
        return false;
    }

    static {
        //NATURAL
        COLD_CHICKEN_BIOMES.add(Biomes.TAIGA);
        COLD_CHICKEN_BIOMES.add(Biomes.SNOWY_TAIGA);
        COLD_CHICKEN_BIOMES.add(Biomes.OLD_GROWTH_PINE_TAIGA);
        COLD_CHICKEN_BIOMES.add(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        COLD_CHICKEN_BIOMES.add(Biomes.WINDSWEPT_HILLS);
        COLD_CHICKEN_BIOMES.add(Biomes.WINDSWEPT_GRAVELLY_HILLS);
        COLD_CHICKEN_BIOMES.add(Biomes.WINDSWEPT_FOREST);

        //JOCKEY SPAWNS ONLY
        COLD_CHICKEN_BIOMES.add(Biomes.SNOWY_PLAINS);
        COLD_CHICKEN_BIOMES.add(Biomes.ICE_SPIKES);
        COLD_CHICKEN_BIOMES.add(Biomes.FROZEN_PEAKS);
        COLD_CHICKEN_BIOMES.add(Biomes.JAGGED_PEAKS);
        COLD_CHICKEN_BIOMES.add(Biomes.STONY_PEAKS);
        COLD_CHICKEN_BIOMES.add(Biomes.SNOWY_SLOPES);
        COLD_CHICKEN_BIOMES.add(Biomes.FROZEN_OCEAN);
        COLD_CHICKEN_BIOMES.add(Biomes.DEEP_FROZEN_OCEAN);
        COLD_CHICKEN_BIOMES.add(Biomes.COLD_OCEAN);
        COLD_CHICKEN_BIOMES.add(Biomes.DEEP_COLD_OCEAN);
        COLD_CHICKEN_BIOMES.add(Biomes.GROVE);
        COLD_CHICKEN_BIOMES.add(Biomes.DEEP_DARK);
        COLD_CHICKEN_BIOMES.add(Biomes.FROZEN_RIVER);
        COLD_CHICKEN_BIOMES.add(Biomes.SNOWY_BEACH);

        //THE END
        COLD_CHICKEN_BIOMES.add(Biomes.THE_END);
        COLD_CHICKEN_BIOMES.add(Biomes.END_HIGHLANDS);
        COLD_CHICKEN_BIOMES.add(Biomes.END_MIDLANDS);
        COLD_CHICKEN_BIOMES.add(Biomes.SMALL_END_ISLANDS);
        COLD_CHICKEN_BIOMES.add(Biomes.END_BARRENS);
    }
}
