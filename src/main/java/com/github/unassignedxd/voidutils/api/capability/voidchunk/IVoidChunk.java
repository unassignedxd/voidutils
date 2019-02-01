package com.github.unassignedxd.voidutils.api.capability.voidchunk;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nullable;

public interface IVoidChunk {

    void update();

    void onChunkLoad();

    void onChunkUnload();

    Chunk getChunk();

    EnumVoidType getVoidType();

    void setVoidType(EnumVoidType type);

    @Nullable
    BlockPos getNodePos();

    void setNodePos(BlockPos set);

    boolean getHasNaturalNode();

    void setHasNaturalNode(boolean set);

    int receiveVoidEnergy(int amount, boolean simulate);

    int extractVoidEnergy(int amount, boolean simulate);

    int getVoidEnergy();

    void setVoidEnergy(int set);

    int getMaxVoidEnergy();
}
