package com.github.unassignedxd.voidutils.main.proxy;

import com.github.unassignedxd.voidutils.main.registry.ITESRProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
}
