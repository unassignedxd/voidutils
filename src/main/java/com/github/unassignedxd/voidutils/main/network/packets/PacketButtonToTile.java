package com.github.unassignedxd.voidutils.main.network.packets;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.blocks.tiles.IButtonReactor;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketButtonToTile implements IMessage {

    public PacketButtonToTile() { }

    public int x;
    public int y;
    public int z;
    public int worldID;
    public int playerID;
    public int buttonID;

    public PacketButtonToTile(int x, int y, int z, int worldID, int playerID, int buttonID){
        this.x = x;
        this.y = y;
        this.z = z;
        this.worldID = worldID;
        this.playerID = playerID;
        this.buttonID = buttonID;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.x = byteBuf.readInt();
        this.y = byteBuf.readInt();
        this.z = byteBuf.readInt();
        this.worldID = byteBuf.readInt();
        this.playerID = byteBuf.readInt();
        this.buttonID = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        buffer.writeInt(this.worldID);
        buffer.writeInt(this.playerID);
        buffer.writeInt(this.buttonID);
    }

    public static class Handler implements IMessageHandler<PacketButtonToTile, IMessage> {

        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(PacketButtonToTile packet, MessageContext messageContext) {
            VoidUtils.proxy.scheduleTask(() -> {
                World world = DimensionManager.getWorld(packet.worldID);
                TileEntity tile = world.getTileEntity(new BlockPos(packet.x, packet.y, packet.z));

                if(tile instanceof IButtonReactor){
                    IButtonReactor reactor = (IButtonReactor)tile;
                    Entity entity = world.getEntityByID(packet.playerID);
                    if(entity instanceof EntityPlayer){
                        reactor.onButtonPressed(packet.buttonID, (EntityPlayer)entity);
                    }
                }
            });
            return null;
        }
    }
}
