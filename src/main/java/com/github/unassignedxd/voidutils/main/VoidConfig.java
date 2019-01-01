package com.github.unassignedxd.voidutils.main;

import net.minecraftforge.common.config.Config;

@Config(modid = VoidUtils.MOD_ID, category = "")
public class VoidConfig {
    public static General general = new General();

    public static class General {
        @Config.Comment("How often should VoidUtil's TEs send data to the Clients. Higher numbers will increase network traffic. Default: 10 ticks")
        public int teUpdate = 10;
    }
}
