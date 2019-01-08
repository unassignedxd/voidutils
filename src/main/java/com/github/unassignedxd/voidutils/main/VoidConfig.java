package com.github.unassignedxd.voidutils.main;

import net.minecraftforge.common.config.Config;

@Config(modid = VoidUtils.MOD_ID, category = "")
public class VoidConfig {
    public static General general = new General();

    public static class General {
        @Config.Comment("How often should VoidUtil's TEs send data to the Clients. Higher numbers will increase network traffic. Default: 5 ticks")
        public int teUpdate = 5;

        @Config.Comment("Which blocks can the Bedrock event can replace.")
        public String[] bedrockEventReplaceBlacklist = {}; //todo add chest / etc.

        @Config.Comment({"As VoidUtils has an automated system to add all types of ingots in the game as acceptable catalyst types, you can blacklist them here if you do not want them to exist.",
                            "The string you should put is the resource location of the item that you want blacklisted. To find this, use the in-game's advanced tooltip function."})
        public String[] resourceCatalystBlacklist = {};

        @Config.Comment({"Which types of Resource Catalyst should exist within VoidUtil's. These catalyst can be used to craft a crystal for the respective type, and duplicate them from there.",
                            "The string you should put is the resource location of the item itself, to find this, use the in-game's advanced tooltip function."})
        public String[] resourceCatalystTypes = {};

        @Config.Comment({"Enable default Resource Catalyst recipes, which automatically register according to the types that exist. The recipe will autofill itself with the according block."})
        public boolean resourceCatalystDefaultRecipe = true;

        @Config.Comment("The max receive that the catalytic infuser can receive energy. Default: 500000 RF /t")
        public int catalyticInfuserMaxReceive = 500000;
    }
}
