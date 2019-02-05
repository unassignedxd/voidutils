package com.github.unassignedxd.voidutils.main.block.functional.voidportal;

import com.github.unassignedxd.voidutils.main.block.base.tile.ItemHandlerCustom;
import com.github.unassignedxd.voidutils.main.block.base.tile.TileBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.items.IItemHandler;

public class TileVoidPortal extends TileBase {

    public final IItemHandler itemHandler = new ItemHandlerCustom(8);

    public int processTime;
    public final int maxProcessTime = 30;

    @Override
    public void update() {
        super.update();

        if(!this.world.isRemote){
            Chunk chunk = world.getChunk(this.getPos());
        }

    }



}
