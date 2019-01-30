package com.github.unassignedxd.voidutils.main.network.packets;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.VoidType;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketVoidChunk implements IMessage {

    public PacketVoidChunk() {}

    public ChunkPos chunkPos;
    public BlockPos nodePos;
    public int voidEnergyStored;
    public int voidTypeID;

    public PacketVoidChunk(Chunk chunk, BlockPos nodePos, int voidEnergyStored, int voidTypeID) {
        this.chunkPos = chunk.getPos();
        this.nodePos = nodePos;
        this.voidEnergyStored = voidEnergyStored;
        this.voidTypeID = voidTypeID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int chunkX = buf.readInt();
        int chunkZ = buf.readInt();
        this.chunkPos = new ChunkPos(chunkX, chunkZ);

        //this.nodePos = BlockPos.fromLong(buf.readLong());

        this.voidEnergyStored = buf.readInt();
        this.voidTypeID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(chunkPos.x);
        buf.writeInt(chunkPos.z);

       // if(this.nodePos != null)
        //    buf.writeLong(nodePos.toLong());

        buf.writeInt(voidEnergyStored);
        buf.writeInt(voidTypeID);
    }

    public static class Handler implements IMessageHandler<PacketVoidChunk, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketVoidChunk packet, MessageContext messageContext) {
            VoidUtils.proxy.scheduleSidedTask(() -> {
                World world = Minecraft.getMinecraft().world;
                if(world != null) {
                    Chunk chunk = world.getChunk(packet.chunkPos.x, packet.chunkPos.z);
                    if(chunk.hasCapability(CapabilityVoidChunk.VOID_CHUNK_CAPABILITY, null)){
                        IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);

                        voidChunk.setNodePos(packet.nodePos);

                        voidChunk.setVoidStored(packet.voidEnergyStored);
                        voidChunk.setVoidType(VoidType.getVoidTypeFromID(packet.voidTypeID));
                    }
                }
            });

            return null;
        }
    }
}
