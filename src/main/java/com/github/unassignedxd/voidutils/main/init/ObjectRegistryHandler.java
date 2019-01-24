package com.github.unassignedxd.voidutils.main.init;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.block.ModBlocks;
import com.github.unassignedxd.voidutils.main.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = VoidUtils.MOD_ID)
public class ObjectRegistryHandler {

    @SubscribeEvent
    public static void onBlockRegisterEvent(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IModObject) {
                block.setCreativeTab(((IModObject) block).getDisplayTab());
                registry.register(block);
            }
        }

        VoidUtils.logger.info("Registered Blocks!");
    }

    @SubscribeEvent
    public static void onItemRegisterEvent(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        for (Item item : ModItems.ITEMS) {
            if (item instanceof IModObject) {
                item.setCreativeTab(((IModObject) item).getDisplayTab());
                registry.register(item);
            }
        }

        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IModObject) {
                ItemBlock itemBlock = new ItemBlock(block);
                itemBlock.setRegistryName(block.getRegistryName());
                registry.register(itemBlock);
            }
        }

        VoidUtils.logger.info("Registered Items!");
    }

    public static void preInit(FMLPreInitializationEvent event) {
        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IModObject) {
                ((IModObject) block).preInit(event);
            }
        }

        for (Item item : ModItems.ITEMS) {
            if (item instanceof IModObject) {
                ((IModObject) item).preInit(event);
            }
        }
    }

    public static void init(FMLInitializationEvent event) {
        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IModObject) {
                ((IModObject) block).init(event);
            }
        }

        for (Item item : ModItems.ITEMS) {
            if (item instanceof IModObject) {
                ((IModObject) item).init(event);
            }
        }
    }

    public static void postInit(FMLPostInitializationEvent event) {
        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IModObject) {
                ((IModObject) block).postInit(event);
            }
        }

        for (Item item : ModItems.ITEMS) {
            if (item instanceof IModObject) {
                ((IModObject) item).postInit(event);
            }
        }
    }
}
