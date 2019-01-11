package com.github.unassignedxd.voidutils.main.compat.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CraftTweakerPlugin {

    public static final ArrayList<Supplier<IAction>> SCHEDULED_ACTIONS = new ArrayList<>();

    public static void preInit() {}

    public static void init() {}

    public static void postInit(){
        for(Supplier<IAction> supplier : SCHEDULED_ACTIONS)
            CraftTweakerAPI.apply(supplier.get());

        SCHEDULED_ACTIONS.clear();
    }
}
