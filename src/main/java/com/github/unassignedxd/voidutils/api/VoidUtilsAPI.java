package com.github.unassignedxd.voidutils.api;

import com.github.unassignedxd.voidutils.api.recipe.VoidInfusionRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;

public class VoidUtilsAPI {

    public static final String MOD_ID = "voidutils";
    public static final String API_ID = MOD_ID + "-api";
    public static final String API_VERSION = "1";

    public static final HashMap<ResourceLocation, VoidInfusionRecipe> VOID_INFUSION_RECIPES = new HashMap<>();
}