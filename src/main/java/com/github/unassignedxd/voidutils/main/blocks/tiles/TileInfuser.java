package com.github.unassignedxd.voidutils.main.blocks.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TileInfuser extends TileBase {

    public final ItemStackHandler inv = new ItemStackHandlerCustom(1, this, true) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public ItemStack getStackInSlot() { return this.inv.getStackInSlot(0); }

    @Override
    public IItemHandlerModifiable getItemHandler(EnumFacing facing) {
        return this.inv;
    }
}
