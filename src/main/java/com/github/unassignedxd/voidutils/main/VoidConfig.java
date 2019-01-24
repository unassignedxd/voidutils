package com.github.unassignedxd.voidutils.main;

import net.minecraftforge.common.config.Config;

@Config(modid = VoidUtils.MOD_ID)
public class VoidConfig {

    public static World world = new World();
    public static TileEntities tileEntities = new TileEntities();

    public static class World {
        @Config.Name("Should Void Ore Spawn?")
        public boolean shouldVoidOreSpawn = true;

        @Config.Name("Max Height for Void Ore to Spawn")
        @Config.RangeInt(min = 0)
        public int voidOreMaxY = 32;

        @Config.Name("Min Height for Void Ore to Spawn")
        @Config.RangeInt(min = 0)
        public int voidOreMinY = 8;

        @Config.Name("The Odds Void Ore will Spawn in a Chunk")
        @Config.RangeInt(min = 0)
        public int voidOreSpawnChance = 5;

        @Config.Name("The Max Size of Each Void Ore Vein")
        @Config.RangeInt(min = 3)
        public int voidMaxVeinSize = 5;

        @Config.Name("What Dimensions Should Void Ore NOT Spawn?")
        @Config.Comment("The value entered will be the dimension ID Void Ore will be blacklisted.")
        public int[] voidOreSpawnBlacklistDimIDs = {-1, 1};
    }

    public static class TileEntities {

        @Config.RangeInt(min = 1)
        @Config.Name("Tile Entity Update Interval")
        @Config.Comment({"How often VoidUtil's Tile Entities will send data (in ticks) to the client.", "Default: 5 Ticks"})
        public int teUpdateInterval = 5;

    }
}
