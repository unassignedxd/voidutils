package com.github.unassignedxd.voidutils.main.blocks.tiles;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.init.ModBlocks;
import com.github.unassignedxd.voidutils.main.util.EnergyStorageCustom;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandlerModifiable;

import java.util.ArrayList;

public class TileCrystalStand extends TileBase {

    public final ItemStackHandlerCustom inv = new ItemStackHandlerCustom(5, this, false) {
        @Override
        protected boolean canInsert(ItemStack stack, int slot) {
            return true;
        }

        @Override
        public int getSlotLimit(int slot) {
           if(slot == 0) { return 1; }
           else return 64;
        }
    };

    public static final int SLOT_CRYSTAL = 0;

    public final EnergyStorageCustom energyStorage = new EnergyStorageCustom(7500000, 500, 500); //todo custom capacity & input based on crystal use

    @Override
    public void update() {
        super.update();

        if(!this.world.isRemote){
            ItemStack stack = this.inv.getStackInSlot(SLOT_CRYSTAL);
            if(!stack.isEmpty() && canPutItems()) {
                if(stack.getItem() == Item.getItemFromBlock(ModBlocks.CRYSTAL)) {
                    if(stack.hasTagCompound()){
                        NBTTagCompound compound = stack.getTagCompound();
                        double depletion = compound.getInteger("Depletion");

                        if(depletion < 100) {
                            ArrayList<TileStabilizer> stabilizers = getNearbyStabilizers();

                            if(compound.hasKey("id")) { //Resource Crystal
                                this.processResourceCrystal(stack, compound, stabilizers);
                            }else if(compound.hasKey("VoidAmount")){ //Void Crystal
                                this.processVoidCrystal(stack, compound, stabilizers);
                            }
                        } else {
                            compound.setDouble("Depletion", 100);
                        }
                    }
                }
            }
        }
    }

    public void processResourceCrystal(ItemStack stack, NBTTagCompound stackCompound, ArrayList<TileStabilizer> stabilizers) {
        ItemStack dupeItem = new ItemStack(stackCompound);
        int energyUse = stackCompound.getInteger("EnergyUse");

        if(!dupeItem.isEmpty()) {
            if(this.energyStorage.getEnergyStored() - energyUse > 0) {
                this.energyStorage.extractEnergy(energyUse, false);

                if(ticksAlive % stackCompound.getInteger("ProcessRate") == 0) {
                    inv.insertItem(1, dupeItem, false);
                    stackCompound.setDouble("Depletion", (stackCompound.getDouble("Depletion") + stackCompound.getDouble("DepletionRate") * 1/(stabilizers.size())));
                }

            }

        } else {
            VoidUtils.logger.error("The crystal that is attached to the Crystal Stand @" + getPos() + ", has an invalid dupe item!");
        }
    }

    public void processVoidCrystal(ItemStack stack, NBTTagCompound compound, ArrayList<TileStabilizer> stabilizers) {

    }

    private ArrayList<TileStabilizer> getNearbyStabilizers() {
        ArrayList<TileStabilizer> returnList = new ArrayList<>();
        for(EnumFacing facing : EnumFacing.HORIZONTALS) {
            TileEntity tile = world.getTileEntity(this.getPos().offset(facing, 4));
            if(tile instanceof TileStabilizer) returnList.add((TileStabilizer) tile);
        }

        return returnList;
    }

    private boolean canPutItems() {
        for(int i = 1; i < 4; i++) {
            if(!this.inv.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
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
