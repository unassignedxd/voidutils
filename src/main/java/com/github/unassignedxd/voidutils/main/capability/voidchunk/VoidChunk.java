package com.github.unassignedxd.voidutils.main.capability.voidchunk;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.EnumVoidTypes;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.block.ModBlocks;
import com.github.unassignedxd.voidutils.main.network.NetworkManager;
import com.github.unassignedxd.voidutils.main.network.packets.PacketVoidChunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class VoidChunk implements IVoidChunk {

    protected final Chunk chunk;
    protected EnumVoidTypes voidType;

    protected int voidEnergy;
    protected int maxVoidEnergy;

    protected boolean hasNaturalNode;
    protected BlockPos nodePos;

    private boolean shouldSendData = false;

    public VoidChunk(Chunk chunk, EnumVoidTypes voidType, int voidEnergy, int maxVoidEnergy) {
        this.chunk = chunk;
        this.voidType = voidType;
        this.voidEnergy = voidEnergy;
        this.maxVoidEnergy = maxVoidEnergy;

        if(voidType.hasPossibleNaturalNode) {
            if(chunk.getWorld().rand.nextDouble() < .25f){
                this.hasNaturalNode = true;
            }
        }
    }

    @Override
    public void onUpdate() {
        if(this.shouldSendData){
            NetworkManager.sendToAllLoaded(this.chunk.getWorld(), new BlockPos(chunk.x << 4, 0, chunk.z << 4), voidPacketFactory());
            this.shouldSendData = false;
        }
    }

    @Override
    public void onChunkLoad(ChunkEvent.Load event) {
        if(this.hasNaturalNode && this.nodePos == null) {
            this.nodePos = new BlockPos(this.chunk.x << 4, this.chunk.getHeightValue(8, 8), this.chunk.z << 4);
            if(this.chunk.getWorld().getBlockState(this.nodePos) != ModBlocks.VOID_NODE.getDefaultState()) {
                this.chunk.getWorld().setBlockState(this.nodePos, ModBlocks.VOID_NODE.getDefaultState());
            }
        }
    }

    @Override
    public void onChunkUnload(ChunkEvent.Unload event) {

    }

    @Override
    public IMessage voidPacketFactory() {
        return new PacketVoidChunk(this.chunk, this.hasNaturalNode, this.voidEnergy, this.voidType.getId());
    }

    @Override
    public Chunk getAttachedChunk() {
        return this.chunk;
    }

    @Override
    public EnumVoidTypes getVoidType() {
        return this.voidType;
    }

    @Override
    public void setVoidType(EnumVoidTypes type) {
        this.voidType = type;
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
    public BlockPos getNodePosition() {
        return this.nodePos;
    }

    @Override
    public void setNodePosition(BlockPos nodePosition) {
        this.nodePos = nodePosition;
    }

    @Override
    public int getVoidStored() {
        return this.voidEnergy;
    }

    @Override
    public void setVoidStored(int set) {
        this.voidEnergy = set;
    }

    @Override
    public int getMaxVoidStored() {
        return this.maxVoidEnergy;
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

}
