package com.github.unassignedxd.voidutils.main.init;

import com.github.unassignedxd.voidutils.api.recipe.ResourceInfusionRecipe;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ModRecipes {

    public static void init(){
        new ResourceInfusionRecipe(new ResourceLocation(VoidUtils.MOD_ID, "resourcecrystal_IRON"), convert(new ItemStack(Blocks.IRON_BLOCK)),
               convert(new ItemStack(Items.IRON_INGOT)), convert(new ItemStack(Items.IRON_INGOT)), convert(new ItemStack(Items.IRON_INGOT)),
                                convert(new ItemStack(Items.IRON_INGOT)), convert(new ItemStack(Items.NETHER_STAR)), convert(new ItemStack(Items.NETHER_STAR)), convert(ItemStack.EMPTY), convert(ItemStack.EMPTY),
                new ItemStack(ModItems.RESOURCE_CATALYST_IRON), 50000).registerRecipe();
    }

    private static Ingredient convert(ItemStack stack){
        return Ingredient.fromStacks(stack);
    }
}
