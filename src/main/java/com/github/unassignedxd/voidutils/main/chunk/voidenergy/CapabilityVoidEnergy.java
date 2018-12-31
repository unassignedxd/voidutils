package com.github.unassignedxd.voidutils.main.chunk.voidenergy;

import com.github.unassignedxd.voidutils.api.voidenergy.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.network.PacketHandler;
import com.github.unassignedxd.voidutils.main.network.packets.PacketVoidChunk;
import com.github.unassignedxd.voidutils.main.util.CapabilityProviderSerializable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nullable;
import java.util.Iterator;

public class CapabilityVoidEnergy {

    @CapabilityInject(IVoidChunk.class)
    public static final Capability<IVoidChunk> CAPABILITY_VOID_CHUNK = null;

    public static final EnumFacing DEFAULT_FACING = null;
    public static final int DEFAULT_CAPACITY = 10000;
    public static final int DEFAULT_ENERGY = 0;
    public static final ResourceLocation ID = new ResourceLocation(VoidUtils.MOD_ID, "void_chunk");

    public static void registerCap() {
        CapabilityManager.INSTANCE.register(IVoidChunk.class, new Capability.IStorage<IVoidChunk>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IVoidChunk> capability, IVoidChunk instance, EnumFacing side) {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setInteger("VoidEnergy", instance.getVoidStored());
                compound.setBoolean("DangerState", instance.getDangerState());
                return compound;
            }

            @Override
            public void readNBT(Capability<IVoidChunk> capability, IVoidChunk instance, EnumFacing side, NBTBase nbt) {
                if(nbt instanceof NBTTagCompound){
                    NBTTagCompound compound = (NBTTagCompound)nbt;
                    if(compound.hasKey("VoidEnergy") && compound.hasKey("DangerState")) {
                        instance.setDangerState(compound.getBoolean("DangerState"));
                        instance.setVoidStored(compound.getInteger("VoidEnergy"));
                    }
                }
            }
        }, () -> null);
    }

    public static IVoidChunk getVoidChunk(Chunk chunk){
        if(chunk.hasCapability(CAPABILITY_VOID_CHUNK, DEFAULT_FACING)){
            return chunk.getCapability(CAPABILITY_VOID_CHUNK, DEFAULT_FACING);
        }
        return null;
    }

    @Mod.EventBusSubscriber(modid = VoidUtils.MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onAttachCapabilityToChunk(AttachCapabilitiesEvent<Chunk> event){
            Chunk chunk = event.getObject();
            if(chunk != null){
                IVoidChunk voidChunk = new VoidChunkStorage(chunk, DEFAULT_ENERGY, false);
                event.addCapability(ID, new CapabilityProviderSerializable<>(CAPABILITY_VOID_CHUNK, voidChunk, DEFAULT_FACING));
            }
        }

        @SubscribeEvent
        public void onChunkWatch(ChunkWatchEvent.Watch event) {
            Chunk chunk = event.getChunkInstance();
            EntityPlayer player = event.getPlayer();
            if(!chunk.getWorld().isRemote && player != null) {
                IVoidChunk voidChunk = getVoidChunk(chunk);
                if(voidChunk != null){
                    PacketHandler.sendToPlayer(player, new PacketVoidChunk(voidChunk.getChunk().x, voidChunk.getChunk().z, voidChunk.getVoidStored(), voidChunk.getDangerState()));
                }
            }
        }

        @SubscribeEvent
        public static void onWorldTickEvent(TickEvent.WorldTickEvent event){
            World world = event.world;
            if(!world.isRemote && event.phase == TickEvent.Phase.END) {
                if(world.getWorldTime() % 20 == 0) {
                    world.profiler.func_194340_a(() -> VoidUtils.MOD_ID + ":onWorldTick");
                    Iterator<Chunk> loadedChunks = event.world.getPersistentChunkIterable(((WorldServer)world).getPlayerChunkMap().getChunkIterator());
                    while (loadedChunks.hasNext()){
                        Chunk chunk = loadedChunks.next();
                        IVoidChunk voidChunk = getVoidChunk(chunk);
                        if(voidChunk != null) { voidChunk.update(); }
                    }
                    world.profiler.endSection();
                }
            }
        }
    }
}
