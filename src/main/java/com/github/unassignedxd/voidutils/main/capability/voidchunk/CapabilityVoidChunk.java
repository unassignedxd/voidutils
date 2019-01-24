package com.github.unassignedxd.voidutils.main.capability.voidchunk;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.VoidType;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.capability.CapabilityProviderSerializable;
import com.github.unassignedxd.voidutils.main.util.ModUtil;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Random;

public class CapabilityVoidChunk {

    @CapabilityInject(IVoidChunk.class)
    public static final Capability<IVoidChunk> VOID_CHUNK_CAPABILITY = null;

    public static final EnumFacing DEFAULT_FACING = null;

    public static final ResourceLocation ID = new ResourceLocation(VoidUtils.MOD_ID, "capability_void_chunk");

    public static void registerCap() {
        CapabilityManager.INSTANCE.register(IVoidChunk.class, new Capability.IStorage<IVoidChunk>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IVoidChunk> capability, IVoidChunk iVoidChunk, EnumFacing enumFacing) {
                NBTTagCompound compound = new NBTTagCompound();
                if(iVoidChunk.getVoidStored() > 0 && iVoidChunk.getVoidType() != null){
                    compound.setInteger("VoidStored", iVoidChunk.getVoidStored());
                    compound.setInteger("VoidTypeID", iVoidChunk.getVoidType().getId());
                }
                return compound;
            }

            @Override
            public void readNBT(Capability<IVoidChunk> capability, IVoidChunk iVoidChunk, EnumFacing enumFacing, NBTBase nbtBase) {
                if(nbtBase instanceof NBTTagCompound){
                    NBTTagCompound compound = (NBTTagCompound)nbtBase;
                    if(compound.hasKey("VoidStored") && compound.hasKey("VoidTypeID")) {
                        iVoidChunk.setVoidStored(compound.getInteger("VoidStored"));
                        iVoidChunk.setVoidType(compound.getInteger("VoidTypeID"));
                    }
                }
            }
        }, () -> null);
    }

    public static IVoidChunk getVoidChunk(Chunk chunk){
        return ModUtil.getCapability(chunk, VOID_CHUNK_CAPABILITY, DEFAULT_FACING);
    }

    public static VoidType getVoidTypeWithRandoms(World world, int corruptedWeight, int pureWeight) {
        Random rand = world.rand;

        if(rand.nextDouble() < .975D){ //97.5% of chunks
            return VoidType.NORMAL;
        } else {
            if(rand.nextInt(corruptedWeight+pureWeight) > pureWeight){
                return VoidType.PURE;
            } else {
                return VoidType.CORRUPTED;
            }
        }

    }

    @Mod.EventBusSubscriber(modid = VoidUtils.MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onCapabilityAttachToChunk(AttachCapabilitiesEvent<Chunk> event){
            Chunk chunk = event.getObject();
            if(chunk != null){
                VoidChunk voidChunk = new VoidChunk(chunk, getVoidTypeWithRandoms(chunk.getWorld(), 50, 50), 0, 10000);
                event.addCapability(ID, new CapabilityProviderSerializable<>(VOID_CHUNK_CAPABILITY, DEFAULT_FACING, voidChunk));
            }
        }
    }


}
