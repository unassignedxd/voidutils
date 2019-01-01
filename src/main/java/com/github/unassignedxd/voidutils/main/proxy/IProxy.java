package com.github.unassignedxd.voidutils.main.proxy;

import com.github.unassignedxd.voidutils.main.registry.ITESRProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

    void preInit(FMLPreInitializationEvent event);
    void init(FMLPreInitializationEvent event);
    void postInit(FMLPreInitializationEvent event);

    void registerRenderer(ItemStack item, ModelResourceLocation resourceLocation);
    void registerTESR(ITESRProvider provider);

    void scheduleTask(Runnable runnable);

}
