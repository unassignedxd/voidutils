package com.github.unassignedxd.voidutils.api.voidenergy;

import com.github.unassignedxd.voidutils.main.chunk.voidenergy.effects.IVoidEffect;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;

public interface IVoidChunk {

    void update();

    void recieveVoid(int amount);
    void extractVoid(int amount);

    Chunk getChunk();

    int getVoidStored();
    void setVoidStored(int amount);

    boolean getDangerState();
    void setDangerState(boolean set);

    ArrayList<IVoidEffect> getEffects();
    void removeEffect(IVoidEffect effect);
}
