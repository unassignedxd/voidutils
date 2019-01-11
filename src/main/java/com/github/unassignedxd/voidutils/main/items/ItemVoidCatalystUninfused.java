package com.github.unassignedxd.voidutils.main.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemVoidCatalystUninfused extends ItemBase {

    protected int tier;

    public ItemVoidCatalystUninfused(String name, int tier){
        super(name);
        this.setMaxStackSize(1);
        this.tier = tier;
    }

    public int getTier() {
        return this.tier;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
        if(!stack.isEmpty()) {
            tooltip.add("Catalyst Tier: " + this.tier);
        }
    }
}
