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

        registerCatalystTypes(); // these will register the custom catalyst types for different ingots and gems.

        /**
         * Void Infusion Modifiers
         */
        new VoidModifier(new ResourceLocation(VoidUtils.MOD_ID, "modifier_netherStar"), new ItemStack(Items.NETHER_STAR), 5, 5, 5, 100).registerModifier();

    }

    /**
     * Default types: Coal, Iron, Gold, Diamond, Emerald, Nether Star
     * Modded Types: Copper, Tin, Silver, Lead, Aluminium, Nickel, Platinum, Iridium, Mana Infused, Steel, Electrum, Invar, Bronze, Constantan, Signalum, Lumium, Enderium
     */
    private static void registerCatalystTypes() {
        // -- Coal -- [Tier 1]
        catalystCreatorHelper(new ResourceLocation(VoidUtils.MOD_ID, "resourceRecipe_coal"), new ItemStack(ModItems.CRAFTING_CATALYST_TIER_1),
                new ItemStack[]{new ItemStack(Items.COAL), new ItemStack(Items.COAL), new ItemStack(Items.COAL), new ItemStack(Items.COAL),
                        new ItemStack(Items.COAL), new ItemStack(Items.COAL), new ItemStack(Blocks.COAL_BLOCK), new ItemStack(Items.NETHER_STAR)},
                new ResourceCatalyst(new ItemStack(Items.COAL), 10, 5600, 0.01), 750000, 000000);

        // -- Iron -- [Tier 2]
        catalystCreatorHelper(new ResourceLocation(VoidUtils.MOD_ID, "resourceRecipe_iron"), new ItemStack(ModItems.CRAFTING_CATALYST_TIER_2),
                new ItemStack[]{new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT),
                        new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.IRON_BLOCK), new ItemStack(Items.NETHER_STAR)},
                new ResourceCatalyst(new ItemStack(Items.IRON_INGOT), 20, 10500, 0.025), 1200000, 696969);

        // -- Gold -- [Tier 3]
        catalystCreatorHelper(new ResourceLocation(VoidUtils.MOD_ID, "resourceRecipe_gold"), new ItemStack(ModItems.CRAFTING_CATALYST_TIER_3),
                new ItemStack[]{new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT),
                        new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Items.NETHER_STAR)},
                new ResourceCatalyst(new ItemStack(Items.GOLD_INGOT), 40, 10500, 0.045), 5500000, 16776960);

        // -- Diamond -- [Tier 4]
        catalystCreatorHelper(new ResourceLocation(VoidUtils.MOD_ID, "resourceRecipe_diamond"), new ItemStack(ModItems.CRAFTING_CATALYST_TIER_4),
                new ItemStack[]{new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.DIAMOND), new ItemStack(Items.DIAMOND), new ItemStack(Blocks.DIAMOND_BLOCK), new ItemStack(Items.NETHER_STAR)},
                new ResourceCatalyst(new ItemStack(Items.DIAMOND), 60, 87000, 0.075), 12000000, 47615);

        // -- Emerald -- [Tier 4]
        catalystCreatorHelper(new ResourceLocation(VoidUtils.MOD_ID, "resourceRecipe_emerald"), new ItemStack(ModItems.CRAFTING_CATALYST_TIER_4),
                new ItemStack[]{new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT),
                        new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.GOLD_INGOT), new ItemStack(Blocks.EMERALD_BLOCK), new ItemStack(Items.NETHER_STAR)},
                new ResourceCatalyst(new ItemStack(Items.EMERALD), 60, 87000, 0.075), 12000000, 52336);

        // -- Nether Star -- [Tier 5]
        catalystCreatorHelper(new ResourceLocation(VoidUtils.MOD_ID, "resourceRecipe_netherStar"), new ItemStack(ModItems.CRAFTING_CATALYST_TIER_5),
                new ItemStack[]{new ItemStack(Items.NETHER_STAR), new ItemStack(Items.NETHER_STAR), new ItemStack(Items.NETHER_STAR), new ItemStack(Items.NETHER_STAR),
                        new ItemStack(Items.NETHER_STAR), new ItemStack(Items.NETHER_STAR), new ItemStack(Items.NETHER_STAR), new ItemStack(Items.NETHER_STAR)},
                new ResourceCatalyst(new ItemStack(Items.NETHER_STAR), 120, 250000, 0.15), 550000000, 13369343);
    }

    /**
     * Very basic helper function, allows an array to be passed in for the modifiers, however the length of the array MUST be 8.
     */
    private static void catalystCreatorHelper(ResourceLocation name, ItemStack input, ItemStack[] modifiers, ResourceCatalyst output, int energyUse, int particleColor) {
        new ResourceCatalystRecipe(name, input, modifiers[0], modifiers[1], modifiers[2], modifiers[3], modifiers[4], modifiers[5], modifiers[6], modifiers[7], output, energyUse, particleColor).registerRecipe();
    }

    private static Ingredient convert(ItemStack stack){
        return Ingredient.fromStacks(stack);
    }

}
