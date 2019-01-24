package com.github.unassignedxd.voidutils.main.init;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IModObject {

    void preInit(FMLPreInitializationEvent event);
    void init(FMLInitializationEvent event);
    void postInit(FMLPostInitializationEvent event);

    String getBaseName();

    default void registerRenderers() {
        ItemStack stack = this instanceof Block ? new ItemStack((Block) this) : new ItemStack((Item) this);
        try {
            VoidUtils.proxy.registerRenders(stack, new ModelResourceLocation(stack.getItem().getRegistryName(), "inventory"));
        } catch (NullPointerException e){
            VoidUtils.logger.fatal("Failed to register object!", e);
        }
    }

    default CreativeTabs getDisplayTab() {
        return VoidUtils.CREATIVE_TAB;
    }
}
