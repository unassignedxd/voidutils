package com.github.unassignedxd.voidutils.api.voidenergy;

import net.minecraft.util.math.ChunkPos;

public interface IVoidChunk {

    void recieveVoid(int amount);
    void extractVoid(int amount);

    ChunkPos getChunkPos();

    int getVoidStored();
    void setVoidStored(int amount);

    boolean getDangerState();
    void setDangerState(boolean set);
}
