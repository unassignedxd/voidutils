package com.github.unassignedxd.voidutils.main.blocks.tiles;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ItemStackHandlerCustom extends ItemStackHandler {

    private final TileBase tileBase;
    private final boolean sendToClients;

    public ItemStackHandlerCustom(int slotAmount) {
        this(slotAmount, null, false);
    }

    public ItemStackHandlerCustom(int slotAmount, TileBase tileBase, boolean sendToClients){
        super(slotAmount);
        this.tileBase = tileBase;
        this.sendToClients = sendToClients;
    }

    @Override
    protected void onContentsChanged(int slot) {
        if(this.tileBase != null){
            this.tileBase.markDirty();;
            if(this.sendToClients && !this.tileBase.getWorld().isRemote){
                this.tileBase.sendData();
            }
        }
    }

    protected boolean canInsert(ItemStack stack, int slot) {
        return true;
    }

    protected boolean canExtract(ItemStack stack, int slot, int amount) {
        return true;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return this.canInsert(stack, slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (this.canInsert(stack, slot)) {
            return super.insertItem(slot, stack, simulate);
        } else {
            return stack;
        }
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (this.canExtract(this.getStackInSlot(slot), slot, amount)) {
            return super.extractItem(slot, amount, simulate);
        } else {
            return ItemStack.EMPTY;
        }
    }
}
