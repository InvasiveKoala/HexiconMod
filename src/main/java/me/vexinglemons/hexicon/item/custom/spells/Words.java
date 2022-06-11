package me.vexinglemons.hexicon.item.custom.spells;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

import static java.lang.Math.max;
import static me.vexinglemons.hexicon.item.custom.spells.GlobalWordFunctions.*;
import static me.vexinglemons.hexicon.item.custom.spells.MobPotionNames.translate;
import static me.vexinglemons.hexicon.item.custom.spells.WordFunctions.*;
import static me.vexinglemons.hexicon.util.Methods.findIndex;

public class Words {
    // create
    public static final String[] verbs = {"gen", "oriri", "cadere", "transfiguro", "depurgo", "dis", "percutite", "ignire", "trahere", "moderor"};

    public static final String[] nouns = {"res", "massa"};
        // self, last spawned entity
        public static final String[] mobs = {"Me", "Lupus", "Felis", "Bovis", "Equus", "Ovis", "Porcus", "Vespertilio", "Apis",
        "Pardalis", "Pullum", "Armis", "Sagitta", "Cod", "Delphini", "Margarita", "Oculus", "Ignis",
        "Vulpes", "Lolligo", "Ferrum", "Llama", "Fungus", "Mulus", "Psittacus", "Explodere",
        "Tridenti"}; // mob translations to english are in MobPotionNames.java under this same package

    public static final String[] adverbs = {"sanus", "fortis", "velox", "procidens", "tarditas",
            "festinare", "lassitudo", "saltus", "nauseum", "resistentia", "respiratio", "invisibilis",
            "caecitas", "vision", "fames", "infirmitas", "venenum", "arescet", "candentis", "avolare", "grace", // Potion Effects (See their equivalents in MobPotionNames under the switch statement)
                                            "valde", // Very
                                            "deincepsme", "deinceps", "retrome", "retro"}; //Directions (
    // Nearest
    public static final String[] specifyingAdjectives = {"proxima", "proximases"};
    public static final String[] blockAdjectives = {"sub"};
    // Verb Settings

                                                //gen, rise, fall, transform, cleanse, push, smite, ignite, pull
    public static final boolean[] hasDirectObject = {false, true, true, true, true, true, true, true, true, true, true}; //If the object of the verb is something that already exists


    //This is one of the parts where I think it's really poorly optimized, but I'm not sure how else to go about this without recoding the whole mod basically. Please give me tips to fix this!
    public static final boolean[] genAdverbs = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false}; // Decides which adverbs affect this verb
    public static final boolean[] riseAdverbs = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false};
    public static final boolean[] fallAdverbs = {false, false, false, false,false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false};
    public static final boolean[] transformAdverbs = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false};
    public static final boolean[] cleanseAdverbs = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false};
    public static final boolean[] pushAdverbs = {false, false, false,false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true};
    public static final boolean[] smiteAdverbs = {false, false, false,false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    public static final boolean[] igniteAdverbs = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false};
    public static final boolean[] pullAdverbs = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false};
    public static final boolean[] controlAdverbs = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    public static final boolean[][] hasAdverbs = {genAdverbs, riseAdverbs, fallAdverbs, transformAdverbs, cleanseAdverbs, pushAdverbs, smiteAdverbs, igniteAdverbs, pullAdverbs, controlAdverbs};




    public static boolean spell(String message, PlayerEntity caster, int lineNumber)
    {
        String[] messagearray = message.split(" ", -1);
        boolean expectsObject = false;
        boolean expectsAdjectiveOrNoun = false;
        int verb = 0;
        int wordNumber;
        int[] adverbModifiers = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1};
        boolean[] adjectiveSpecifiers = {false, false};
        boolean[] blockSpecifiers = {false};
        Entity[] target;
        BlockPos[] blockTarget;
        for(String word : messagearray)
        {
            wordNumber = findIndex(messagearray,word);
            boolean wordIsVerb = findIndex(verbs, word) > -1;
            if (expectsObject)
            {
                int adverbCheck = findIndex(adverbs, word);

                int specifyingCheck = findIndex(specifyingAdjectives, word);
                int blockCheck = findIndex(blockAdjectives, word);

                int adjectiveCheck = max(blockCheck, specifyingCheck);

                if (adverbCheck > -1) // if there is an adverb
                {
                    if (expectsAdjectiveOrNoun)
                    {
                        caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                ("Word " + (wordNumber + 1) + " of line " + lineNumber+ " is an adverb after an adjective."), true);
                        return false;
                    }
                    boolean[] verbBools = hasAdverbs[verb];
                    boolean canUseAdverb = verbBools[adverbCheck];
                    if (!canUseAdverb)
                    {
                        caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                ("Word " + (wordNumber + 1) + " of line " + lineNumber+ " isn't a compatible adverb with the verb " + verbs[verb] + "."), true);
                        return false;
                    }
                    else
                    {
                        adverbModifiers[adverbCheck]++;
                    }
                }

                else if (adjectiveCheck > -1)
                {
                    if (!hasDirectObject[verb])
                    {
                        caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                ("Word " + (wordNumber + 1) + " of line " + lineNumber + " isn't a compatible adjective with " + verbs[verb]), true);
                        return false;
                    }
                    expectsAdjectiveOrNoun = true;
                    if (specifyingCheck > -1)
                    {
                        adjectiveSpecifiers[findIndex(specifyingAdjectives, word)] = true;
                    }
                    else if (blockCheck > -1)
                    {
                        blockSpecifiers[findIndex(blockAdjectives, word)] = true;
                    }
                }

                else // If the word is a noun (Nouns end the spell)
                {
                    target = chooseTarget(caster, word, adjectiveSpecifiers, verb);
                    blockTarget = chooseBlockTarget(caster, blockSpecifiers, verb);
                    if (findIndex(nouns, word) == -1 && findIndex(mobs, word) == -1) // If the noun literally isn't a word return false
                    {
                        caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                ("Word " + (wordNumber + 1)  + " of line " + lineNumber+ " isn't a word. Check spelling."), true);
                        return false;
                    }
                    if (target == null && blockTarget == null && hasDirectObject[verb]) // If the target is null but there is supposed to be a target, return false
                    {
                        return false;
                    }
                    else if (target == null && blockTarget == null)  //If the target is null but there doesn't need to be a target, just set it to caster so the for loop runs
                    {
                        target = new Entity[]{caster};
                    }
                    adverbModifiers = getUsedAdverbs(adverbModifiers, verb);
                    if (target != null)
                    {
                        for (Entity targ : target)
                        {
                            switch (verb)
                            {
                                case 0:
                                    EntityType entityToSpawn = translate(word);
                                    gen(entityToSpawn, caster, wordNumber, adverbModifiers, lineNumber);
                                    break;
                                case 1:
                                    rise(targ, adverbModifiers);
                                    break;
                                case 2:
                                    fall(targ, adverbModifiers);
                                    break;
                                case 3:
                                    transform(targ, adverbModifiers);
                                    break;
                                case 4:
                                    cleanse(targ, adverbModifiers);
                                    break;
                                case 5:
                                    push(caster, targ, adverbModifiers);
                                    break;
                                case 6:
                                    lightning(targ);
                                    break;
                                case 7:
                                    ignite(targ, adverbModifiers);
                                    break;
                                case 8:
                                    pull(targ, adverbModifiers, caster);
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
                verb = findIndex(verbs, word);
            }

        }
        return false;
    }



}
