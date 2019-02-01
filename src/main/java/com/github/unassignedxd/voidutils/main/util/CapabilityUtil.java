package com.github.unassignedxd.voidutils.main.util;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.EnumVoidType;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.Random;

public class CapabilityUtil {

    @Nullable
    public static <T> T getCapability(@Nullable ICapabilityProvider provider, Capability<T> capability, @Nullable EnumFacing facing) {
        return provider != null && provider.hasCapability(capability, facing) ? provider.getCapability(capability, facing) : null;
    }

    public static EnumVoidType getVoidTypeWithRandoms(World world) {
        Random rand = world.rand;
        double rD = rand.nextDouble();

        if(rD < .95) { //95%
            return EnumVoidType.NORMAL;
        } else if(rD < .975 && rD > .95){ //2.5%
            return EnumVoidType.PURE;
        } else if(rD <= 1 && rD > .975){ //2.5%
            return EnumVoidType.CORRUPTED;
        }

        return EnumVoidType.NORMAL;
    }

    public static boolean getHasNaturalNodeRandom(EnumVoidType voidType, World world) {
        if(voidType.getPossibleNaturalNode()){
            if(world.rand.nextDouble() < .125f) {
                return true;
            }
        }
        return false;
    }
}
