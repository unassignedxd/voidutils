package com.github.unassignedxd.voidutils.main.registry;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.Map;

public class RegistryHandler {

    private static final ArrayList<IModItem> ITEMS = new ArrayList<>();

    public static void addItemToRegister(IModItem item) { ITEMS.add(item);  }

    private static void registerItem(Item item, String name){
        item.setTranslationKey(VoidUtils.MOD_ID + "." + name);
        item.setRegistryName(VoidUtils.MOD_ID, name);
        item.setCreativeTab(VoidUtils.CREATIVE_TAB);

        ForgeRegistries.ITEMS.register(item);
    }

    private static void registerBlock(Block block, String name, ItemBlock itemBlock){
        block.setTranslationKey(VoidUtils.MOD_ID + "." + name);
        block.setRegistryName(VoidUtils.MOD_ID, name);
        block.setCreativeTab(VoidUtils.CREATIVE_TAB);


        ForgeRegistries.BLOCKS.register(block);

        if(itemBlock != null){
            itemBlock.setRegistryName(block.getRegistryName());
            ForgeRegistries.ITEMS.register(itemBlock);
        }

    }

    public static void preInit(FMLPreInitializationEvent event){
        for(IModItem item : ITEMS) {

            if(item instanceof Item) {
                registerItem((Item)item, item.getUnlocalizedName());
            }
            else if(item instanceof Block) {
                Block block = (Block)item;
                registerBlock(block, item.getUnlocalizedName(), new ItemBlock(block));
            }

            if(item instanceof IModelProvider){
                Map<ItemStack, ModelResourceLocation> models = ((IModelProvider)item).getItemStackModelLocations();
                for(ItemStack stack : models.keySet())
                    VoidUtils.proxy.registerRenderer(stack, models.get(stack));
            }

            item.preInit(event);
        }
        VoidUtils.logger.info("Successfully registered items!");
    }

    public static void init(FMLInitializationEvent event){
        for(IModItem item : ITEMS) {
            if(item instanceof ITESRProvider) VoidUtils.proxy.registerTESR((ITESRProvider) item);

            item.init(event);
        }
    }

    public static void postInit(FMLPostInitializationEvent event){
        for(IModItem item : ITEMS) {
            item.postInit(event);
        }
    }
}
