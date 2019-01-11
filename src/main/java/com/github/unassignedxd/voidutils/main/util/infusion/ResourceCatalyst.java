package com.github.unassignedxd.voidutils.main.util.infusion;

import net.minecraft.item.ItemStack;

public class ResourceCatalyst {

    protected ItemStack resourceDupe;
    protected int dupeRate;
    protected int powerUseAmount;
    protected double depletionRate;

    public ResourceCatalyst(ItemStack resourceDupe, int dupeRate, int powerUseAmount, double depletionRate){
        this.resourceDupe = resourceDupe;
        this.dupeRate = dupeRate;
        this.powerUseAmount = powerUseAmount;
        this.depletionRate = depletionRate;
    }

    public double getDepletionRate() {
        return depletionRate;
    }

    public int getPowerUseAmount() {
        return powerUseAmount;
    }

    public int getDupeRate() { return dupeRate; }

    public ItemStack getResourceDupe() {
        return resourceDupe;
    }
}
