package me.ddtincoming.jillingots.item.custom;

import me.ddtincoming.jillingots.capabilities.IPlayerData;
import me.ddtincoming.jillingots.capabilities.PlayerDataProvider;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.storage.PlayerData;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.List;

public class Waterstone extends Item {

    public Waterstone(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.jillingots.waterstone_shift"));
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.jillingots.shift"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.getHeldItem(handIn).damageItem(1, playerIn, player -> playerIn.sendBreakAnimation(handIn));
        playerIn.getCooldownTracker().setCooldown(this, 100);
        LazyOptional<IPlayerData> waterstoneActive = playerIn.getCapability(PlayerDataProvider.capability, null);
        waterstoneActive.ifPresent(player -> {player.setInOrb(true);});
        //IPlayerTicks ticks = playerIn.getCapability(TicksProvider.TICKS_CAPABILITY, null);
       // IBubble bubble = playerIn.getCapability(BubbleProvider.BUBBLE_CAPABILITY,` null);
       // ticks.setTicks(0);
        //bubble.setBubble(true);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}

