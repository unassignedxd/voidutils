package com.github.unassignedxd.voidutils.main.block.functional.voidnode;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.VoidType;
import com.github.unassignedxd.voidutils.main.block.base.tile.TileBase;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;

public class TileVoidNode extends TileBase {

    public VoidType nodeType;

    @Override
    public void onLoad() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeNBT(compound, SaveType.TILE);

        if(!compound.hasKey("VoidTypeID")) {
            Chunk chunk = this.world.getChunk(this.getPos());
            if(chunk.hasCapability(CapabilityVoidChunk.VOID_CHUNK_CAPABILITY, null)) {
                IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(chunk);
                this.nodeType = voidChunk.getVoidType();
            }
        }
    }

    @Override
    protected boolean shouldUpdate() {
        return false;
    }

    public VoidType getType() {
        return nodeType;
    }

    @Override
    public void readNBT(NBTTagCompound compound, SaveType saveType) {
        super.readNBT(compound, saveType);
        this.nodeType = VoidType.getVoidTypeFromID(compound.getInteger("VoidTypeID"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("VoidTypeID", this.nodeType.getId());
        return super.writeToNBT(compound);
    }
}
;