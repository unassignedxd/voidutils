package com.github.unassignedxd.voidutils.main.capability.voidchunk;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.VoidType;
import com.github.unassignedxd.voidutils.main.network.NetworkManager;
import com.github.unassignedxd.voidutils.main.network.packets.PacketVoidChunk;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class VoidChunk implements IVoidChunk {

    protected Chunk chunk;
    protected VoidType voidType;

    protected int voidEnergy;
    protected int capacity;

    private boolean hasNode = false; //have a node in corrupted / pure areas, used in crafting and void equivalence trading.

    public VoidChunk(Chunk chunk, VoidType voidType, int voidEnergy, int capacity) {
        this.chunk = chunk;
        this.voidType = voidType;
        this.voidEnergy = voidEnergy;
        this.capacity = capacity;
    }

    private boolean shouldUpdate = false;

    @Override
    public void update() {
        World world = chunk.getWorld();

        if (this.shouldUpdate) {
            NetworkManager.sendToAllLoaded(world, new BlockPos(this.chunk.x << 4, 0, this.chunk.z << 4), this.packetFactory());
            this.shouldUpdate = false;
        }
    }

    public IMessage packetFactory() {
        return new PacketVoidChunk(this.chunk, this.voidEnergy, this.voidType.getId());
    }

    @Override
    public Chunk getVoidChunk() {
        return this.chunk;
    }

    @Override
    public VoidType getVoidType() {
        return this.voidType;
    }

    @Override
    public VoidType setVoidType(VoidType voidType) {
        return this.voidType = voidType;
    }

    @Override
    public int getVoidStored() {
        return this.voidEnergy;
    }

    @Override
    public int setVoidStored(int set) {
        this.voidEnergy = set;
        this.shouldUpdate = true;
        return voidEnergy;
    }

    @Override
    public int getMaxVoidStored() {
        return this.capacity;
    }

    @Override
    public int extractVoid(int amount, boolean sim) {
        int ext = Math.min(this.voidEnergy, amount);
        if (!sim) {
            this.voidEnergy -= ext;
            this.shouldUpdate = true;
        }
        return ext;
    }

    @Override
    public int receiveVoid(int amount, boolean sim) {
        int rec = Math.min(this.capacity - this.voidEnergy, amount);
        if (!sim) {
            this.voidEnergy += rec;
            this.shouldUpdate = true;
        }

        return rec;
    }

}
