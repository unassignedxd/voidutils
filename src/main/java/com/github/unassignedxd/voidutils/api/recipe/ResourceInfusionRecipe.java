package com.github.unassignedxd.voidutils.api.recipe;

import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ResourceInfusionRecipe {

    public final ResourceLocation name;
    public final Ingredient input;
    public final Ingredient modifier1;
    public final Ingredient modifier2;
    public final Ingredient modifier3;
    public final Ingredient modifier4;
    public final Ingredient modifier5;
    public final Ingredient modifier6;
    public final Ingredient modifier7;
    public final Ingredient modifier8;

    public final ItemStack output;
    public final int energyUse;

    public ResourceInfusionRecipe(ResourceLocation name, Ingredient input,
                                  Ingredient modifier1, Ingredient modifier2, Ingredient modifier3, Ingredient modifier4, Ingredient modifier5, Ingredient modifier6, Ingredient modifier7, Ingredient modifier8,
                                  ItemStack output, int energyUse) {
        this.name = name;
        this.input = input;

        this.modifier1 = modifier1;
        this.modifier2 = modifier2;
        this.modifier3 = modifier3;
        this.modifier4 = modifier4;
        this.modifier5 = modifier5;
        this.modifier6 = modifier6;
        this.modifier7 = modifier7;
        this.modifier8 = modifier8;

        this.output = output;
        this.energyUse = energyUse;

    }

    public ResourceInfusionRecipe registerRecipe() {
        VoidUtilsAPI.RESOURCE_INFUSION_RECIPES.put(this.name, this);
        return this;
    }

    public boolean matches(ItemStack in, ItemStack infuser1, ItemStack infuser2, ItemStack infuser3, ItemStack infuser4,
                           ItemStack infuser5,ItemStack infuser6,ItemStack infuser7,ItemStack infuser8){

        if (!input.apply(in)) return false;
        List<Ingredient> matches = new ArrayList<>();
        ItemStack[] stacks = { infuser1, infuser2, infuser3, infuser4, infuser5, infuser6, infuser7, infuser8 };
        boolean[] unused = { true, true, true, true, true, true, true, true };
        for (ItemStack s : stacks) {
            if (unused[0] && modifier1.apply(s)) {
                matches.add(modifier1);
                unused[0] = false;
            } else if (unused[1] && modifier2.apply(s)) {
                matches.add(modifier2);
                unused[1] = false;
            } else if (unused[2] && modifier3.apply(s)) {
                matches.add(modifier3);
                unused[2] = false;
            } else if (unused[3] && modifier4.apply(s)) {
                matches.add(modifier4);
                unused[3] = false;
            } else if (unused[4] && modifier5.apply(s)) {
                matches.add(modifier2);
                unused[4] = false;
            } else if (unused[5] && modifier6.apply(s)) {
                matches.add(modifier3);
                unused[5] = false;
            } else if (unused[6] && modifier7.apply(s)) {
                matches.add(modifier4);
                unused[6] = false;
            } else if (unused[7] && modifier8.apply(s)) {
                matches.add(modifier4);
                unused[7] = false;
            }
        }

        return matches.size() == 8;

    }
}
