package com.github.unassignedxd.voidutils.main.util.infusion;

import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import com.github.unassignedxd.voidutils.api.infusion.modifier.IVoidModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class VoidModifier implements IVoidModifier {

    protected ResourceLocation name;
    protected ItemStack modifier;
    protected double voidAmount;
    protected double powerAmount;
    protected double depletionAmount;
    protected int energyUse;

    public VoidModifier(ResourceLocation name, ItemStack modifier, double voidAmount, double powerAmount, double depletionAmount, int energyUse){
        this.name = name;
        this.modifier = modifier;
        this.voidAmount = voidAmount;
        this.powerAmount = powerAmount;
        this.depletionAmount = depletionAmount;
        this.energyUse = energyUse;
    }

    public void registerModifier(){
        VoidUtilsAPI.VOID_INFUSION_MODIFIERS.put(name, this);
    }

    @Override
    public ResourceLocation getName() {
        return this.name;
    }

    @Override
    public ItemStack getItem() {
        return this.modifier;
    }

    @Override
    public double getVoidAmount() {
        return this.voidAmount;
    }

    @Override
    public double getPowerAmount() {
        return this.powerAmount;
    }

    @Override
    public double getDepletionAmount() {
        return this.depletionAmount;
    }

    @Override
    public int getEnergyUse() {
        return this.energyUse;
    }
}
