package com.github.unassignedxd.voidutils.main.capability.voidchunk;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.VoidType;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.util.ModUtil;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;
import java.util.Random;

public class CapabilityVoidChunk {

    @CapabilityInject(IVoidChunk.class)
    public static final Capability<IVoidChunk> VOID_CHUNK_CAPABILITY = null;

    public static final EnumFacing DEFAULT_FACING = null;

    public static final ResourceLocation ID_CHUNK = new ResourceLocation(VoidUtils.MOD_ID, "capability_void_chunk");

    public static void registerCap() {
        CapabilityManager.INSTANCE.register(IVoidChunk.class, new Capability.IStorage<IVoidChunk>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IVoidChunk> capability, IVoidChunk iVoidChunk, EnumFacing enumFacing) {
                NBTTagCompound compound = new NBTTagCompound();
                if (iVoidChunk.getVoidStored() > 0 && iVoidChunk.getVoidType() != null) {
                    compound.setInteger("VoidStored", iVoidChunk.getVoidStored());
                    compound.setInteger("VoidTypeID", iVoidChunk.getVoidType().getId());
                }
                return compound;
            }

            @Override
            public void readNBT(Capability<IVoidChunk> capability, IVoidChunk iVoidChunk, EnumFacing enumFacing, NBTBase nbtBase) {
                if (nbtBase instanceof NBTTagCompound) {
                    NBTTagCompound compound = (NBTTagCompound) nbtBase;
                    if (compound.hasKey("VoidStored") && compound.hasKey("VoidTypeID")) {
                        iVoidChunk.setVoidStored(compound.getInteger("VoidStored"));
                        iVoidChunk.setVoidType(VoidType.getVoidTypeFromID(compound.getInteger("VoidTypeID")));
                    }
                }
            }
        }, () -> null);
    }

    public static IVoidChunk getVoidChunk(Chunk chunk) {
        return ModUtil.getCapability(chunk, VOID_CHUNK_CAPABILITY, DEFAULT_FACING);
    }

    public static VoidType getVoidTypeWithRandoms(World world) {
        Random rand = world.rand;
        double rD = rand.nextDouble();

        if(rD < .95) { //95%
            return VoidType.NORMAL;
        } else if(rD < .975 && rD > .95){ //2.5%
            return VoidType.PURE;
        } else if(rD <= 1 && rD > .975){ //2.5%
            return VoidType.CORRUPTED;
        }

        return VoidType.NORMAL;
    }

    public static BlockPos getRandTypeNode(Chunk chunk, VoidType type) {
        Random rand = chunk.getWorld().rand;

        if(type.isHasPossibleNode()){
            if(rand.nextDouble() < .25d) {
                return ModUtil.getCentralBlockPos(chunk).up(5);
            }
        }

        return null;
    }

}
