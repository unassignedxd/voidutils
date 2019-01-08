package com.github.unassignedxd.voidutils.main.blocks.tiles;

import com.github.unassignedxd.voidutils.main.util.EnergyStorageCustom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.IEnergyStorage;

public class TileStabilizer extends TileBase {

    public final EnergyStorageCustom energyStorage = new EnergyStorageCustom(1000000, 250000, 250000);

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.energyStorage;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, SaveType type) {
        super.readFromNBT(compound, type);
        energyStorage.deserializeNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, SaveType type) {
        super.writeToNBT(compound, type);
        energyStorage.serializeNBT();
    }
}
