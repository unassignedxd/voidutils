package com.github.unassignedxd.voidutils.main.capability.voidchunk;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.VoidType;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.world.chunk.Chunk;

public class VoidChunk implements IVoidChunk {

    protected Chunk chunk;
    protected VoidType voidType;

    protected int voidEnergy;
    protected int capacity;

    public VoidChunk(Chunk chunk, VoidType voidType, int voidEnergy, int capacity) {
        this.chunk = chunk;
        this.voidType = voidType;
        this.voidEnergy = voidEnergy;
        this.capacity = capacity;
    }

    private boolean shouldUpdate = false;

    @Override
    public void update() {

        if (shouldUpdate) {
            sendVoidPacket();
            this.shouldUpdate = false;
        }
    }

    private void sendVoidPacket() {

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
    public VoidType setVoidType(int voidTypeID) {
        VoidType setter = null;
        for (VoidType voidType : VoidType.values()) {
            if (voidType.getId() == voidTypeID) setter = voidType;
        }

        if (setter == null)
            VoidUtils.logger.error("Failed to set a Chunk's Void Type to a proper setter ID! Chunk in question @ " + chunk.getPos());

        return this.voidType = setter;
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
