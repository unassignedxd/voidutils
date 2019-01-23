package com.github.unassignedxd.voidutils.main;

import com.github.unassignedxd.voidutils.main.proxy.IProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
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
    public static final String VERSION = "0.0.1"; //r a b

    @Mod.Instance
    public static VoidUtils instance;
    @SidedProxy(clientSide = "com.github.unassignedxd.voidutils.main.proxy.ClientProxy", serverSide = "com.github.unassignedxd.voidutils.main.proxy.ServerProxy")
    public static IProxy proxy;

    public static Logger logger;
    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Blocks.BEDROCK); //todo icon
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit(event);
    }
}
