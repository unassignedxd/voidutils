package com.github.unassignedxd.voidutils.main.util;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.EnumVoidTypes;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.Random;

public class CapabilityUtil {

    @Nullable
    public static <T> T getCapability(@Nullable ICapabilityProvider provider, Capability<T> capability, @Nullable EnumFacing facing) {
        return provider != null && provider.hasCapability(capability, facing) ? provider.getCapability(capability, facing) : null;
    }

    public static EnumVoidTypes getRandedVoidType(Random random) {
        double d = random.nextDouble();
        if(d > .15D && d < .575D) {return EnumVoidTypes.CORRUPTED; }
        else if(d > .575D && d < 1D) { return EnumVoidTypes.PURE; }
        else return EnumVoidTypes.NORMAL;
    }

}
