package com.github.unassignedxd.voidutils.api.voidenergy;

import net.minecraft.world.chunk.Chunk;

public interface IVoidChunk {

    void update();

    void recieveVoid(int amount);
    void extractVoid(int amount);

    Chunk getChunk();

    int getVoidStored();
    void setVoidStored(int amount);

    boolean getDangerState();
    void setDangerState(boolean set);
}
