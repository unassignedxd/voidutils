package com.github.unassignedxd.voidutils.main;

import com.github.unassignedxd.voidutils.main.block.ModBlocks;
import com.github.unassignedxd.voidutils.main.init.ObjectRegistryHandler;
import com.github.unassignedxd.voidutils.main.item.ModItems;
import com.github.unassignedxd.voidutils.main.proxy.IProxy;
import com.github.unassignedxd.voidutils.main.world.WorldOreGenerator;
import com.github.unassignedxd.voidutils.main.world.WorldStructureGenerator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

/**
 * This code is under the GNU General Public License v3.0
 * You can see more licensing information at:
 * https://github.com/unassignedxd/voidutils/blob/master/LICENSE
 * ---
 * Copyright © 2018-2019 unassigned
 * ---
 * Created on 12/30/2018 for voidutils
 */
@Mod(modid = VoidUtils.MOD_ID, name = VoidUtils.NAME, version = VoidUtils.VERSION)
public class VoidUtils {

    public static final String MOD_ID = "voidutils";
    public static final String NAME = "Void Utils";
    public static final String VERSION = "0.0.1"; //r b a

    @Mod.Instance
    public static VoidUtils instance;

    @SidedProxy(clientSide = "com.github.unassignedxd.voidutils.main.proxy.ClientProxy", serverSide = "com.github.unassignedxd.voidutils.main.proxy.ServerProxy")
    public static IProxy proxy;

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.VOID_SHARD); //todo icon
        }
    };

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        new ModBlocks();
        new ModItems();

        ObjectRegistryHandler.preInit(event);
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        new WorldOreGenerator();
        new WorldStructureGenerator();

        ObjectRegistryHandler.init(event);
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        ObjectRegistryHandler.postInit(event);
        proxy.postInit(event);
    }
}
