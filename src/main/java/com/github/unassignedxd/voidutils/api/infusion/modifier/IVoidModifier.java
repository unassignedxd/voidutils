package com.github.unassignedxd.voidutils.api.infusion.modifier;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * This is applied to items that stores the modifier types.
 */
public interface IVoidModifier {
    ResourceLocation getName();
    ItemStack getItem();
    double getVoidAmount();
    double getPowerAmount();
    double getDepletionAmount();
    int getEnergyUse();

}
