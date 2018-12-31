package com.github.unassignedxd.voidutils.main.network.packets;

import com.github.unassignedxd.voidutils.api.voidenergy.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.chunk.voidenergy.CapabilityVoidEnergy;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketVoidChunk implements IMessage {

    private int chunkX;
    private int chunkZ;
    private int voidEnergy;
    private boolean dangerState;

    public PacketVoidChunk() {}

    public PacketVoidChunk(int chunkX, int chunkZ, int voidEnergy, boolean dangerState){
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.voidEnergy = voidEnergy;
        this.dangerState = dangerState;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.chunkX = buf.readInt();
        this.chunkZ = buf.readInt();

        this.voidEnergy = buf.readInt();
        this.dangerState = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.chunkX);
        buf.writeInt(this.chunkZ);

        buf.writeInt(this.voidEnergy);
        buf.writeBoolean(this.dangerState);
    }

    public static class Handler implements IMessageHandler<PacketVoidChunk, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketVoidChunk message, MessageContext ctx) {
            VoidUtils.proxy.scheduleTask(() -> {
                World world = Minecraft.getMinecraft().world;
                if(world != null){
                    Chunk chunk = world.getChunk(message.chunkX, message.chunkZ);
                    IVoidChunk voidChunk = CapabilityVoidEnergy.getVoidChunk(chunk);
                    if(voidChunk != null){
                        voidChunk.setVoidStored(message.voidEnergy);
                        voidChunk.setDangerState(message.dangerState);
                    }
                }
            });

            return null;
        }

    }
}
