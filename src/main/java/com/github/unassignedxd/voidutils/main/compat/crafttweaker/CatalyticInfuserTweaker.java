package com.github.unassignedxd.voidutils.main.compat.crafttweaker;

import com.blamejared.mtlib.helpers.InputHelper;
import com.blamejared.mtlib.helpers.LogHelper;
import com.blamejared.mtlib.utils.BaseMapAddition;
import com.blamejared.mtlib.utils.BaseMapRemoval;
import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystRecipe;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.util.infusion.ResourceCatalyst;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ZenRegister
@ZenClass("mods." + VoidUtils.MOD_ID + ".CatalyticInfuser")
public class CatalyticInfuserTweaker {

    @ZenMethod
    public static void addRecipe(String name, IItemStack input,
                                 IItemStack modifier1, IItemStack modifier2, IItemStack modifier3, IItemStack modifier4, IItemStack modifier5, IItemStack modifier6, IItemStack modifier7, IItemStack modifier8,
                                 ItemStack dupeResource, int dupeRate, int powerUsePerTick, double depletionPerTick, int infusionEnergyUse, int particleColor){
        CraftTweakerPlugin.SCHEDULED_ACTIONS.add(() -> {
            ResourceLocation location = new ResourceLocation(name);
            return new AddRecipe(Collections.singletonMap(location, new ResourceCatalystRecipe(location, InputHelper.toStack(input),
                    InputHelper.toStack(modifier1), InputHelper.toStack(modifier2), InputHelper.toStack(modifier3), InputHelper.toStack(modifier4),
                    InputHelper.toStack(modifier5), InputHelper.toStack(modifier6), InputHelper.toStack(modifier7), InputHelper.toStack(modifier8),
                    new ResourceCatalyst(dupeResource, dupeRate, powerUsePerTick, depletionPerTick),
                    infusionEnergyUse, particleColor)));
        });
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        CraftTweakerPlugin.SCHEDULED_ACTIONS.add(() -> {
            HashMap<ResourceLocation, ResourceCatalystRecipe> recipes = new HashMap<>();
            for(ResourceCatalystRecipe recipe : recipes.values()) {
                if(ItemStack.areItemsEqual(recipe.output.getResourceDupe(), InputHelper.toStack(output))) {
                    recipes.put(recipe.name, recipe);
                }
            }
            return new RemoveRecipe(recipes);
        });
    }

    private static class RemoveRecipe extends BaseMapRemoval<ResourceLocation, ResourceCatalystRecipe> {

        protected RemoveRecipe(Map<ResourceLocation, ResourceCatalystRecipe> map){
            super("Catalytic Resource Infuser", VoidUtilsAPI.RESOURCE_INFUSION_RECIPES, map);
        }

        @Override
        protected String getRecipeInfo(Map.Entry<ResourceLocation, ResourceCatalystRecipe> recipe) {
            return LogHelper.getStackDescription(recipe.getValue().output.getResourceDupe());
        }
    }

    private static class AddRecipe extends BaseMapAddition<ResourceLocation, ResourceCatalystRecipe> {

        protected AddRecipe(Map<ResourceLocation, ResourceCatalystRecipe> map) {
            super("Catalytic Resource Infuser", VoidUtilsAPI.RESOURCE_INFUSION_RECIPES, map);
        }

        @Override
        protected String getRecipeInfo(Map.Entry<ResourceLocation, ResourceCatalystRecipe> recipe) {
            return LogHelper.getStackDescription(recipe.getValue().output.getResourceDupe());
        }
    }
}
