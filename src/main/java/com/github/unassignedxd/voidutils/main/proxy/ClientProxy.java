package com.github.unassignedxd.voidutils.main.proxy;

import com.github.unassignedxd.voidutils.main.events.ClientEvents;
import com.github.unassignedxd.voidutils.main.particles.ParticleHandler;
import com.github.unassignedxd.voidutils.main.particles.ParticleInfuse;
import com.github.unassignedxd.voidutils.main.registry.ITESRProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.function.Supplier;

public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void registerRenderer(ItemStack item, ModelResourceLocation resourceLocation) {
        ModelLoader.setCustomModelResourceLocation(item.getItem(), item.getItemDamage(), resourceLocation);
    }

    @Override
    public void registerTESR(ITESRProvider provider) {
        Tuple<Class, TileEntitySpecialRenderer> tuple = provider.getTESR();
        ClientRegistry.bindTileEntitySpecialRenderer(tuple.getFirst(), tuple.getSecond());
    }

    @Override
    public void scheduleTask(Runnable runnable) {
        Minecraft.getMinecraft().addScheduledTask(runnable);
    }

    @Override
    public void spawnInfusionParticle(double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int color, float alpha, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        ParticleHandler.spawnParticle(() -> new ParticleInfuse(Minecraft.getMinecraft().world,
                posX, posY, posZ, motionX, motionY, motionZ,
                color, alpha, scale, maxAge, gravity, collision, fade), posX, posY, posZ, 32);
    }
}
