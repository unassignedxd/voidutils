package com.github.unassignedxd.voidutils.main.inventory.container;

import com.github.unassignedxd.voidutils.main.blocks.tiles.TileBase;
import com.github.unassignedxd.voidutils.main.blocks.tiles.TileCatalyticInfuser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerCatalystInfuser extends Container {

    private final TileCatalyticInfuser machine;

    public ContainerCatalystInfuser(InventoryPlayer inventory, TileBase tile) {
        this.machine = (TileCatalyticInfuser)tile;

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 97+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 155));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }
}
