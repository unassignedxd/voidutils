package com.github.unassignedxd.voidutils.main;

import com.github.unassignedxd.voidutils.main.chunk.voidenergy.CapabilityVoidEnergy;
import com.github.unassignedxd.voidutils.main.network.PacketHandler;
import com.github.unassignedxd.voidutils.main.proxy.IProxy;
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
    public static final String VERSION = "1.0";

    public static VoidUtils instance;

    @SidedProxy(clientSide = "com.github.unassignedxd.voidutils.main.proxy.ClientProxy", serverSide = "com.github.unassignedxd.voidutils.main.proxy.ServerProxy")
    public static IProxy proxy;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        CapabilityVoidEnergy.registerCap();
        PacketHandler.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }
}
