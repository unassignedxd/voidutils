package com.github.unassignedxd.voidutils.main.network.packets;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.EnumVoidType;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketVoidChunk implements IMessage {

    public PacketVoidChunk() {}

    public ChunkPos chunkPos;

    public boolean hasNaturalNode;

    public int voidEnergy;
    public int voidTypeID;

    public PacketVoidChunk(Chunk chunk, boolean hasNaturalNode, int voidEnergyStored, int voidTypeID) {
        this.chunkPos = chunk.getPos();
        this.hasNaturalNode = hasNaturalNode;
        this.voidEnergy = voidEnergyStored;
        this.voidTypeID = voidTypeID;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int chunkX = buf.readInt();
        int chunkZ = buf.readInt();
        this.chunkPos = new ChunkPos(chunkX, chunkZ);
        this.hasNaturalNode = buf.readBoolean();
        this.voidEnergy = buf.readInt();
        this.voidTypeID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(chunkPos.x);
        buf.writeInt(chunkPos.z);
        buf.writeBoolean(hasNaturalNode);
        buf.writeInt(voidEnergy);
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
                    if(chunk.hasCapability(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null)){
                        IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);

                        voidChunk.setVoidEnergy(packet.voidEnergy);
                        voidChunk.setVoidType(EnumVoidType.getVoidTypeByID(packet.voidTypeID));
                        voidChunk.setHasNaturalNode(packet.hasNaturalNode);
                    }
                }
            });

            return null;
        }
    }
}
