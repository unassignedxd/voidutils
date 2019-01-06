package com.github.unassignedxd.voidutils.main.init;

import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystInfusionRecipe;
import com.github.unassignedxd.voidutils.main.util.infusion.ResourceCatalyst;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.util.infusion.VoidModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ModRecipes {

    public static void init(){
        /**
         * Catalyst recipes:
         */
        new ResourceCatalystInfusionRecipe(new ResourceLocation(VoidUtils.MOD_ID, "resourcecrystal_IRON"), convert(new ItemStack(Blocks.IRON_BLOCK)),
               convert(new ItemStack(Items.IRON_INGOT)), convert(new ItemStack(Items.IRON_INGOT)), convert(new ItemStack(Items.IRON_INGOT)),
                                convert(new ItemStack(Items.IRON_INGOT)), convert(new ItemStack(Items.NETHER_STAR)), convert(new ItemStack(Items.NETHER_STAR)), convert(ItemStack.EMPTY), convert(ItemStack.EMPTY),
                new ResourceCatalyst(new ItemStack(Items.IRON_INGOT), 500, 0.01), 50000).registerRecipe();

        /**
         * Void Infusion Modifiers
         */
        new VoidModifier(new ResourceLocation(VoidUtils.MOD_ID, "modifier_netherStar"), new ItemStack(Items.NETHER_STAR), 5, 5, 5, 100).registerModifier();

    }

    private static Ingredient convert(ItemStack stack){
        return Ingredient.fromStacks(stack);
    }
}
