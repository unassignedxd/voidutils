package com.github.unassignedxd.voidutils.main.block.base.tile;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ItemHandlerCustom extends ItemStackHandler {

    private final TileBase tileClass;
    private final boolean sendDataToClient;

    public ItemHandlerCustom(int invSize) {
        this(invSize, null, false);
    }

    public ItemHandlerCustom(int size, TileBase tileClass, boolean sendDataToClient) {
        super(size);
        this.tileClass = tileClass;
        this.sendDataToClient = sendDataToClient;
    }

    protected boolean canInsert(ItemStack attemptedStack, int slot){
        return true;
    }

    protected boolean canExtract(ItemStack attemptedStack, int slot, int amount) {
        return true;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return this.canInsert(stack, slot);
    }

    @Override
    protected void onContentsChanged(int slot) {
        if(this.tileClass != null){
            this.tileClass.markDirty();
            if(this.sendDataToClient && !this.tileClass.getWorld().isRemote)
                this.tileClass.sendData();
        }
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if(this.canExtract(this.getStackInSlot(slot), slot, amount))
            return super.extractItem(slot, amount, simulate);
        else
            return ItemStack.EMPTY;
    }
}
