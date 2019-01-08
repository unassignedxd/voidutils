package com.github.unassignedxd.voidutils.main.chunk.voidenergy.effects;

import com.github.unassignedxd.voidutils.api.voidenergy.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class VoidBedrockEffect implements IVoidEffect {

    protected ResourceLocation NAME = new ResourceLocation(VoidUtils.MOD_ID, "void_chunk_effect_HIGH");

    @Override
    public void update(IVoidChunk chunk, World world, BlockPos centralLoc) {
        if(chunk.getVoidStored() < 7500) {
            chunk.setDangerState(false);
            chunk.removeEffect(this);
        }

        Iterable<BlockPos> iterablePos = BlockPos.getAllInBox(new BlockPos(centralLoc.getX(), world.getHeight(), centralLoc.getZ()), new BlockPos(centralLoc.getX()+15, centralLoc.getY()-5, centralLoc.getZ()+15));
        for(BlockPos checkPos : iterablePos){
            if(world.rand.nextInt(50) == 0) {
                IBlockState state = world.getBlockState(checkPos);
                if(state != Blocks.AIR) {
                    world.setBlockState(checkPos, Blocks.BEDROCK.getDefaultState());
                }
            }
        }
    }

    @Override
    public ResourceLocation getName() {
        return this.NAME;
    }
}
