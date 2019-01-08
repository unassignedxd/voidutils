package com.github.unassignedxd.voidutils.main.blocks.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

public class TileCrystalStand extends TileBase {

    public final ItemStackHandlerCustom inv = new ItemStackHandlerCustom(1, this, true) {
        @Override
        protected boolean canInsert(ItemStack stack, int slot) {
            return super.canInsert(stack, slot); //check for crystal insertion.
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    @Override
    public void update() {
        super.update();

        if(!this.world.isRemote){

        }
    }

    @Override
    public IItemHandlerModifiable getItemHandler(EnumFacing facing) {
        return this.inv;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, SaveType type) {
        super.writeToNBT(compound, type);
        inv.serializeNBT();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, SaveType type) {
        super.readFromNBT(compound, type);
        inv.deserializeNBT(compound);
    }
}
