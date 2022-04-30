package me.ddtincoming.jillingots.item.custom.spells;

import java.util.ArrayList;
import java.util.List;

import static me.ddtincoming.jillingots.item.custom.spells.Words.hasAdverbs;

public class GlobalWordFunctions {

    public static int checkDirectionValidity(int[] modifiers) // 0 is good, 1 means there isn't a direction, 2 means there is more than 1 direction
    {
        if (modifiers[1] == 1 && modifiers[2] == 1 && modifiers[3] == 1 && modifiers[4] == 1) {
            return 1;
        }
        boolean onemodifier = false;
        int i = 0;
        for (int modifier : modifiers)
        {
            if (i == 0){continue;}

            if (modifier > 1)
            {
                if (onemodifier)
                {
                    return 2;
                }
                onemodifier = true;
            }
            i++;
        }
        return 0;

    }
    public static int[] getUsedAdverbs(int[] modifiers, int verb)
    {
        int i = 0;
        List<Integer> newModifiers = new ArrayList<>();
        for (int modifier  : modifiers)
        {
            if (hasAdverbs[verb][i])
            {
                newModifiers.add(modifier);
            }
            i++;
        }
        int[] array = new int[newModifiers.size()];
        for(int i2 = 0; i2 < newModifiers.size(); i2++) array[i2] = newModifiers.get(i2);
        return array;
    }
}