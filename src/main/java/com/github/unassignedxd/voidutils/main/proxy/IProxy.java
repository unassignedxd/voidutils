package com.github.unassignedxd.voidutils.main.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

    void preInit(FMLPreInitializationEvent event);
    void init(FMLPreInitializationEvent event);
    void postInit(FMLPreInitializationEvent event);

    void registerRenderer(Item item);

    void scheduleTask(Runnable runnable);

}
