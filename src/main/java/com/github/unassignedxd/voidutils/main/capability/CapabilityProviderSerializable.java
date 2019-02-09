package com.github.unassignedxd.voidutils.main.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderSerializable<HANDLER> implements ICapabilitySerializable<NBTBase> {

    protected Capability<HANDLER> cap;
    protected HANDLER instance;
    protected EnumFacing facing;

    public CapabilityProviderSerializable(Capability<HANDLER> capability, HANDLER instance, EnumFacing facing) {
        this.cap = capability;
        this.instance = instance;
        this.facing = facing;
    }

    public Capability<HANDLER> getCap() {
        return cap;
    }

    public HANDLER getInstance() {
        return instance;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing enumFacing) {
        return capability == getCap();
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing enumFacing) {
        return capability == getCap() ? getCap().cast(getInstance()) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return getCap().writeNBT(getInstance(), getFacing());
    }

    @Override
    public void deserializeNBT(NBTBase nbtBase) {
        getCap().readNBT(getInstance(), getFacing(), nbtBase);
    }
}
