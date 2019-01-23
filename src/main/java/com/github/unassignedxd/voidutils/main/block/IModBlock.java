package com.github.unassignedxd.voidutils.main.block;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public interface IModBlock {

    default void registerRenderers(){
        ItemStack stack = new ItemStack((Block) this);
        VoidUtils.proxy.registerRenders(stack, new ModelResourceLocation(stack.getItem().getRegistryName(), "inventory"));
    }

}
