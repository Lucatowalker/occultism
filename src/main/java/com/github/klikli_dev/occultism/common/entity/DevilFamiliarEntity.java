/*
 * MIT License
 *
 * Copyright 2021 vemerion
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.klikli_dev.occultism.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.github.klikli_dev.occultism.common.advancement.FamiliarTrigger;
import com.github.klikli_dev.occultism.registry.OccultismAdvancements;
import com.google.common.collect.ImmutableList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowMobGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

public class DevilFamiliarEntity extends FamiliarEntity {

    private static final DataParameter<Boolean> LOLLIPOP = EntityDataManager.createKey(DevilFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> NOSE = EntityDataManager.createKey(DevilFamiliarEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EARS = EntityDataManager.createKey(DevilFamiliarEntity.class,
            DataSerializers.BOOLEAN);

    private final float heightOffset;

    public DevilFamiliarEntity(EntityType<? extends DevilFamiliarEntity> type, World worldIn) {
        super(type, worldIn);
        this.heightOffset = this.getRNG().nextFloat() * 5;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return FamiliarEntity.registerAttributes().createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
            ILivingEntityData spawnDataIn, CompoundNBT dataTag) {
        this.setLollipop(this.getRNG().nextDouble() < 0.1);
        this.setNose(this.getRNG().nextDouble() < 0.5);
        this.setEars(this.getRNG().nextDouble() < 0.5);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1, 3, 1));
        this.goalSelector.addGoal(4, new AttackGoal(this));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new FollowMobGoal(this, 1, 3, 7));
    }
    
    @Override
    public void setFamiliarOwner(LivingEntity owner) {
        if (hasLollipop())
            OccultismAdvancements.FAMILIAR.trigger(owner, FamiliarTrigger.Type.RARE_VARIANT);
        super.setFamiliarOwner(owner);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isServerWorld() && this.isSwingInProgress) {
            Vector3d direction = Vector3d.fromPitchYaw(this.getPitchYaw()).scale(0.6);

            for (int i = 0; i < 5; i++) {
                Vector3d pos = this.getPositionVec().add(direction.x + (this.getRNG().nextFloat() - 0.5f) * 0.7,
                        1.5 + (this.getRNG().nextFloat() - 0.5f) * 0.7, direction.z + (this.getRNG().nextFloat() - 0.5f) * 0.7);
                this.world.addParticle(ParticleTypes.FLAME, pos.x, pos.y, pos.z, direction.x * 0.25, 0, direction.z * 0.25);
            }
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(LOLLIPOP, false);
        this.dataManager.register(NOSE, false);
        this.dataManager.register(EARS, false);
    }

    public float getAnimationHeight(float partialTicks) {
        return MathHelper.cos((this.ticksExisted + this.heightOffset + partialTicks) / 3.5f);
    }

    public boolean hasLollipop() {
        return this.dataManager.get(LOLLIPOP);
    }

    private void setLollipop(boolean b) {
        this.dataManager.set(LOLLIPOP, b);
    }

    public boolean hasNose() {
        return this.dataManager.get(NOSE);
    }

    private void setNose(boolean b) {
        this.dataManager.set(NOSE, b);
    }

    public boolean hasEars() {
        return this.dataManager.get(EARS);
    }

    private void setEars(boolean b) {
        this.dataManager.set(EARS, b);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setLollipop(compound.getBoolean("hasLollipop"));
        this.setNose(compound.getBoolean("hasNose"));
        this.setEars(compound.getBoolean("hasEars"));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("hasLollipop", this.hasLollipop());
        compound.putBoolean("hasNose", this.hasNose());
        compound.putBoolean("hasEars", this.hasEars());
    }

    @Override
    public Iterable<EffectInstance> getFamiliarEffects() {
        if (this.isEffectEnabled()) {
                return ImmutableList.of(new EffectInstance(Effects.FIRE_RESISTANCE, 300, 0, false, false));
        }
        return ImmutableList.of();
    }

    public static class AttackGoal extends Goal {

        private static final int MAX_COOLDOWN = 20 * 5;

        private final FamiliarEntity entity;
        private int cooldown = MAX_COOLDOWN;

        public AttackGoal(FamiliarEntity entity) {
            this.entity = entity;
        }

        @Override
        public boolean shouldExecute() {
            return this.cooldown-- < 0 && this.entity.getFamiliarOwner() instanceof PlayerEntity && !this.getNearbyEnemies().isEmpty();
        }

        private List<Entity> getNearbyEnemies() {
            LivingEntity owner = this.entity.getFamiliarOwner();
            LivingEntity revenge = owner.getRevengeTarget();
            LivingEntity target = owner.getLastAttackedEntity();
            List<Entity> enemies = new ArrayList<>();
            if (this.isClose(revenge))
                enemies.add(revenge);
            if (this.isClose(target))
                enemies.add(target);
            return enemies;
        }

        private boolean isClose(LivingEntity e) {
            return e != null && e.getDistanceSq(this.entity) < 5;
        }

        public void startExecuting() {
            List<Entity> enemies = this.getNearbyEnemies();
            if (!enemies.isEmpty())
                OccultismAdvancements.FAMILIAR.trigger(this.entity.getFamiliarOwner(), FamiliarTrigger.Type.DEVIL_FIRE);
            for (Entity e : enemies) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) this.entity.getFamiliarOwner()), 4);
            }
            this.cooldown = MAX_COOLDOWN;
            this.entity.swingArm(Hand.MAIN_HAND);
        }

        public void resetTask() {
            this.cooldown = MAX_COOLDOWN;
        }
    }
}
