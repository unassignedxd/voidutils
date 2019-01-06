package com.github.unassignedxd.voidutils.main.compat.jei.infusion;

import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystInfusionRecipe;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class CatalystInfuserWrapper implements IRecipeWrapper {
    public final ResourceCatalystInfusionRecipe recipe;

    public CatalystInfuserWrapper(ResourceCatalystInfusionRecipe recipe){
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();

        builder.add(this.recipe.input.getMatchingStacks());
        builder.add(this.recipe.modifier1.getMatchingStacks());
        builder.add(this.recipe.modifier2.getMatchingStacks());
        builder.add(this.recipe.modifier3.getMatchingStacks());
        builder.add(this.recipe.modifier4.getMatchingStacks());
        builder.add(this.recipe.modifier5.getMatchingStacks());
        builder.add(this.recipe.modifier6.getMatchingStacks());
        builder.add(this.recipe.modifier7.getMatchingStacks());
        builder.add(this.recipe.modifier8.getMatchingStacks());

        iIngredients.setInputs(VanillaTypes.ITEM, builder.build());
        iIngredients.setOutput(VanillaTypes.ITEM, this.recipe.output.getResourceDupe());

    }
}
