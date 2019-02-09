package com.github.unassignedxd.voidutils.api.capability.voidchunk;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public interface IVoidChunk {

    void onUpdate();

    void onChunkLoad(ChunkEvent.Load event);

    void onChunkUnload(ChunkEvent.Unload event);

    Chunk getAttachedChunk();

    EnumVoidTypes getVoidType();

    void setVoidType(EnumVoidTypes type);

    boolean getHasNaturalNode();

    void setHasNaturalNode(boolean set);

    BlockPos getNodePosition();

    void setNodePosition(BlockPos nodePosition);

    int getVoidStored();

    void setVoidStored(int set);

    int getMaxVoidStored();

    int extractVoidEnergy(int amount, boolean simulate);

    int receiveVoidEnergy(int amount, boolean simulate);
}
