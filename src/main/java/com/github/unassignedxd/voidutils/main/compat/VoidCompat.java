package com.github.unassignedxd.voidutils.main.compat;

import com.github.unassignedxd.voidutils.main.compat.crafttweaker.CraftTweakerPlugin;
import net.minecraftforge.fml.common.Loader;

public class VoidCompat {

    public static boolean craftTweakerLoaded;
    public static boolean mtLibLoaded;

    public static void preInit(){
        craftTweakerLoaded = Loader.isModLoaded("crafttweaker");
        mtLibLoaded = Loader.isModLoaded("mtlib");
    }

    public static void init() {}

    public static void postInit() {
        if(craftTweakerLoaded && mtLibLoaded)
            CraftTweakerPlugin.postInit();
    }
}
