package com.github.unassignedxd.voidutils.main.proxy;

import com.github.unassignedxd.voidutils.main.events.ClientEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


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
    public void registerRenders(ItemStack stack, ModelResourceLocation location) {
        ClientEvents.OBJECT_MODEL_REG.put(stack, location);
    }

    @SubscribeEvent
    public void registerParticleTexture(ResourceLocation location) {
        ClientEvents.PARTICLE_TEXTURES.add(location);
    }

    @Override
    public void scheduleSidedTask(Runnable runnable) {
        Minecraft.getMinecraft().addScheduledTask(runnable);
    }

}
