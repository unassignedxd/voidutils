package com.github.unassignedxd.voidutils.api.capability.voidchunk;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;

public interface IVoidChunk {

    void update();

    Chunk getVoidChunk();

    VoidType getVoidType();

    VoidType setVoidType(VoidType voidType);

    @Nullable
    BlockPos getNodePos();

    BlockPos setNodePos(BlockPos set);

    int getVoidStored();

    int setVoidStored(int set);

    int getMaxVoidStored();

    int extractVoid(int amount, boolean sim);

    int receiveVoid(int amount, boolean sim);

}
