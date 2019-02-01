package com.github.unassignedxd.voidutils.main.block.functional.voidnode;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.block.base.tile.TileBase;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;

public class TileVoidNode extends TileBase {

    public IVoidChunk voidChunk = null;

    @Override
    public void update() {
        super.update();
        if(!this.world.isRemote) {
            if(voidChunk == null) {
                Chunk chunk = world.getChunk(this.getPos());
                if(chunk.hasCapability(CapabilityVoidChunk.CAPABILITY_VOID_CHUNK, null)){
                    this.voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                }
            }
        }
    }

    public IVoidChunk getVoidChunk() {
        return voidChunk;
    }

    @Override
    public void readNBT(NBTTagCompound compound, SaveType saveType) {
        super.readNBT(compound, saveType);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }
}
;