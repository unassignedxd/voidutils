package com.github.unassignedxd.voidutils.main.compat.jei;

import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystInfusionRecipe;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.compat.jei.infusion.CatalystInfuserCategory;
import com.github.unassignedxd.voidutils.main.compat.jei.infusion.CatalystInfuserWrapper;
import com.github.unassignedxd.voidutils.main.init.ModBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
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
    public void register(IModRegistry registry) {
        registry.handleRecipes(ResourceCatalystInfusionRecipe.class, CatalystInfuserWrapper::new, CATALYST_INFUSION);

        registry.addRecipes(VoidUtilsAPI.RESOURCE_INFUSION_RECIPES.values(), CATALYST_INFUSION);

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.CATALYST_INFUSER), CATALYST_INFUSION);
    }
}
