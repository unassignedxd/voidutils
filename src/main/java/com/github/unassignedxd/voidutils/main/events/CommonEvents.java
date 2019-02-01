package com.github.unassignedxd.voidutils.main.events;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.EnumVoidType;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.capability.CapabilityProviderSerializable;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.VoidChunk;
import com.github.unassignedxd.voidutils.main.util.CapabilityUtil;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;

@Mod.EventBusSubscriber(modid = VoidUtils.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void onAttachCapabilityToChunk(AttachCapabilitiesEvent<Chunk> event) {
        Chunk chunk = event.getObject();
        if(chunk != null) {
            chunk.getWorld().profiler.startSection(VoidUtils.MOD_ID + ":attachCapabilities");
            EnumVoidType voidType = CapabilityUtil.getVoidTypeWithRandoms(chunk.getWorld());
            IVoidChunk voidChunk = new VoidChunk(chunk, voidType, CapabilityUtil.getHasNaturalNodeRandom(voidType, chunk.getWorld()), 5000, 10000);
            event.addCapability(CapabilityVoidChunk.ID, new CapabilityProviderSerializable<>(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null, voidChunk));
            chunk.getWorld().profiler.endSection();
        }
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        Chunk chunk = event.getChunk();
        if(chunk != null) {
            chunk.getWorld().profiler.startSection(VoidUtils.MOD_ID + ":onChunkLoad");
            if(chunk.hasCapability(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null)) {
                IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                if(voidChunk != null) {
                    voidChunk.onChunkLoad();
                }
            }
            chunk.getWorld().profiler.endSection();
        }
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        Chunk chunk = event.getChunk();
        if(chunk != null) {
            chunk.getWorld().profiler.startSection(VoidUtils.MOD_ID + ":onChunkUnload");
            if(chunk.hasCapability(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null)) {
                IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                if(voidChunk != null) {
                    voidChunk.onChunkUnload();
                }
            }
            chunk.getWorld().profiler.endSection();
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if(!event.world.isRemote && event.phase == TickEvent.Phase.END){
            if(event.world.getTotalWorldTime() % 20 == 0) { //update once a second.
                event.world.profiler.startSection(VoidUtils.MOD_ID + ":onWorldTick");
                Iterator<Chunk> loadedChunks = event.world.getPersistentChunkIterable(((WorldServer) event.world).getPlayerChunkMap().getChunkIterator());
                while(loadedChunks.hasNext()){
                    Chunk chunk = loadedChunks.next();
                    if(chunk.hasCapability(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null)) {
                        IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                        voidChunk.update();
                    }
                }
                event.world.profiler.endSection();
            }
        }
    }

}
