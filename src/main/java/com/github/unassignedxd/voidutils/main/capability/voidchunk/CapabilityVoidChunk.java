package com.github.unassignedxd.voidutils.main.capability.voidchunk;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.EnumVoidTypes;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.util.CapabilityUtil;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
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
            public NBTBase writeNBT(Capability<IVoidChunk> capability, IVoidChunk voidChunk, EnumFacing enumFacing) {
                NBTTagCompound compound = new NBTTagCompound();
                if(voidChunk != null) {
                    compound.setInteger("VoidEnergy", voidChunk.getVoidStored());
                    compound.setInteger("VoidTypeID", voidChunk.getVoidType().getId());
                    compound.setBoolean("HasNaturalNode", voidChunk.getHasNaturalNode());
                    if(voidChunk.getHasNaturalNode())
                        compound.setLong("NodePos", voidChunk.getNodePosition().toLong());
                }
                return compound;
            }

            @Override
            public void readNBT(Capability<IVoidChunk> capability, IVoidChunk voidChunk, EnumFacing enumFacing, NBTBase nbtBase) {
                if(nbtBase != null) {
                    if(nbtBase instanceof NBTTagCompound){
                        NBTTagCompound compound = (NBTTagCompound)nbtBase;
                        voidChunk.setVoidStored(compound.getInteger("VoidEnergy"));
                        voidChunk.setVoidType(EnumVoidTypes.getVoidTypeByID(compound.getInteger("VoidTypeID")));
                        voidChunk.setHasNaturalNode(compound.getBoolean("HasNaturalNode"));
                        if(voidChunk.getHasNaturalNode() && compound.hasKey("NodePos")) {
                            voidChunk.setNodePosition(BlockPos.fromLong(compound.getLong("NodePos")));
                        }
                    }
                }
            }
        }, () -> null);
    }

    public static IVoidChunk getVoidChunk(Chunk chunk){
        return CapabilityUtil.getCapability(chunk, CAPABILITY_VOID_CHUNK, DEFAULT_FACING);
    }
}
