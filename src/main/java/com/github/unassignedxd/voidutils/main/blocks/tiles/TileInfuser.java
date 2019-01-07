package com.github.unassignedxd.voidutils.main.blocks.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

    public boolean hasItemStack() { return this.inv.getStackInSlot(0) != ItemStack.EMPTY; }

    public ItemStack getStackInSlot() { return this.inv.getStackInSlot(0); }

    @Override
    public IItemHandlerModifiable getItemHandler(EnumFacing facing) {
        return this.inv;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, SaveType type) {
        super.readFromNBT(compound, type);

        if(type != SaveType.SAVE_BLOCK){
            this.inv.deserializeNBT(compound.getCompoundTag("Items"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, SaveType type) {
        super.writeToNBT(compound, type);
        if(type != SaveType.SAVE_BLOCK){
            compound.setTag("Items", inv.serializeNBT());
        }
    }

}
