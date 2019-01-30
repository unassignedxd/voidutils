package com.github.unassignedxd.voidutils.main.proxy;

import com.github.unassignedxd.voidutils.main.block.functional.voidnode.RenderVoidNode;
import com.github.unassignedxd.voidutils.main.block.functional.voidnode.TileVoidNode;
import com.github.unassignedxd.voidutils.main.events.ClientEvents;
import com.github.unassignedxd.voidutils.main.client.particles.ParticleHandler;
import com.github.unassignedxd.voidutils.main.client.particles.particle.base.EnumParticleType;
import com.github.unassignedxd.voidutils.main.client.particles.particle.ParticleVoidNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ClientProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
        ClientRegistry.bindTileEntitySpecialRenderer(TileVoidNode.class, new RenderVoidNode());
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
    public void spawnParticle(EnumParticleType particleType, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float alpha, int color, float scale, int maxAge, float gravity, boolean collision, boolean fade) {
        Minecraft mc = Minecraft.getMinecraft();
        switch (particleType) {
            case VOID_NODE:
                ParticleHandler.spawnParticle(() -> new ParticleVoidNode(mc.world,
                        posX, posY, posZ,
                        motionX, motionY, motionZ,
                        alpha, color, scale, maxAge, gravity, collision, fade), posX, posY, posZ, 32);
                break;
        }
    }

    @Override
    public void scheduleSidedTask(Runnable runnable) {
        Minecraft.getMinecraft().addScheduledTask(runnable);
    }

}
