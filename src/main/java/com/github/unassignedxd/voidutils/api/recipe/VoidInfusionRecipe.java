package com.github.unassignedxd.voidutils.api.recipe;

import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class VoidInfusionRecipe {

    protected ResourceLocation name;
    protected List<ItemStack> inputs;
    protected ItemStack output;
    protected int voidUse;

    public VoidInfusionRecipe(ResourceLocation name, List<ItemStack> inputs, ItemStack output, int voidUse) {
        this.name = name;
        this.inputs = inputs;
        this.output = output;
        this.voidUse = voidUse;
    }

    public ResourceLocation getName() {
        return name;
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getVoidUse() {
        return voidUse;
    }

    public VoidInfusionRecipe register() {
        VoidUtilsAPI.VOID_INFUSION_RECIPES.put(this.name, this);
        return this;
    }
}
