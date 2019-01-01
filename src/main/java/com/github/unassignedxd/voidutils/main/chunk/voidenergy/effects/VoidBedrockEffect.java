package com.github.unassignedxd.voidutils.main.chunk.voidenergy.effects;

import com.github.unassignedxd.voidutils.api.voidenergy.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VoidBedrockEffect implements IVoidEffect {

    protected ResourceLocation NAME = new ResourceLocation(VoidUtils.MOD_ID, "void_chunk_effect_HIGH");

    @Override
    public void update(IVoidChunk chunk, World world, BlockPos centralLoc) {
        if(chunk.getVoidStored() < 7500) {
            chunk.removeEffect(this);
        }
    }

    @Override
    public ResourceLocation getName() {
        return this.NAME;
    }
}
