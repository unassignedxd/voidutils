package com.github.unassignedxd.voidutils.main.init;

import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystRecipe;
import com.github.unassignedxd.voidutils.main.VoidConfig;
import com.github.unassignedxd.voidutils.main.util.ModHelper;
import com.github.unassignedxd.voidutils.main.util.infusion.ResourceCatalyst;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.util.infusion.VoidModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class ModRecipes {

    public static void init(){
        /**
         * Catalyst recipes:
         */
        new ResourceCatalystRecipe(new ResourceLocation(VoidUtils.MOD_ID, "crystalrecipe_resource_IRON"), convert(new ItemStack(Blocks.IRON_BLOCK)),
               convert(new ItemStack(Items.IRON_INGOT)), convert(new ItemStack(Items.IRON_INGOT)), convert(new ItemStack(Items.IRON_INGOT)),
                                convert(new ItemStack(Items.IRON_INGOT)), convert(new ItemStack(Items.NETHER_STAR)), convert(new ItemStack(Items.NETHER_STAR)), convert(ItemStack.EMPTY), convert(ItemStack.EMPTY),
                new ResourceCatalyst(new ItemStack(Items.IRON_INGOT), 500, 0.01), 50000, 0).registerRecipe();

        /**
         * Void Infusion Modifiers
         */
        if(VoidConfig.general.resourceCatalystDefaultRecipe) addDefaultCatalystRecipes();

        new VoidModifier(new ResourceLocation(VoidUtils.MOD_ID, "modifier_netherStar"), new ItemStack(Items.NETHER_STAR), 5, 5, 5, 100).registerModifier();

    }

    private static Ingredient convert(ItemStack stack){
        return Ingredient.fromStacks(stack);
    }

    /**
     * Looped Recipes
     */
    private static void addDefaultCatalystRecipes() {
        NonNullList<ItemStack> cataTypes = ModHelper.getAcceptableCatalystTypes();

        for(ItemStack item : cataTypes){ //todo for all -> change INPUT to a special void ingredient. [4 uninfused void catalyst, etc]
            new ResourceCatalystRecipe(new ResourceLocation(VoidUtils.MOD_ID, "crystalrecipe_resource_"+(item.getItem().toString()).toUpperCase()), convert(item.copy()),
                    convert(item.copy()), convert(item.copy()), convert(item.copy()), convert(item.copy()), convert(item.copy()), convert(item.copy()), convert(new ItemStack(Items.NETHER_STAR)), convert(new ItemStack(Items.NETHER_STAR)),
                    new ResourceCatalyst(item.copy(), 15000, 0.01), 50000, 0).registerRecipe();
        }
    }
}
