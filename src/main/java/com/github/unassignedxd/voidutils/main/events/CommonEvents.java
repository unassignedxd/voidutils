package com.github.unassignedxd.voidutils.main.events;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.capability.CapabilityProviderSerializable;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.VoidChunk;
import com.github.unassignedxd.voidutils.main.network.NetworkManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;

@Mod.EventBusSubscriber(modid = VoidUtils.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void onCapabilityAttachToChunk(AttachCapabilitiesEvent<Chunk> event) {
        Chunk chunk = event.getObject();
        if (chunk != null) {
            VoidChunk voidChunk = new VoidChunk(chunk, CapabilityVoidChunk.getVoidTypeWithRandoms(chunk.getWorld()), 5000, 10000);
            event.addCapability(CapabilityVoidChunk.ID_CHUNK, new CapabilityProviderSerializable<>(CapabilityVoidChunk.VOID_CHUNK_CAPABILITY, CapabilityVoidChunk.DEFAULT_FACING, voidChunk));
        }
    }

    @SubscribeEvent
    public static void onChunkWatch(ChunkWatchEvent event) {
        Chunk chunk = event.getChunkInstance();
        if(chunk != null) {
            if(!chunk.getWorld().isRemote && chunk.hasCapability(CapabilityVoidChunk.VOID_CHUNK_CAPABILITY, null)) {
                VoidChunk voidChunk = (VoidChunk)CapabilityVoidChunk.getVoidChunk(chunk);
                NetworkManager.sendToPlayer(event.getPlayer(), voidChunk.packetFactory());
            }
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
                    if(chunk.hasCapability(CapabilityVoidChunk.VOID_CHUNK_CAPABILITY, null)) {
                        IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                        voidChunk.update();
                    }
                }
                event.world.profiler.endSection();
            }
        }
    }

}
