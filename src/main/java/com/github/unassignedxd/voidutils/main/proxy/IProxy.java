package com.github.unassignedxd.voidutils.main.proxy;

import com.github.unassignedxd.voidutils.main.particles.particle.base.EnumParticleType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface IProxy {

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void registerRenders(ItemStack stack, ModelResourceLocation location);

    void registerParticleTexture(ResourceLocation location);

    void spawnParticle(EnumParticleType type, double posX, double posY, double posZ,
                       double motionX, double motionY, double motionZ,
                       float alpha, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade);

    void scheduleSidedTask(Runnable runnable);
}
