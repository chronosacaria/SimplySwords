package net.sweenus.simplyswords.item.custom;


import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sweenus.simplyswords.config.SimplySwordsConfig;

import java.util.List;

public class EmberIreSwordItem extends SwordItem {
    public EmberIreSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int fhitchance = SimplySwordsConfig.getIntValue("ember_ire_chance");
        int fduration = SimplySwordsConfig.getIntValue("ember_ire_duration");


        if (attacker.getRandom().nextInt(100) <= fhitchance) {
            attacker.setOnFireFor(fduration / 20);
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, fduration, 2), attacker);
        }

        return super.postHit(stack, target, attacker);

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!user.world.isClient()) {
            if (user.hasStatusEffect(StatusEffects.STRENGTH)) {

                ServerWorld sworld = (ServerWorld)user.world;
                BlockPos position = (user.getBlockPos());
                Vec3d rotation = user.getRotationVec(1f);
                Vec3d newPos = user.getPos().add(rotation);

                //Entity fireball = EntityType.FIREBALL.spawn((ServerWorld) world, null, null, null, newPos, SpawnReason.TRIGGERED, true, true);
                Entity fireball = new FireballEntity(EntityType.FIREBALL, (ServerWorld) world);
                fireball.updatePosition(newPos.getX(), user.getY() + 2, newPos.getZ());
                sworld.spawnEntity(fireball);
                fireball.setVelocity(user.getX(), 0.0, user.getZ());
                user.removeStatusEffect(StatusEffects.STRENGTH);
            }
        }
        return super.use(world,user,hand);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        // default white text
        tooltip.add(Text.translatable("item.simplyswords.emberiresworditem.tooltip1").formatted(Formatting.GOLD));

        // formatted red text
        tooltip.add(Text.translatable("item.simplyswords.emberiresworditem.tooltip2"));
    }

}
