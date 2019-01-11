package com.github.unassignedxd.voidutils.main.compat.jei.infusion;

import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystRecipe;
import com.github.unassignedxd.voidutils.main.blocks.tiles.TileCatalyticInfuser;
import com.github.unassignedxd.voidutils.main.init.ModItems;
import com.github.unassignedxd.voidutils.main.util.ModHelper;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class CatalystInfuserWrapper implements IRecipeWrapper {
    public final ResourceCatalystRecipe recipe;

    public CatalystInfuserWrapper(ResourceCatalystRecipe recipe){
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        builder.add(this.recipe.input.getMatchingStacks());

        Ingredient[] ingredients = new Ingredient[]{recipe.modifier1, recipe.modifier2, recipe.modifier3, recipe.modifier4, recipe.modifier5, recipe.modifier6, recipe.modifier7, recipe.modifier8};
        for(int i = 0; i <ingredients.length; i++){
            builder.add(ingredients[i].getMatchingStacks());
        }

        iIngredients.setInputs(VanillaTypes.ITEM, builder.build());

        ItemStack output = TileCatalyticInfuser.createResourceCatalyst(this.recipe);
        iIngredients.setOutput(VanillaTypes.ITEM, output.copy());

    }
}
