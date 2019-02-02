package com.github.unassignedxd.voidutils.main.capability.voidchunk;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.EnumVoidType;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.block.ModBlocks;
import com.github.unassignedxd.voidutils.main.network.NetworkManager;
import com.github.unassignedxd.voidutils.main.network.packets.PacketVoidChunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import javax.annotation.Nullable;

public class VoidChunk implements IVoidChunk {

    protected final Chunk chunk;

    protected EnumVoidType voidType;
    protected int voidEnergy;
    protected int maxVoidEnergy;

    protected BlockPos nodePos;
    protected boolean hasNaturalNode;

    private boolean shouldSendData = false;

    public VoidChunk(Chunk chunk, EnumVoidType voidType, boolean hasNaturalNode, int voidEnergy, int maxVoidEnergy) {
        this.chunk = chunk;
        this.voidType = voidType;
        this.hasNaturalNode = hasNaturalNode;
        this.voidEnergy = voidEnergy;
        this.maxVoidEnergy = maxVoidEnergy;
    }

    @Override
    public void update() {
        World world = chunk.getWorld();

        if(nodePos != null){
            if(world.getBlockState(nodePos) != ModBlocks.VOID_NODE.getDefaultState()) {
                this.setHasNaturalNode(false);
                VoidUtils.logger.info("Possible node destruction at " + nodePos.getX() + " " + nodePos.getY() + " " + nodePos.getZ());
                this.nodePos = null;
                this.shouldSendData = true;
            }
        }

        if (this.shouldSendData) {
            NetworkManager.sendToAllLoaded(chunk.getWorld(), new BlockPos(this.chunk.x << 4, 0, this.chunk.z << 4), this.voidPacketFactory());
            this.shouldSendData = false;
        }
    }

    @Override
    public void onChunkLoad() {
        if(this.hasNaturalNode && nodePos == null) {
            this.nodePos = new BlockPos(
                    (this.chunk.x << 4) + 8,
                    this.chunk.getHeightValue(8, 8),
                    (this.chunk.z << 4) + 8
            );

            if(chunk.getWorld().getBlockState(nodePos) != ModBlocks.VOID_NODE.getDefaultState()) {
                chunk.getWorld().setBlockState(nodePos, ModBlocks.VOID_NODE.getDefaultState());
                VoidUtils.logger.info("Spawned natural node at " + nodePos.getX() + " " + nodePos.getY() + " " + nodePos.getZ());
            }
        }
    }

    @Override
    public void onChunkUnload() {

    }

    public IMessage voidPacketFactory() {
        return new PacketVoidChunk(this.chunk, this.hasNaturalNode, this.voidEnergy, this.voidType.getId());
    }

    @Override
    public Chunk getChunk() {
        return this.chunk;
    }

    @Override
    public EnumVoidType getVoidType() {
        return this.voidType;
    }

    @Override
    public void setVoidType(EnumVoidType type) {
        this.voidType = type;
        this.shouldSendData = true;
    }

    @Nullable
    @Override
    public BlockPos getNodePos() {
        return this.nodePos;
    }

    @Override
    public void setNodePos(BlockPos set) {
        this.nodePos = set;
    }

    @Override
    public boolean getHasNaturalNode() {
        return this.hasNaturalNode;
    }

    @Override
    public void setHasNaturalNode(boolean set) {
        this.hasNaturalNode = set;
    }

    @Override
    public int extractVoidEnergy(int amount, boolean simulate) {
        int ext = Math.min(this.voidEnergy, amount);
        if (!simulate) {
            this.voidEnergy -= ext;
            this.shouldSendData = true;
        }
        return ext;
    }

    @Override
    public int receiveVoidEnergy(int amount, boolean simulate) {
        int rec = Math.min(this.maxVoidEnergy - this.voidEnergy, amount);
        if (!simulate) {
            this.voidEnergy += rec;
            this.shouldSendData = true;
        }

        return rec;
    }

    @Override
    public int getVoidEnergy() {
        return this.voidEnergy;
    }

    @Override
    public void setVoidEnergy(int set) {
        this.voidEnergy = set;
        this.shouldSendData = true;
    }

    @Override
    public int getMaxVoidEnergy() {
        return this.maxVoidEnergy;
    }
}
