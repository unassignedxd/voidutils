package com.github.unassignedxd.voidutils.main.network;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.network.packets.PacketVoidChunk;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkManager {

    protected static SimpleNetworkWrapper network;

    private static int messageID = 1;

    public static void init() {
        network = new SimpleNetworkWrapper(VoidUtils.MOD_ID);

        registerMessage(PacketVoidChunk.Handler.class, PacketVoidChunk.class,  Side.CLIENT);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void registerMessage(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> message, Side side) {
        network.registerMessage(handler, message, messageID++, side);
    }

    public static void sendToAllLoaded(World world, BlockPos blockPos, IMessage iMessage) {
        network.sendToAllTracking(iMessage, new NetworkRegistry.TargetPoint(world.provider.getDimension(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0));
    }

    public static void sendToAllAround(World world, BlockPos blockPos, int r, IMessage iMessage){
        network.sendToAllTracking(iMessage, new NetworkRegistry.TargetPoint(world.provider.getDimension(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), r));
    }

    public static void sendToPlayer(EntityPlayer player, IMessage iMessage) {
        network.sendTo(iMessage, (EntityPlayerMP) player);
    }
}
