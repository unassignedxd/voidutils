package com.github.unassignedxd.voidutils.main.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLPreInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void registerRenderer(Item item) {

    }

    @Override
    public void scheduleTask(Runnable runnable) {
        Minecraft.getMinecraft().addScheduledTask(runnable);
    }
}
