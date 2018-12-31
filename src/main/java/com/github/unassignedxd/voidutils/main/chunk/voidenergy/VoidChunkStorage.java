package com.github.unassignedxd.voidutils.main.chunk.voidenergy;

import com.github.unassignedxd.voidutils.api.voidenergy.IVoidChunk;
import com.github.unassignedxd.voidutils.main.chunk.voidenergy.effects.IVoidEffect;
import com.github.unassignedxd.voidutils.main.network.PacketHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;

public class VoidChunkStorage implements IVoidChunk {

    protected Chunk chunk;
    protected int voidEnergy;
    protected boolean dangerState;
    protected ArrayList<IVoidEffect> effects = new ArrayList<>();

    private boolean needsSync;

    public VoidChunkStorage(Chunk chunk, int voidEnergy, boolean dangerState) {
        this.chunk = chunk;
        this.voidEnergy = voidEnergy;
        this.dangerState = dangerState;
    }

    @Override
    public void update() {
        World world = this.chunk.getWorld();

        for(IVoidEffect effect : this.effects){
            double x = this.chunk.getPos().getXStart() >> 4;
            double z = this.chunk.getPos().getZStart() >> 4;
            int actY = world.getHeight((int)x,(int)z);

            world.profiler.func_194340_a(() -> effect.getName().toString());
            effect.update(this, world, new BlockPos(x,actY,z));
            world.profiler.endSection();
        }

        if(this.needsSync){

            this.needsSync = false;
        }
    }

    @Override
    public void recieveVoid(int amount) {
        if(voidEnergy + amount > CapabilityVoidEnergy.DEFAULT_CAPACITY) this.voidEnergy = CapabilityVoidEnergy.DEFAULT_CAPACITY;

        voidEnergy += amount;
        onVoidChanged();
    }

    @Override
    public void extractVoid(int amount) {
        if(voidEnergy - amount < 0) this.voidEnergy = 0;

        voidEnergy -= amount;
        this.needsSync = true;
    }

    @Override
    public Chunk getChunk() {
        return this.chunk;
    }

    @Override
    public int getVoidStored() {
        return this.voidEnergy;
    }

    @Override
    public void setVoidStored(int amount) {
        this.voidEnergy = amount;
        this.needsSync = true;
    }

    @Override
    public boolean getDangerState() {
        return this.dangerState;
    }

    @Override
    public void setDangerState(boolean set) {
        this.dangerState = set;
        this.needsSync = true;
    }

    private void onVoidChanged() {

    }
}
