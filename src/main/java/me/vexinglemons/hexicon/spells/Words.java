package me.vexinglemons.hexicon.spells;

import me.vexinglemons.hexicon.capabilities.PlayerData.IPlayerData;
import me.vexinglemons.hexicon.capabilities.PlayerData.PlayerDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.LazyOptional;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static me.vexinglemons.hexicon.spells.GlobalWordFunctions.*;
import static me.vexinglemons.hexicon.spells.MobPotionNames.translate;
import static me.vexinglemons.hexicon.spells.WordFunctions.*;

public class Words {
    static ArrayList<String> inTransitives = new ArrayList<>(Arrays.asList(
            "gen"
    ));

    // Verbs that don't have a living object


    static final ArrayList<String> verbs = new ArrayList<>(
            Arrays.asList("gen", "oriri", "cadere", "transfiguro", "depurgo", "dis",
                    "percutite", "ignire", "trahere","moderor", "revelare")
    );

    static final ArrayList<String> nouns = new ArrayList<>(Arrays.asList(
            "res", "massa" //Thing, Block
    ));

        static final ArrayList<String> mobs = new ArrayList<>(Arrays.asList(
                "me", "lupus", "felis", "bovis", "equus", "ovis", "porcus", "vespertilio", "apis",
                "pardalis", "pullum", "armis", "sagitta", "cod", "delphini", "margarita", "oculus", "ignis",
                "vulpes", "lolligo", "ferrum", "llama", "fungus", "mulus", "psittacus", "explodere",
                "tridenti"
        ));
       // mob translations to english are in MobPotionNames.java under this same package


    static final ArrayList<String> adverbs = new ArrayList<>(
            Arrays.asList(
                    "valde", // Very
                    "deincepsme", "deinceps", "retrome", "retro")
    );

    static final ArrayList<String> potions = new ArrayList<>(
            Arrays.asList("sanus", "fortis", "velox", "procidens", "tarditas",
                    "festinare", "lassitudo", "saltus", "nauseum", "resistentia", "respiratio", "invisibilis",
                    "caecitas", "vision", "fames", "infirmitas", "venenum", "arescet", "candentis", "avolare", "grace")
    ); // Potion Effects (See their equivalents in MobPotionNames under the switch statement)

    // Nearest
    static final ArrayList<String> specifyingAdjectives = new ArrayList<>(
            Arrays.asList("proxima", "proximases"
    ));

    static final ArrayList<String> blockAdjectives = new ArrayList<>(
            Arrays.asList("sub", "supra", "orientem", "occidens", "comprehendo") // Under, above, east, west, inclusive (includes all blocks)
     );

    static final ArrayList<String> potionVerbs = new ArrayList<>(
            Arrays.asList("gen", "transfiguro", "depurgo")
    );
    /*
    Decides which verbs are compatible with which adverbs
    Verb on the left, adverb list on the right.
     */
    static final HashMap<String, ArrayList<String>> verbAdverbs = new HashMap<>();
    public Words(){
        ArrayList<String> genAdverbs = new ArrayList<>(Arrays.asList("none"));
        verbAdverbs.put("gen", genAdverbs);

        ArrayList<String> riseAdverbs = new ArrayList<>(Arrays.asList("valde"));
        verbAdverbs.put("oriri", riseAdverbs);

        ArrayList<String> fallAdverbs = new ArrayList<>(Arrays.asList("valde"));
        verbAdverbs.put("cadere", fallAdverbs);

        ArrayList<String> cleanseAdverbs = new ArrayList<>();
        verbAdverbs.put("depurgo", cleanseAdverbs);

        ArrayList<String> pushAdverbs = new ArrayList<>(Arrays.asList("valde", "deincepsme", "deinceps", "retrome", "retro"));
        verbAdverbs.put("dis", pushAdverbs);

        ArrayList<String> smiteAdverbs = new ArrayList<>();
        verbAdverbs.put("percutite", smiteAdverbs);

        ArrayList<String> igniteAdverbs = new ArrayList<>(Arrays.asList("valde"));
        verbAdverbs.put("ignire", igniteAdverbs);

        ArrayList<String> pullAdverbs = new ArrayList<>(Arrays.asList("valde", "retro"));
        verbAdverbs.put("trahere", pullAdverbs);

        ArrayList<String> controlAdverbs = new ArrayList<>();
        verbAdverbs.put("moderor", controlAdverbs);

        ArrayList<String> classifyAdverbs = new ArrayList<>();
        verbAdverbs.put("revelare", classifyAdverbs);

    }

    static private final HashMap<String, Integer> ManaCosts = new HashMap<>();
    {
        ManaCosts.put("gen", 15);
        ManaCosts.put("oriri", 5);
        ManaCosts.put("cadere", 5);
        ManaCosts.put("transfiguro", 15);
        ManaCosts.put("depurgo", 5);
        ManaCosts.put("dis", 5);
        ManaCosts.put("percutite", 20);
        ManaCosts.put("ignire", 5);
        ManaCosts.put("trahere", 5);
        ManaCosts.put("moderor", 30);
        ManaCosts.put("revelare", 1);
        ManaCosts.put("res", 10);
        ManaCosts.put("massa", 3);
        ManaCosts.put("explodere", 5);
        ManaCosts.put("valde", 5);
        ManaCosts.put("avolare", 15);
        ManaCosts.put("proximases", 15);
        ManaCosts.put("comprehendo", 15);
        ManaCosts.put("sub", 1);
        ManaCosts.put("supra", 1);
        ManaCosts.put("orientem", 1);
        ManaCosts.put("occidens", 1);
        ManaCosts.put("deinceps", 0);
        ManaCosts.put("deincepsme", 0);
        ManaCosts.put("retro", 0);
        ManaCosts.put("retrome", 0);
        // The default value is 2. If a word is not listed here, it costs 2 mana. Most verbs and other important words are here anyways, though.
    }


    public static boolean spell(String message, PlayerEntity caster, int lineNumber)
    {
        String newMessage = message.replace(".", "").replace(",", "").replace("!", "");
        String[] messagearray = newMessage.split(" ", -1);
        boolean expectsObject = false;
        boolean expectsAdjectiveOrNoun = false;
        int verb = 0;
        String verbString = "";

        HashMap<String, Integer> adverbMods = new HashMap<>(); // Technically potions are adverbs so I include them here.
        for(String mod : potions) {adverbMods.put(mod, 1);}
        for(String mod : adverbs) {adverbMods.put(mod, 1);}

        HashMap<String, Boolean> adjectiveSpecifiers = new HashMap<>();
        for(String mod : specifyingAdjectives) { adjectiveSpecifiers.put(mod, false); }

        HashMap<String, Integer> blockSpecifiers = new HashMap<>();
        for(String mod : blockAdjectives) { blockSpecifiers.put(mod, 1); }

        ArrayList<Entity> target;
        ArrayList<BlockPos> blockTarget;
        int wordNumber = 0;
        int manaCost = 0;
        for(String rawWord : messagearray)
        {
            String word = rawWord.toLowerCase();
            manaCost += ManaCosts.getOrDefault(word, 2);
            boolean wordIsVerb = verbs.contains(word);
            if (expectsObject)
            {
                boolean adverbCheck = adverbs.contains(word);
                boolean potionCheck = potions.contains(word);

                boolean isSpecifying = specifyingAdjectives.contains(word);
                boolean isBlock = blockAdjectives.contains(word);

                boolean adjectiveCheck = isSpecifying || isBlock;

                if (adverbCheck || potionCheck) // if there is an adverb
                {
                    if (potionCheck && !potionVerbs.contains(verbString))
                    {
                        caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                ("Word " + (wordNumber + 1) + " of line " + lineNumber+ " isn't a compatible adverb with the verb " + verbString + "."), true);
                        return false;
                    }
                    if (expectsAdjectiveOrNoun)
                    {
                        caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                ("Word " + (wordNumber + 1) + " of line " + lineNumber+ " is an adverb after an adjective."), true);
                        return false;
                    }
                    ArrayList<String> verbBools = verbAdverbs.get(verbString);
                    boolean canUseAdverb;
                    try{canUseAdverb = verbBools.contains(word);} catch(NullPointerException e) {canUseAdverb = false;}
                    boolean canUsePotionAdverb = potionVerbs.contains(verbString) && potionCheck;
                    if (!canUseAdverb && !canUsePotionAdverb)
                    {
                        caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                ("Word " + (wordNumber + 1) + " of line " + lineNumber+ " isn't a compatible adverb with the verb " + verbString + "."), true);
                        return false;
                    }
                    else
                    {
                        adverbMods.put(word, (adverbMods.get(word) + 1));
                    }
                }

                else if (adjectiveCheck)
                {
                    if (inTransitives.contains(verbString))
                    {
                        caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                ("Word " + (wordNumber + 1) + " of line " + lineNumber + " isn't a compatible adjective with " + verbString), true);
                        return false;
                    }
                    expectsAdjectiveOrNoun = true;
                    if (isSpecifying)
                    {
                        adjectiveSpecifiers.put(word, true);
                    }
                    else if (isBlock)
                    {
                        blockSpecifiers.put(word, (blockSpecifiers.get(word) + 1));
                    }
                }

                else // If the word is a noun (Nouns end the spell)
                {
                    boolean hasDirectObject = !inTransitives.contains(verbString);
                    ArrayList<Boolean> adjectiveSpecifiersList = new ArrayList<>(adjectiveSpecifiers.values());

                    target = chooseTarget(caster, word, adjectiveSpecifiersList, hasDirectObject);
                    //ArrayList<Integer> blockList = new ArrayList<>(blockSpecifiers.values());

                    blockTarget = chooseBlockTarget(caster, blockSpecifiers, hasDirectObject);
                    if (!nouns.contains(word) && !mobs.contains(word)) // If the noun literally isn't a word return false
                    {
                        caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                ("Word " + (wordNumber + 1)  + " of line " + lineNumber+ " isn't a word. Check spelling."), true);
                        return false;
                    }
                    if (target == null && blockTarget == null && hasDirectObject) // If the target is null but there is supposed to be a target, return false
                    {
                        return false;
                    }
                    else if (target == null && blockTarget == null)  //If the target is null but there doesn't need to be a target, just set it to caster so the for loop runs
                    {
                        target = new ArrayList<>(Arrays.asList(caster));
                    }
                    ArrayList<Integer> UsedAdverbs = getUsedAdverbs(adverbMods, verbString);
                    // MANA HANDLING

                    LazyOptional<IPlayerData> mana = caster.getCapability(PlayerDataProvider.capability, null);
                    if (mana.isPresent())
                    {
                        IPlayerData player = mana.resolve().get();
                        int playerMana = player.getMana();
                        if (playerMana >= manaCost)
                        {
                            player.removeMana(manaCost);
                        }
                        else{
                            caster.sendMessage(new StringTextComponent("Not enough mana."), caster.getUniqueID());
                            return false;
                        }
                    }

                    // --------
                    if (target != null)
                    {
                        for (Entity targ : target)
                        {
                            switch (verb)
                            {
                                case 0:
                                    EntityType entityToSpawn = translate(word);
                                    gen(entityToSpawn, caster, wordNumber, UsedAdverbs, lineNumber);
                                    break;
                                case 1:
                                    rise(targ, UsedAdverbs);
                                    break;
                                case 2:
                                    fall(targ, UsedAdverbs);
                                    break;
                                case 3:
                                    transform(targ, UsedAdverbs);
                                    break;
                                case 4:
                                    cleanse(targ, UsedAdverbs);
                                    break;
                                case 5:
                                    push(caster, targ, UsedAdverbs);
                                    break;
                                case 6:
                                    lightning(targ);
                                    break;
                                case 7:
                                    ignite(targ, UsedAdverbs);
                                    break;
                                case 8:
                                    pull(targ, UsedAdverbs, caster);
                                    break;
                                case 9:
                                    control(targ, caster);
                                    break;
                            }
                        }
                    }
                    else if (word.equalsIgnoreCase("massa"))
                    {
                        for (BlockPos blockLoc : blockTarget)
                        {
                            switch (verb)
                            {
                                case 10:
                                    classify(blockLoc, caster);
                                    break;

                            }
                        }
                    }
                    return true;
                }


            }
            else if (wordIsVerb)
            {
                expectsObject = true;
                verbString = word;
                verb = verbs.indexOf(verbString);

            }
            wordNumber++;

        }
        return false;
    }



}
