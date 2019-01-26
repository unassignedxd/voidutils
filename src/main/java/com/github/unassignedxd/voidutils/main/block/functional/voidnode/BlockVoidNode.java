package com.github.unassignedxd.voidutils.main.block.functional.voidnode;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.VoidType;
import com.github.unassignedxd.voidutils.main.block.base.BlockTileBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockVoidNode extends BlockTileBase {

    public BlockVoidNode(){
        super("void_node", Material.ROCK, TileVoidNode.class, "void_node");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);

        TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileVoidNode) {
            TileVoidNode voidNode = (TileVoidNode)tile;
            if(voidNode.getType() != null){ //todo custom fx
                worldIn.spawnParticle(EnumParticleTypes.CRIT_MAGIC, pos.getX()+.5f, pos.getY()+.5f, pos.getZ()+.5f, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }

    }

    @Override
    public CreativeTabs getDisplayTab() {
        return null;
    }
}
