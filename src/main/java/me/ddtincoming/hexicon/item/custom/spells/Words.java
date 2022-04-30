package me.ddtincoming.hexicon.item.custom.spells;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

import static java.lang.Math.max;
import static me.ddtincoming.hexicon.item.custom.spells.GlobalWordFunctions.getUsedAdverbs;
import static me.ddtincoming.hexicon.item.custom.spells.Mobnames.translate;
import static me.ddtincoming.hexicon.item.custom.spells.WordFunctions.*;
import static me.ddtincoming.hexicon.util.Methods.findIndex;

public class Words {
    // create
    public static final String[] verbs = {"gen", "oriri", "cadere", "transfiguro", "depurgo", "dis", "percutite", "ignire", "trahere"};

    public static final String[] nouns = {};
        // self, last spawned entity
        public static final String[] mobs = {"Me", "Lupus", "Felis", "Bovis", "Equus", "Ovis", "Porcus", "Vespertilio", "Apis",
        "Pardalis", "Pullum", "Armis", "Sagitta", "Cod", "Delphini", "Margarita", "Oculus", "Ignis",
        "Vulpes", "Lolligo", "Ferrum", "Llama", "Fungus", "Mulus", "Psittacus", "Explodere",
        "Tridenti"}; // mob translations to english are in Mobnames.java under this same package

    // very, quickly (action), healthily, strongly, speedily (description), forward (player)
    public static final String[] adverbs = {"sanus", "fortis", "velox", "procidens", "tarditas",
            "festinare", "lassitudo", "saltus", "nauseum", "resistentia", "respiratio", "invisibilis",
            "caecitas", "vision", "fames", "infirmitas", "venenum", "arescet", "candentis", "avolare", "grace", // Potion Effects
                                            "valde", // Very
                                            "deincepsme", "deinceps", "retrome", "retro"}; //Directions
    // Nearest
    public static final String[] specifyingAdjectives = {"proxima"};
    public static final String[] modifyingAdjectives = {};
    // Verb Settings

                                                //gen, rise, fall, transform, cleanse, push, smite, ignite, pull
    public static final boolean[] hasDirectObject = {false, true, true, true, true, true, true, true, true}; //If the object of the verb is something that already exists


    //This is one of the parts where I know it's really poorly optimized, but I'm not sure how else to go about this without recoding the whole mod basically. Please give me tips to fix this!
    public static final boolean[] genAdverbs = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false}; // Decides which adverbs affect this verb
    public static final boolean[] riseAdverbs = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false};
    public static final boolean[] fallAdverbs = {false, false, false, false,false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false};
    public static final boolean[] transformAdverbs = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false};
    public static final boolean[] cleanseAdverbs = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false};
    public static final boolean[] pushAdverbs = {false, false, false,false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true};
    public static final boolean[] smiteAdverbs = {false, false, false,false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    public static final boolean[] igniteAdverbs = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false};
    public static final boolean[] pullAdverbs = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false};
    public static final boolean[][] hasAdverbs = {genAdverbs, riseAdverbs, fallAdverbs, transformAdverbs, cleanseAdverbs, pushAdverbs, smiteAdverbs, igniteAdverbs, pullAdverbs};




    public static boolean spell(String message, PlayerEntity caster, int lineNumber)
    {
        String[] messagearray = message.split(" ", -1);
        boolean expectsObject = false;
        boolean expectsAdjectiveOrNoun = false;
        int verb = 0;
        int wordNumber;
        int[] adverbModifiers = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1 ,1};
        //int[] adjectiveModifiers = {};
        boolean[] adjectiveSpecifiers = {false};
        Entity[] target;
        for(String word : messagearray)
        {
            wordNumber = findIndex(messagearray,word);
            boolean wordIsVerb = findIndex(verbs, word) > -1;
            if (expectsObject)
            {
                int adverbCheck = findIndex(adverbs, word);

                int specifyingCheck = findIndex(specifyingAdjectives, word);
                int modifyingCheck = findIndex(modifyingAdjectives, word);
                int adjectiveCheck = max(modifyingCheck, specifyingCheck);

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
                    expectsAdjectiveOrNoun = true;
                    if (specifyingCheck > -1)
                    {
                        if (!hasDirectObject[verb])
                        {
                            caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                    ("Word " + (wordNumber + 1)  + " of line " + lineNumber+ " isn't a compatible adjective with " + verbs[verb]), true);
                            return false;
                        }
                        adjectiveSpecifiers[findIndex(specifyingAdjectives, word)] = true;
                    }
                    else if (modifyingCheck > -1)
                    {
                        if (hasDirectObject[verb])
                        {
                            caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                    ("Word " + (wordNumber + 1) + " of line " + lineNumber+ " isn't a compatible adjective with " + verbs[verb]), true);
                            return false;
                        }
                        //adjectiveModifiers[findIndex(modifyingAdjectives, word)]++;
                    }

                }

                else // If the word is a noun (Nouns end the spell)
                {
                    target = chooseTarget(caster, word, adjectiveSpecifiers, verb);
                    if (findIndex(nouns, word) == -1 && findIndex(mobs, word) == -1)
                    {
                        caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                                ("Word " + (wordNumber + 1)  + " of line " + lineNumber+ " isn't a word. Check spelling."), true);
                        return false;
                    }
                    if (target == null && hasDirectObject[verb])
                    {
                        return false;
                    }
                    adverbModifiers = getUsedAdverbs(adverbModifiers, verb);
                    switch (verb) // When i don't have each case return true, for some reason the oriri verb makes the player go down instead of up, which i don't really understand. If anyone tells me why this is the case i'd happily fix it because i know it's definitely not the best way
                    {
                        case 0:
                            EntityType entityToSpawn = translate(word);
                            gen(entityToSpawn, caster, wordNumber, adverbModifiers, lineNumber);
                            return true;
                        case 1:
                            rise(target, adverbModifiers);
                            return true;
                        case 2:
                            fall(target, adverbModifiers);
                            return true;
                        case 3:
                            transform(target, adverbModifiers);
                            return true;
                        case 4:
                            cleanse(target, adverbModifiers);
                            return true;
                        case 5:
                            push(caster, target, adverbModifiers);
                            return true;
                        case 6:
                            lightning(target);
                            return true;
                        case 7:
                            ignite(target, adverbModifiers);
                            return true;
                        case 8:
                            pull(target, adverbModifiers, caster);
                            return true;
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


    public static Entity[] chooseTarget(PlayerEntity caster, String word, boolean[] specifiers, int verb)
    {
        if (!hasDirectObject[verb])
        {
            return null;
        }
        Entity[] target = null;
        EntityType entityType = null;
        if (translate(word) != null)
        {
            entityType = translate(word);
        }

        if (specifiers[0])
        {
            ServerWorld world = (ServerWorld) caster.getEntityWorld();

            List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(caster, caster.getBoundingBox().expand(45.0f, 25.0f, 45.0f).expand(-45.0f, -25.0f, -45.0f));
            if (entities.size() <= 0)
            {
                caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        ("No mobs are within range."), true);
                return null;
            }
            target = new Entity[]{entities.get(0)};
            for(Entity entity : entities)
            {
                if (entity.getType() == entityType)
                {
                    if (entity.getDistance(caster) < target[0].getDistance(caster))
                    {
                        target = new Entity[]{entity};
                    }
                }
            }
            if (target[0].getType() != entityType)
            {
                caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        ("No mobs of this kind are within range."), true);
                return null;
            }

        }
        if (target == null && entityType == EntityType.PLAYER)
        {
            target = new Entity[]{caster};
        }
        if (target == null && hasDirectObject[verb])
        {
            if (hasDirectObject[verb])
            {
                caster.sendStatusMessage(ITextComponent.getTextComponentOrEmpty
                        (word + " isn't a compatible noun with its verb."), true);
            }

        }

        return target;
    }

}
