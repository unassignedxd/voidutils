package com.github.unassignedxd.voidutils.main.events;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;

@Mod.EventBusSubscriber(modid = VoidUtils.MOD_ID)
public class CommonEvents {

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
                        voidChunk.onUpdate();
                    }
                }
                event.world.profiler.endSection();
            }
        }
    }

}
