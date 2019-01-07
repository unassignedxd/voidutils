package com.github.unassignedxd.voidutils.main.network.packets;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketParticles implements IMessage {

    public PacketParticles() { }

    private float posX;
    private float posY;
    private float posZ;
    private int useType;
    private int[] data;

    public PacketParticles(float posX, float posY, float posZ, int useType, int... data){
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.useType = useType;
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.posX = byteBuf.readFloat();
        this.posY = byteBuf.readFloat();
        this.posZ = byteBuf.readFloat();
        this.useType = byteBuf.readInt();

        this.data = new int[byteBuf.readByte()]; //get the length of the data
        for(int i = 0; i < this.data.length; i++) {
            this.data[i] = byteBuf.readInt(); //get data out of buffer
        }
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeFloat(this.posX);
        byteBuf.writeFloat(this.posY);
        byteBuf.writeFloat(this.posZ);
        byteBuf.writeInt(this.useType);

        byteBuf.writeByte(this.data.length); //set the length of the data
        for(int i : this.data) { byteBuf.writeInt(i); } //put data into buffer
    }

    public static class Handler implements IMessageHandler<PacketParticles, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketParticles packet, MessageContext messageContext) {
            VoidUtils.proxy.scheduleTask( () -> {
                World world = Minecraft.getMinecraft().world;
                if(world != null) {
                    switch(packet.useType) { }
                }
            });
            return null;
        }
    }
}
