package me.ddtincoming.jillingots.item.custom.spells;


import me.ddtincoming.jillingots.capabilities.BookDataProvider;
import me.ddtincoming.jillingots.capabilities.IBookData;
import net.minecraft.item.*;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static me.ddtincoming.jillingots.item.custom.spells.Words.spell;

public class Spellbook // This will be its own item soon but for now i'm using a book to get all the logic down
{
    @SubscribeEvent
    public void onRightclickBook(PlayerInteractEvent.RightClickItem event) {
        if (!event.getWorld().isRemote)
        {
            LazyOptional<IBookData> bookData = event.getItemStack().getCapability(BookDataProvider.capability,null);
            bookData.ifPresent(book -> {
                if (book.getIsSpellbook())
                {
                    if (!event.getPlayer().isSneaking())
                    {
                        event.setCanceled(true);
                        if (event.getItemStack().getItem() == Items.WRITTEN_BOOK) {

                            ItemStack item = event.getItemStack();
                            ListNBT nbt = (ListNBT) item.getTag().get("pages");
                            String text = nbt.get(0).getString();
                            text = text.replace("{\"text\":\"", "");
                            text = text.replace("\"}", "");
                            text = text.replace("\\n", " hithisisaplaceholdermethodfordoingthisbecauseidkhowpleasetellmehowtodothisindiscord ");
                            String[] lines = text.split(" hithisisaplaceholdermethodfordoingthisbecauseidkhowpleasetellmehowtodothisindiscord ");
                            int lineNum = 1;
                            event.getPlayer().getCooldownTracker().setCooldown(event.getItemStack().getItem(), 5 * lines.length);
                            for (String line : lines)
                            {
                                spell(line, event.getPlayer(), lineNum);
                                lineNum++;
                            }
                        }
                    }
                }
            });
        }
    }
}
