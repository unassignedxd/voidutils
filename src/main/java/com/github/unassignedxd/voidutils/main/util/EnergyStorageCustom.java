package com.github.unassignedxd.voidutils.main.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageCustom extends EnergyStorage implements INBTSerializable<NBTBase> {

    public EnergyStorageCustom(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public EnergyStorageCustom(int capacity) {
        super(capacity);
    }

    /**
    public int setCapacity(int capacity) {
        return this.capacity = capacity;
    }
*/
    public boolean atCapacity() { return this.getEnergyStored() == this.getMaxEnergyStored();}

    @Override
    public NBTBase serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("Energy", this.energy);
        compound.setInteger("MaxReceive", this.maxReceive);
        compound.setInteger("MaxExtract", this.maxExtract);
        compound.setInteger("Capacity", this.capacity);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        if(nbt instanceof NBTTagCompound){
            NBTTagCompound compound = (NBTTagCompound)nbt;
            if(compound.hasKey("Energy") && compound.hasKey("MaxReceive") && compound.hasKey("MaxExtract") && compound.hasKey("Capacity")){
                this.energy = compound.getInteger("Energy");
                this.maxReceive = compound.getInteger("MaxReceive");
                this.maxExtract = compound.getInteger("MaxExtract");
                this.capacity = compound.getInteger("Capacity");
            }
        }
    }
}
