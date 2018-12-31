package com.github.unassignedxd.voidutils.main.chunk.voidenergy;

import com.github.unassignedxd.voidutils.api.voidenergy.IVoidChunk;
import net.minecraft.util.math.ChunkPos;

public class VoidChunkStorage implements IVoidChunk {

    protected ChunkPos chunkPos;
    protected int voidEnergy;
    protected boolean dangerState;

    public VoidChunkStorage(ChunkPos chunkPos, int voidEnergy, boolean dangerState) {
        this.chunkPos = chunkPos;
        this.voidEnergy = voidEnergy;
        this.dangerState = dangerState;
    }

    @Override
    public void recieveVoid(int amount) {
        if(voidEnergy + amount > CapabilityVoidEnergy.DEFAULT_CAPACITY) this.voidEnergy = CapabilityVoidEnergy.DEFAULT_CAPACITY;

        voidEnergy += amount;
    }

    @Override
    public void extractVoid(int amount) {
        if(voidEnergy - amount < 0) this.voidEnergy = 0;

        voidEnergy -= amount;
    }

    @Override
    public ChunkPos getChunkPos() {
        return this.chunkPos;
    }

    @Override
    public int getVoidStored() {
        return this.voidEnergy;
    }

    @Override
    public void setVoidStored(int amount) {
        this.voidEnergy = amount;
    }

    @Override
    public boolean getDangerState() {
        return this.dangerState;
    }

    @Override
    public void setDangerState(boolean set) {
        this.dangerState = set;
    }
}
