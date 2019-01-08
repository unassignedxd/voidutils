package com.github.unassignedxd.voidutils.main.chunk.voidenergy.effects;

import com.github.unassignedxd.voidutils.api.voidenergy.IVoidChunk;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public interface IVoidEffect {

    void update(IVoidChunk chunk, World world, BlockPos centralLoc);

    ResourceLocation getName();


}
