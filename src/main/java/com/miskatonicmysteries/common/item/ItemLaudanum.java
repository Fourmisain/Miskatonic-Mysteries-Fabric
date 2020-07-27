package com.miskatonicmysteries.common.item;

import com.miskatonicmysteries.lib.Constants;
import com.miskatonicmysteries.lib.ModRegistries;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ItemLaudanum extends Item {
    public ItemLaudanum() {
        super(new Settings().group(Constants.MM_GROUP).recipeRemainder(Items.GLASS_BOTTLE));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.applyStatusEffect(new StatusEffectInstance(ModRegistries.TRANQUILIZED, 2400, 0));
        user.applyStatusEffect(new StatusEffectInstance(ModRegistries.OVERMEDICATED, 24000, user.getStatusEffect(ModRegistries.OVERMEDICATED) != null ? user.getStatusEffect(ModRegistries.OVERMEDICATED).getAmplifier() + 1 : 0, false, false, false));
        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (user instanceof PlayerEntity && !((PlayerEntity) user).abilities.creativeMode) {
                ItemStack itemStack = new ItemStack(Items.GLASS_BOTTLE);
                PlayerEntity playerEntity = (PlayerEntity) user;
                if (!playerEntity.inventory.insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }

            return stack;
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 30;
    }
}