package com.github.unassignedxd.voidutils.main.compat.jei.infusion;

import com.github.unassignedxd.voidutils.api.recipe.CatalystInfusionRecipe;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.compat.jei.JEIVoidUtilsPlugin;
import com.github.unassignedxd.voidutils.main.init.ModBlocks;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

public class CatalystInfuserCategory implements IRecipeCategory<CatalystInfuserWrapper> {

    private final IDrawable background;
    private final ItemStack catalystInfuser = new ItemStack(ModBlocks.CATALYST_INFUSER);

    public CatalystInfuserCategory(IGuiHelper helper){
        this.background = helper.createDrawable(new ResourceLocation(VoidUtils.MOD_ID, "textures/gui/jei/catalyst_infusion.png"), 0, 0, 148, 148);
    }

    @Override
    public String getUid() {
        return JEIVoidUtilsPlugin.CATALYST_INFUSION;
    }

    @Override
    public String getTitle() {
        return I18n.format("container." + JEIVoidUtilsPlugin.CATALYST_INFUSION + ".name");
    }

    @Override
    public String getModName() {
        return VoidUtils.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CatalystInfuserWrapper catalystInfuserWrapper, IIngredients iIngredients) {
        IGuiItemStackGroup iGroup = recipeLayout.getItemStacks();
        CatalystInfusionRecipe recipe = catalystInfuserWrapper.recipe;
        iGroup.init(0, true, 65, 56);
        iGroup.set(0, Arrays.asList(recipe.input.getMatchingStacks()));

        iGroup.init(1, false, 56, 18);
        iGroup.set(1, recipe.output);

        iGroup.init(2, true, 0, 10);
        iGroup.set(2, Arrays.asList(recipe.modifier1.getMatchingStacks()));

        iGroup.init(3, true, 0, 30);
        iGroup.set(3, Arrays.asList(recipe.modifier1.getMatchingStacks()));

        iGroup.init(4, true, 0, 50);
        iGroup.set(4, Arrays.asList(recipe.modifier1.getMatchingStacks()));

        iGroup.init(5, true, 0, 70);
        iGroup.set(5, Arrays.asList(recipe.modifier1.getMatchingStacks()));

        iGroup.init(6, true, 0, 90);
        iGroup.set(6, Arrays.asList(recipe.modifier1.getMatchingStacks()));

        iGroup.init(7, true, 10, 10);
        iGroup.set(7, Arrays.asList(recipe.modifier1.getMatchingStacks()));

        iGroup.init(8, true, 20, 10);
        iGroup.set(8, Arrays.asList(recipe.modifier1.getMatchingStacks()));
    }
}
