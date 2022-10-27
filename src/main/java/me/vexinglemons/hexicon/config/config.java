package me.vexinglemons.hexicon.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class config
{
    public static class Common
    {
        private static final int defaultInt1 = 450;

        public final ForgeConfigSpec.ConfigValue<Integer> Mana;


        public Common(ForgeConfigSpec.Builder builder)
        {
            builder.push("category1");
            this.Mana = builder.comment("The base maximum mana of all players when starting the mod. Default: 450, Min: 50, Max: 1000")
                    .worldRestart()
                    .defineInRange("Short but readable name",defaultInt1 , 50, 1000);
            builder.pop();
        }
    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static //constructor
    {
        Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON = commonSpecPair.getLeft();
        COMMON_SPEC = commonSpecPair.getRight();
    }
}