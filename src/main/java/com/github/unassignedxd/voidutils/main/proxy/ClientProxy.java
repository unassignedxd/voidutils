package com.github.unassignedxd.voidutils.main.proxy;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.block.ModBlocks;
import com.github.unassignedxd.voidutils.main.init.IModObject;
import com.github.unassignedxd.voidutils.main.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ClientProxy implements IProxy {

    private static HashMap<ItemStack, ModelResourceLocation> OBJECT_MODEL_REG = new HashMap<>();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init(FMLInitializationEvent event) {

    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void registerRenders(ItemStack stack, ModelResourceLocation location) {
        OBJECT_MODEL_REG.put(stack, location);
    }

    @SubscribeEvent
    public void onModelRegistryEvent(ModelRegistryEvent event){
        for(Item item : ModItems.ITEMS){
            if(item instanceof IModObject){
                ((IModObject)item).registerRenderers();
            }
        }
        for(Block block : ModBlocks.BLOCKS){
            if(block instanceof IModObject){
                ((IModObject) block).registerRenderers();
            }
        }

        for(Map.Entry<ItemStack, ModelResourceLocation> entry : OBJECT_MODEL_REG.entrySet()){
            ModelLoader.setCustomModelResourceLocation(entry.getKey().getItem(), entry.getKey().getItemDamage(), entry.getValue());
        }

        VoidUtils.logger.info("Successfully Registered Models!");
    }
}
