package com.github.unassignedxd.voidutils.main.registry;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class for future implementations of TESR
 */
public interface ITESRProvider {

    @SideOnly(Side.CLIENT)
    Tuple<Class, TileEntitySpecialRenderer> getTESR();
}
