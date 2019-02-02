package com.github.unassignedxd.voidutils.main.block.functional.voidportal;

import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.api.recipe.VoidInfusionRecipe;
import com.github.unassignedxd.voidutils.main.block.base.tile.TileBase;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import com.github.unassignedxd.voidutils.main.client.particles.particle.base.EnumParticleType;
import com.github.unassignedxd.voidutils.main.util.ModUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;

public class TileVoidPortal extends TileBase {

    @Override
    public void update() {
        super.update();

        if(!this.world.isRemote) {
            if(!runPortal()) {

            }
        }
    }

    public boolean runPortal() {
        IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(this.world.getChunk(this.getPos()));

        if(voidChunk != null && voidChunk.getHasNaturalNode()){

        }else return false;

        return true;
    }
}
