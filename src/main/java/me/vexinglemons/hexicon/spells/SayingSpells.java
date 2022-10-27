package me.vexinglemons.hexicon.spells;

import me.vexinglemons.hexicon.capabilities.BookData.BookDataProvider;
import me.vexinglemons.hexicon.capabilities.BookData.IBookData;
import me.vexinglemons.hexicon.capabilities.PlayerData.IPlayerData;
import me.vexinglemons.hexicon.capabilities.PlayerData.PlayerDataProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static me.vexinglemons.hexicon.spells.Words.spell;

//Not an item but close to spellbooks
public class SayingSpells {
    @SubscribeEvent
    public void ServerChatEvent(ServerChatEvent event) {
        if (!event.getPlayer().getEntityWorld().isRemote) {
            if (event.getMessage().equalsIgnoreCase("incantare")) {
                if (event.getPlayer().getHeldItemMainhand().getItem() == Items.WRITTEN_BOOK) {
                    transformSpellbook(event.getPlayer());
                }
            } else if (event.getMessage().equalsIgnoreCase("potentia")) {
                LazyOptional<IPlayerData> playerData = event.getPlayer().getCapability(PlayerDataProvider.capability, null);
                playerData.ifPresent(player -> {
                            event.getPlayer().sendStatusMessage(new StringTextComponent("\u00A7dYou have \u00A7l" + player.getMana() + "\u00A7r\u00A7d mana."), true);
                        }
                );

            }
            spell(event.getMessage(), event.getPlayer(), 1);
        }
    }
    public void transformSpellbook(PlayerEntity player)
    {
        LazyOptional<IPlayerData> mana = player.getCapability(PlayerDataProvider.capability, null);
        if (mana.isPresent())
        {
            IPlayerData resolved = mana.resolve().get();
            if (resolved.getMana() < 50)
            {
                player.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        ("You need 50 mana to make a spellbook!"), true);
                return;
            }
            resolved.removeMana(50);
        } else {return;}

        LazyOptional<IBookData> bookData = player.getHeldItemMainhand().getCapability(BookDataProvider.capability, null);
        bookData.ifPresent(book -> {
            if (book.getIsSpellbook()) {
                player.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        ("This book is already a spellbook."), true);
            } else {
                player.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        ("The book has transformed into a spellbook!"), true);
                book.setIsSpellbook(true);
                ItemStack Itembook = player.getHeldItemMainhand();
                Itembook.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u00A7dSpellbook: " + Itembook.getDisplayName().getString()));
                player.getEntityWorld().playSound(player, player.getPosition(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.AMBIENT, 1.0F, 1.0F);
            }
        });
    }
}
