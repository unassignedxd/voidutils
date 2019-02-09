package com.github.unassignedxd.voidutils.main.events;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.EnumVoidTypes;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.capability.CapabilityProviderSerializable;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.VoidChunk;
import com.github.unassignedxd.voidutils.main.network.NetworkManager;
import com.github.unassignedxd.voidutils.main.network.packets.PacketVoidChunk;
import com.github.unassignedxd.voidutils.main.util.CapabilityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkEvent;
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
        if(chunk != null) {
            EnumVoidTypes type = CapabilityUtil.getRandedVoidType(chunk.getWorld().rand);
            IVoidChunk voidChunk = new VoidChunk(chunk, type, type.hasPossibleNaturalNode, 5000, 10000);
            event.addCapability(CapabilityVoidChunk.ID, new CapabilityProviderSerializable<>(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, voidChunk, CapabilityVoidChunk.DEFAULT_FACING));
        }
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        World world = event.getWorld();
        Chunk chunk = event.getChunk();

        if(world != null && chunk != null) {
            if(!world.isRemote) {
                if(chunk.hasCapability(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null)){
                    IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                    if(voidChunk != null) voidChunk.onChunkLoad(event);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        World world = event.getWorld();
        Chunk chunk = event.getChunk();

        if(world != null && chunk != null) {
            if(!world.isRemote) {
                if(chunk.hasCapability(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null)){
                    IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                    if(voidChunk != null) voidChunk.onChunkUnload(event);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onChunkWatch(ChunkWatchEvent event) {
        EntityPlayer player = event.getPlayer();
        Chunk chunk = event.getChunkInstance();

        if(player != null && chunk != null) {
            if(chunk.hasCapability(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null)){
                IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                if(voidChunk != null) {
                    NetworkManager.sendToPlayer(player,
                            new PacketVoidChunk(voidChunk.getAttachedChunk(), voidChunk.getHasNaturalNode(), voidChunk.getVoidStored(), voidChunk.getVoidType().getId()));
                }
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
