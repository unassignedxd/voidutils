package com.github.unassignedxd.voidutils.main.recipe;

import com.github.unassignedxd.voidutils.api.recipe.VoidInfusionRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public class VoidInfusionRecipes {

    public static void initVoidRecipes() {
        new VoidInfusionRecipe(new ResourceLocation("test"),
                Arrays.asList(), new ItemStack(Items.DIAMOND), 0).register();
    }
}
