package com.github.unassignedxd.voidutils.main.compat.jei;

import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystRecipe;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.compat.jei.infusion.CatalystInfuserCategory;
import com.github.unassignedxd.voidutils.main.compat.jei.infusion.CatalystInfuserWrapper;
import com.github.unassignedxd.voidutils.main.compat.jei.infusion.ResourceCatalystSubtypeInterpreter;
import com.github.unassignedxd.voidutils.main.init.ModBlocks;
import com.github.unassignedxd.voidutils.main.init.ModItems;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIVoidUtilsPlugin implements IModPlugin {

    public static final String CATALYST_INFUSION = VoidUtils.MOD_ID + ".catalyst_infusion";

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(
                new CatalystInfuserCategory(helper)
        );
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        ResourceCatalystSubtypeInterpreter.registerSubtypes(subtypeRegistry);
    }

    @Override
    public void register(IModRegistry registry) {
        registry.handleRecipes(ResourceCatalystRecipe.class, CatalystInfuserWrapper::new, CATALYST_INFUSION);

        registry.addRecipes(VoidUtilsAPI.RESOURCE_INFUSION_RECIPES.values(), CATALYST_INFUSION);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.CATALYTIC_INFUSER), CATALYST_INFUSION);
    }
}
