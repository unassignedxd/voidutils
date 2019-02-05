package com.github.unassignedxd.voidutils.main.block.functional.voidnode;

import com.github.unassignedxd.voidutils.main.block.base.BlockTileBase;
import com.github.unassignedxd.voidutils.main.client.particles.particle.base.EnumParticleType;
import com.github.unassignedxd.voidutils.main.util.ModUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
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
                if(voidNode.getVoidChunk() != null){
                    int c = voidNode.getVoidChunk().getVoidType().getDecimalColor();
                    for(int i = 0; i < 5; i ++) {
                        float interX = (pos.getX() + .5f) + ModUtil.getRangedFloat(rand, -1, 1);
                        float interY = (pos.getY() + .5f) + ModUtil.getRangedFloat(rand, -1, 1);
                        float interZ = (pos.getZ() + .5f) + ModUtil.getRangedFloat(rand, -1, 1);

                        ModUtil.spawnParticleStream(EnumParticleType.VOID_NODE, interX, interY, interZ,
                                pos.getX()+.5f,pos.getY()+.5f,pos.getZ()+.5f,
                                0.01f, 1, c, 1);
                }
            }
        }

    }

    @Override
    public CreativeTabs getDisplayTab() {
        return null;
    }
}
