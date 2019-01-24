package com.github.unassignedxd.voidutils.main.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderSerializable<HANDLER> implements ICapabilityProvider, INBTSerializable<NBTBase> {

    public Capability<HANDLER> capability;
    public EnumFacing facing;
    public HANDLER instance;

    public CapabilityProviderSerializable(Capability<HANDLER> capability, EnumFacing facing, HANDLER instance) {
        this.capability = capability;
        this.facing = facing;
        this.instance = instance;
    }

    @Nullable
    @Override
    public NBTBase serializeNBT() {
        return getCapability().writeNBT(getInstance(), getFacing());
    }

    @Override
    public void deserializeNBT(final NBTBase nbt) {
        getCapability().readNBT(getInstance(), getFacing(), nbt);
    }

    @Override
    public boolean hasCapability(final Capability<?> capability, @Nullable final EnumFacing facing) {
        return capability == getCapability();
    }

    public <T> T getCapability(final Capability<T> capability, @Nullable final EnumFacing facing) {
        if (capability == getCapability()) {
            return getCapability().cast(getInstance());
        }

        return null;
    }

    public final Capability<HANDLER> getCapability() {
        return capability;
    }

    @Nullable
    public EnumFacing getFacing() {
        return facing;
    }

    @Nullable
    public final HANDLER getInstance() {
        return instance;
    }
}
