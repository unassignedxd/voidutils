package com.github.unassignedxd.voidutils.main.proxy;

import com.github.unassignedxd.voidutils.main.client.particles.particle.base.EnumParticleType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void registerRenders(ItemStack stack, ModelResourceLocation location) {

    }

    @Override
    public void registerParticleTexture(ResourceLocation location) {

    }

    @Override
    public void spawnParticle(EnumParticleType type, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float alpha, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {

    }

    @Override
    public void scheduleSidedTask(Runnable runnable) {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(runnable);
    }

}
