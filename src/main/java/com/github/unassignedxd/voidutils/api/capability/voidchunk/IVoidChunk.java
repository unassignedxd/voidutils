package com.github.unassignedxd.voidutils.api.capability.voidchunk;

import net.minecraft.world.chunk.Chunk;

public interface IVoidChunk {

    void update();

    Chunk getVoidChunk();

    VoidType getVoidType();

    VoidType setVoidType(int voidTypeID);

    VoidType setVoidType(VoidType voidType);

    int getVoidStored();

    int setVoidStored(int set);

    int getMaxVoidStored();

    int extractVoid(int amount, boolean sim);

    int receiveVoid(int amount, boolean sim);

}
