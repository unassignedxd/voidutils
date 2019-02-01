package com.github.unassignedxd.voidutils.main.capability.voidchunk;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.EnumVoidType;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.util.CapabilityUtil;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityVoidChunk {

    @CapabilityInject(IVoidChunk.class)
    public static final Capability<IVoidChunk> CAPABILITY_VOID_CHUNK = null;

    public static final EnumFacing DEFAULT_FACING = null;

    public static final ResourceLocation ID = new ResourceLocation(VoidUtils.MOD_ID, "capability_voidchunk");

    public static void registerCap() {
        CapabilityManager.INSTANCE.register(IVoidChunk.class, new Capability.IStorage<IVoidChunk>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IVoidChunk> capability, IVoidChunk iVoidChunk, EnumFacing enumFacing) {
                NBTTagCompound compound = new NBTTagCompound();
                if(iVoidChunk != null) {
                    compound.setInteger("VoidEnergy", iVoidChunk.getVoidEnergy());
                    compound.setInteger("VoidTypeID", iVoidChunk.getVoidType().getId());
                    compound.setBoolean("HasNaturalNode", iVoidChunk.getHasNaturalNode());
                }
                return compound;
            }

            @Override
            public void readNBT(Capability<IVoidChunk> capability, IVoidChunk iVoidChunk, EnumFacing enumFacing, NBTBase nbtBase) {
                if(iVoidChunk != null && nbtBase instanceof NBTTagCompound){
                    NBTTagCompound compound = (NBTTagCompound)nbtBase;
                    if(compound.hasKey("VoidEnergy")){ //only one nessessary check
                        iVoidChunk.setVoidEnergy(compound.getInteger("VoidEnergy"));
                        iVoidChunk.setVoidType(EnumVoidType.getVoidTypeByID(compound.getInteger("VoidTypeID")));
                        iVoidChunk.setHasNaturalNode(compound.getBoolean("HasNaturalNode"));
                    }
                }
            }
        }, () -> null);
    }

    public static IVoidChunk getVoidChunk(Chunk chunk) {
        return CapabilityUtil.getCapability(chunk, CAPABILITY_VOID_CHUNK, DEFAULT_FACING);
    }
}
