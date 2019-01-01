package com.github.unassignedxd.voidutils.main.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class CreativeTabVoidUtils extends CreativeTabs {

    public CreativeTabVoidUtils(String name) { super(name); }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Blocks.BEDROCK); //todo icon
    }
}
