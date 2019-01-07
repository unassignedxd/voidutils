package com.github.unassignedxd.voidutils.api;

import com.github.unassignedxd.voidutils.api.infusion.modifier.IVoidModifier;
import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class VoidUtilsAPI {

    public static final String MOD_ID = "voidutils";
    public static final String API_ID = MOD_ID + "-api";
    public static final String API_VERSION = "1";

    public static final Map<ResourceLocation, ResourceCatalystRecipe> RESOURCE_INFUSION_RECIPES = new HashMap<>();

    public static final Map<ResourceLocation, IVoidModifier> VOID_INFUSION_MODIFIERS = new HashMap<>();

}