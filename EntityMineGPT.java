package com.minegpt.entity;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIBase;

/** First prototype: named humanoid companion with basic navigation and simple hostile-mob defense. */
public class EntityMineGPT extends EntityCreature {
    private final EntityAIFollowPlayer followPlayerAI;

    public EntityMineGPT(World world) {
        super(world);
        this.setSize(0.6F, 1.8F);
        this.setCustomNameTag("MineGPT");
        this.setAlwaysRenderNameTag(true);
        this.enablePersistence();

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        this.followPlayerAI = new EntityAIFollowPlayer(this);
        this.tasks.addTask(2, followPlayerAI);
        this.tasks.addTask(3, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityMob>(this, EntityMob.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
    }

    @Override
    public boolean attackEntityAsMob(Entity target) {
        return target.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
    }

    @Override
    protected boolean canDespawn() { return false; }

    public static class EntityAIFollowPlayer extends EntityAIBase {
        private final EntityMineGPT bot;
        private EntityPlayer player;

        public EntityAIFollowPlayer(EntityMineGPT bot) {
            this.bot = bot;
            this.setMutexBits(3);
        }

        @Override
        public boolean shouldExecute() {
            if (bot.getAttackTarget() != null) return false;
            player = bot.world.getClosestPlayerToEntity(bot, 12.0D);
            return player != null && bot.getDistanceSq(player) > 9.0D;
        }

        @Override
        public boolean shouldContinueExecuting() {
            return player != null && player.isEntityAlive() && bot.getAttackTarget() == null
                    && bot.getDistanceSq(player) > 4.0D && bot.getDistanceSq(player) < 256.0D;
        }

        @Override
        public void startExecuting() { bot.getNavigator().tryMoveToEntityLiving(player, 1.0D); }

        @Override
        public void updateTask() {
            if (player != null && bot.ticksExisted % 10 == 0) {
                bot.getNavigator().tryMoveToEntityLiving(player, 1.0D);
            }
        }

        @Override
        public void resetTask() {
            player = null;
            bot.getNavigator().clearPath();
        }
    }
}
