package com.github.unassignedxd.voidutils.main.item;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface IModItem {

    default void registerRenderers(){
        ItemStack stack = new ItemStack((Item) this);
        VoidUtils.proxy.registerRenders(stack, new ModelResourceLocation(stack.getItem().getRegistryName(), "inventory"));
    }

}
