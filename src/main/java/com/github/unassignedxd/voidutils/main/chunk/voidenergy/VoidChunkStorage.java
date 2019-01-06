package com.github.unassignedxd.voidutils.main.chunk.voidenergy;

import com.github.unassignedxd.voidutils.api.voidenergy.IVoidChunk;
import com.github.unassignedxd.voidutils.main.chunk.voidenergy.effects.IVoidEffect;
import com.github.unassignedxd.voidutils.main.chunk.voidenergy.effects.VoidBedrockEffect;
import com.github.unassignedxd.voidutils.main.network.PacketHandler;
import com.github.unassignedxd.voidutils.main.network.packets.PacketVoidChunk;
import net.minecraft.util.math.BlockPos;
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

        if(this.voidEnergy >= 7500) {
            this.dangerState = true;
        } else { this.dangerState = false; }

        if(dangerState) {
            IVoidEffect effect = new VoidBedrockEffect();
            if(!effects.contains(effect)){
                effects.add(effect);
            }
        }

        for(IVoidEffect effect : this.effects){
            double x = this.chunk.getPos().getXStart() << 4;
            double z = this.chunk.getPos().getZStart() << 4;
            int actY = world.getHeight((int)x,(int)z);

            world.profiler.startSection(effect.getName().toString()+":update");
            effect.update(this, world, new BlockPos(x,actY,z));
            world.profiler.endSection();
        }

        if(this.needsSync){
            PacketHandler.sendToAllTracking(world, new BlockPos(this.chunk.x << 4, 0, this.chunk.z << 4), new PacketVoidChunk(this.chunk.x, this.chunk.z, this.voidEnergy, this.dangerState));
            this.needsSync = false;
        }
    }

    @Override
    public void recieveVoid(int amount) {
        if(voidEnergy + amount > CapabilityVoidEnergy.DEFAULT_CAPACITY) {
            this.voidEnergy = CapabilityVoidEnergy.DEFAULT_CAPACITY;
            amount=0;
        }

        voidEnergy += amount;
        this.needsSync = true;
    }

    @Override
    public void extractVoid(int amount) {
        if(voidEnergy - amount < 0) {
            this.voidEnergy = 0;
            amount=0;
        }

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

    @Override
    public ArrayList<IVoidEffect> getEffects() {
        return this.effects;
    }

    public void removeEffect(IVoidEffect effect){
        this.effects.remove(effect);
    }

}
