package me.ddtincoming.hexicon.item.custom.spells;

import me.ddtincoming.hexicon.capabilities.BookDataProvider;
import me.ddtincoming.hexicon.capabilities.IBookData;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static me.ddtincoming.hexicon.item.custom.spells.Words.spell;

//Not an item but close to spellbooks
public class SayingSpells {
    @SubscribeEvent
    public void ServerChatEvent(ServerChatEvent event)
    {
        if (!event.getPlayer().getEntityWorld().isRemote)
        {
            if (event.getMessage().equalsIgnoreCase("incantare"))
            {
                if (event.getPlayer().getHeldItemMainhand().getItem() == Items.WRITTEN_BOOK)
                {
                    LazyOptional<IBookData> bookData = event.getPlayer().getHeldItemMainhand().getCapability(BookDataProvider.capability,null);
                    bookData.ifPresent(book -> {
                        if (book.getIsSpellbook())
                        {
                            event.getPlayer().sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                    ("This book is already a spellbook."), true);
                        }
                        else
                        {
                            event.getPlayer().sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                    ("The book has transformed into a spellbook!"), true);
                            book.setIsSpellbook(true);
                            ItemStack Itembook = event.getPlayer().getHeldItemMainhand();
                            Itembook.setDisplayName(ITextComponent.getTextComponentOrEmpty("\u00A7dSpellbook: " + Itembook.getDisplayName().getString()));
                            event.getPlayer().getEntityWorld().playSound(event.getPlayer(), event.getPlayer().getPosition(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.AMBIENT, 1.0F, 1.0F);
                        }
                    });
                }
            }
            spell(event.getMessage(), event.getPlayer(), 1);
        }
    }
}
