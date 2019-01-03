package com.github.unassignedxd.voidutils.api;

import com.github.unassignedxd.voidutils.api.recipe.ResourceInfusionRecipe;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class VoidUtilsAPI {

    public static final String MOD_ID = "voidutils";
    public static final String API_ID = MOD_ID + "-api";
    public static final String API_VERSION = "1";

    /**
     * The list that is accessed through infusion crafting. Note: the ResourceInfusionRecipe#ingredients field must be less than 9 ingredients (8 or less).
     */
    public static final Map<ResourceLocation, ResourceInfusionRecipe> RESOURCE_INFUSION_RECIPES = new HashMap<>();
}