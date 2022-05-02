package me.vexinglemons.hexicon.item.custom.spells;


import me.vexinglemons.hexicon.capabilities.BookData.BookDataProvider;
import me.vexinglemons.hexicon.capabilities.BookData.IBookData;
import me.vexinglemons.hexicon.capabilities.PlayerData.IPlayerData;
import me.vexinglemons.hexicon.capabilities.PlayerData.PlayerDataProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraftforge.eventbus.api.SubscribeEvent;

import static java.lang.Math.round;
import static me.vexinglemons.hexicon.item.custom.spells.Words.spell;

public class Spellbook
{
    public static float waitSpell(String word) // This wouldn't work with the normal word stuff because each number would have to be a separate noun
    {
        float asInteger = Float.parseFloat(word);
        if (asInteger > 0)
        {
            return asInteger;
        }
        return 0;
    }

    public void doSpellsInBook(PlayerEntity player, String text)
    {
        text = text.replace("{\"text\":\"", "");
        text = text.replace("\"}", "");
        text = text.replace("\\n", " hithisisprettyhackysolutionpleasetellmehowtodothisindiscord ");
        String[] lines = text.split(" hithisisprettyhackysolutionpleasetellmehowtodothisindiscord ");
        for (int lineNum = 0; lineNum < lines.length; lineNum++)
        {
            String[] words = lines[lineNum].split(" ");
            if (words[0].equals("manere"))
            {
                final float time = waitSpell(words[1]);;
                String combinedString = "";
                for (int i = lineNum + 1; i < lines.length; i++)
                {
                    combinedString += "\\n" + lines[i];
                }
                final String combined = combinedString;
                LazyOptional<IPlayerData> playerData = player.getCapability(PlayerDataProvider.capability, null);
                playerData.ifPresent((playerinst) ->
                {
                    playerinst.setTicks(round(time * 40));
                    playerinst.setSpellString(combined);
                });
                break;
            }
            spell(lines[lineNum], player, lineNum + 1);
        }
    }

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
                        if (event.getItemStack().getItem() == Items.WRITTEN_BOOK)
                        {
                            event.getPlayer().getCooldownTracker().setCooldown(event.getItemStack().getItem(), 20);
                            ItemStack item = event.getItemStack();
                            ListNBT nbt = (ListNBT) item.getTag().get("pages");
                            String text = nbt.get(0).getString();
                            doSpellsInBook(event.getPlayer(), text);
                        }
                    }
                }
            });
        }
    }

    @SubscribeEvent
    public void spellbookTick(TickEvent.PlayerTickEvent event) // I know this will only allow 1 ongoing spell at a time with the wait word, that's intentional, I might change it later though if I make spellbooks their own item that are hard to get.
    {
        if (event.player.getEntityWorld().isRemote)
        {
            return;
        }
        LazyOptional<IPlayerData> playerData = event.player.getCapability(PlayerDataProvider.capability, null);
        playerData.ifPresent(player ->
        {
            float ticks = player.getTicks();
            if (ticks > 0.0f)
            {
                System.out.println("hi");
                player.setTicks(ticks - 1.0f);
                return;
            }
            if (!player.getSpellString().equals(""))
            {
                String spellString = player.getSpellString();
                player.setSpellString("");
                System.out.println("something");
                doSpellsInBook(event.player, spellString);
            }
        });
    }



}
