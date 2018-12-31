package com.github.unassignedxd.voidutils.main.util;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderSerializable<HANDLER> implements ICapabilitySerializable<NBTBase>  {

    protected Capability<HANDLER> capability;
    protected HANDLER instance;
    protected EnumFacing facing;

    public CapabilityProviderSerializable(Capability<HANDLER> capability, HANDLER instance, EnumFacing facing){
        this.capability = capability;
        this.instance = instance;
        this.facing = facing;
    }

    @Override
    public NBTBase serializeNBT() {
        return getCapability().writeNBT(getInstance(), getFacing());
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        getCapability().readNBT(getInstance(), getFacing(), nbt);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == getCapability();
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == getCapability() ? getCapability().cast(getInstance()) : null;
    }

    public Capability<HANDLER> getCapability() {
        return capability;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public HANDLER getInstance() {
        return instance;
    }
}
