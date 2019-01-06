package com.github.unassignedxd.voidutils.main.inventory;

import com.github.unassignedxd.voidutils.main.blocks.tiles.TileBase;
import com.github.unassignedxd.voidutils.main.inventory.container.ContainerCatalystInfuser;
import com.github.unassignedxd.voidutils.main.inventory.gui.GuiCatalystInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    /* This should return a Container for the server to open. */
    @Nullable
    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        TileBase tile = null;
        if(GuiTypes.values()[i].checkTile){
            tile = (TileBase)world.getTileEntity(new BlockPos(x,y,z));
        }
        switch(GuiTypes.values()[i]){
            case CRYSTAL_INFUSER:
                return new ContainerCatalystInfuser(entityPlayer.inventory, tile);
            default:
                return null;
        }
    }

    /* This should return a GUI for the client to open. */
    @Nullable
    @Override
    public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int x, int y, int z) {
        TileBase tile = null;
        if(GuiTypes.values()[i].checkTile){
            tile = (TileBase)world.getTileEntity(new BlockPos(x,y,z));
        }
        switch(GuiTypes.values()[i]){
            case CRYSTAL_INFUSER:
                return new GuiCatalystInfuser(entityPlayer.inventory, tile);
        }
        return null;
    }

    public enum GuiTypes {
        CRYSTAL_INFUSER;

        public final boolean checkTile;

        GuiTypes(boolean checkTile) { this.checkTile = checkTile;}
        GuiTypes(){ this(true); }
    }
}
