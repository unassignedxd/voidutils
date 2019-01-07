package com.github.unassignedxd.voidutils.main.proxy;

import com.github.unassignedxd.voidutils.main.registry.ITESRProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) { }

    @Override
    public void init(FMLInitializationEvent event) { }

    @Override
    public void postInit(FMLPostInitializationEvent event) { }

    @Override
    public void registerRenderer(ItemStack item, ModelResourceLocation resourceLocation) { }

    @Override
    public void registerTESR(ITESRProvider provider) { }

    @Override
    public void scheduleTask(Runnable runnable) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(runnable);
    }

    @Override
    public void spawnInfusionParticle(double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int color, float alpha, float scale, int maxAge, float gravity, boolean collision, boolean fade) {

    }

}
