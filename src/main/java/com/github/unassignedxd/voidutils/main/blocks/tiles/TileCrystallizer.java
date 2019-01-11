package com.github.unassignedxd.voidutils.main.blocks.tiles;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.init.ModBlocks;
import com.github.unassignedxd.voidutils.main.items.ItemResourceCatalyst;
import com.github.unassignedxd.voidutils.main.items.ItemVoidCatalyst;
import com.github.unassignedxd.voidutils.main.util.EnergyStorageCustom;
import com.github.unassignedxd.voidutils.main.util.ModHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileCrystallizer extends TileBase {

    public ItemStackHandlerCustom inv = new ItemStackHandlerCustom(2, this, false) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        protected boolean canInsert(ItemStack stack, int slot) {
            return (stack.getItem() instanceof ItemResourceCatalyst || stack.getItem() instanceof ItemVoidCatalyst);
        }
    };

    public final EnergyStorageCustom energyStorage = new EnergyStorageCustom(10000000, 10000, 10000);
    public static final int ENERGY_USE = 10000; //rf / t

    public int processTime;
    public int maxProcessTime = 600;

    @Override
    public void update() {
        super.update();

        if(!this.world.isRemote) {
            ItemStack input = this.inv.getStackInSlot(0); //0 input, 1 output

            if(this.inv.getStackInSlot(1).isEmpty() && isValid(input) && this.energyStorage.getEnergyStored()-ENERGY_USE > 0) {
                this.processTime++;
                this.energyStorage.extractEnergy(ENERGY_USE, false);

                boolean done = this.processTime >= this.maxProcessTime;

                if(done) {
                    if(input.getItem() instanceof ItemResourceCatalyst)
                        this.inv.setStackInSlot(1, createResourceCrystal(input));
                    else if(input.getItem() instanceof ItemVoidCatalyst)
                        this.inv.setStackInSlot(1, createVoidCrystal(input));
                }
            }else {
                processTime--;
            }
        } else { this.processTime = 0; }
    }

    private ItemStack createVoidCrystal(ItemStack input){
        ItemStack returnStack = new ItemStack(ModBlocks.CRYSTAL);
        NBTTagCompound inputCompound = input.getTagCompound();
        if(inputCompound == null) { VoidUtils.logger.error("An NBT error has occured when trying to write catalytic data to a crystal! This should not happen!"); return ItemStack.EMPTY; }

        if(!inputCompound.hasKey("VoidAmount") || !inputCompound.hasKey("PowerAmount") || !inputCompound.hasKey("DepletionRateAmount") || !inputCompound.hasKey("EnergyUse")){
            VoidUtils.logger.error("An error occured when trying to check for valid catalytic data. This should not happen!");
            return ItemStack.EMPTY;
        }

        NBTTagCompound compound = new NBTTagCompound();

        compound.setDouble("VoidAmount", inputCompound.getDouble("VoidAmount"));
        compound.setDouble("PowerAmount", inputCompound.getDouble("PowerAmount"));
        compound.setDouble("DepletionRateAmount", inputCompound.getDouble("DepletionRateAmount"));
        compound.setDouble("Depletion", 0);
        compound.setInteger("EnergyUse", inputCompound.getInteger("EnergyUse"));

        ModHelper.applyData(returnStack, compound);

        return returnStack;
    }

    private ItemStack createResourceCrystal(ItemStack input) {
        ItemStack returnStack = new ItemStack(ModBlocks.CRYSTAL);
        NBTTagCompound inputCompound = input.getTagCompound();
        if(inputCompound == null) { VoidUtils.logger.error("An NBT error has occured when trying to write catalytic data to a crystal! This should not happen!"); return ItemStack.EMPTY; }

        if(!inputCompound.hasKey("EnergyUse") || !inputCompound.hasKey("DepletionRate") || !inputCompound.hasKey("ProcessRate") ||!inputCompound.hasKey("id")){
            VoidUtils.logger.error("An error occured when trying to check for valid catalytic data. This should not happen!");
            return ItemStack.EMPTY;
        }

        NBTTagCompound compound = new NBTTagCompound();

        compound.setTag("id", inputCompound.getTag("id"));
        compound.setDouble("DepletionRate", inputCompound.getDouble("DepletionRate"));
        compound.setDouble("Depletion", 0);
        compound.setInteger("EnergyUse", inputCompound.getInteger("EnergyUse"));
        compound.setDouble("ProcessRate", inputCompound.getDouble("ProcessRate"));

        ModHelper.applyData(returnStack, compound);

        return returnStack;
    }

    //Saftey incase somehow the player manages to get anything but the catalysts in this inventory.
    private boolean isValid(ItemStack stack) { return stack.getItem() instanceof ItemResourceCatalyst || stack.getItem() instanceof ItemVoidCatalyst; }

    private void resetMachine() {
        this.processTime=0;
    }

    @Override
    public IItemHandlerModifiable getItemHandler(EnumFacing facing) {
        return this.inv;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.energyStorage;
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
