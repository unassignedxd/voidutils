package com.github.unassignedxd.voidutils.main.events;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.capability.CapabilityProviderSerializable;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.VoidChunk;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = VoidUtils.MOD_ID)
public class CommonEvents {

    @SubscribeEvent
    public static void onCapabilityAttachToChunk(AttachCapabilitiesEvent<Chunk> event) {
        Chunk chunk = event.getObject();
        if (chunk != null) {
            VoidChunk voidChunk = new VoidChunk(chunk, CapabilityVoidChunk.getVoidTypeWithRandoms(chunk.getWorld(), 50, 50), 0, 10000);
            event.addCapability(CapabilityVoidChunk.ID_CHUNK, new CapabilityProviderSerializable<>(CapabilityVoidChunk.VOID_CHUNK_CAPABILITY, CapabilityVoidChunk.DEFAULT_FACING, voidChunk));
        }
    }

}
