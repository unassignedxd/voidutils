package com.github.unassignedxd.voidutils.main.proxy;

import com.github.unassignedxd.voidutils.main.registry.ITESRProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

    void preInit(FMLPreInitializationEvent event);
    void init(FMLInitializationEvent event);
    void postInit(FMLPostInitializationEvent event);

    void registerRenderer(ItemStack item, ModelResourceLocation resourceLocation);
    void registerTESR(ITESRProvider provider);

    void scheduleTask(Runnable runnable);

    void spawnInfusionParticle(double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int color, float alpha, float scale,
                               int maxAge, float gravity, boolean collision, boolean fade);
}
