package com.github.unassignedxd.voidutils.main.network.packets;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.VoidType;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketVoidChunk implements IMessage {

    public PacketVoidChunk() {}

    public ChunkPos chunkPos;
    public int voidEnergyStored;
    public int voidTypeID;

    public PacketVoidChunk(Chunk chunk, int voidEnergyStored, int voidTypeID) {
        this.chunkPos = chunk.getPos();
        this.voidEnergyStored = voidEnergyStored;
        this.voidTypeID = voidTypeID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int chunkX = buf.readInt();
        int chunkZ = buf.readInt();
        this.chunkPos = new ChunkPos(chunkX, chunkZ);

        this.voidEnergyStored = buf.readInt();
        this.voidTypeID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(chunkPos.x);
        buf.writeInt(chunkPos.z);
        buf.writeInt(voidEnergyStored);
        buf.writeInt(voidTypeID);
    }

    public static class Handler implements IMessageHandler<PacketVoidChunk, IMessage> {

        @Override
        public IMessage onMessage(PacketVoidChunk voidChunkPacket, MessageContext messageContext) {
            VoidUtils.proxy.scheduleSidedTask(() -> {
                World world = Minecraft.getMinecraft().world;
                if(world != null) {
                    Chunk chunk = world.getChunk(voidChunkPacket.chunkPos.x, voidChunkPacket.chunkPos.z);
                    if(chunk.hasCapability(CapabilityVoidChunk.VOID_CHUNK_CAPABILITY, null)){
                        IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);

                        voidChunk.setVoidStored(voidChunkPacket.voidEnergyStored);
                        voidChunk.setVoidType(VoidType.getVoidTypeFromID(voidChunkPacket.voidTypeID));
                    }
                }
            });

            return null;
        }
    }
}
