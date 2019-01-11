package com.github.unassignedxd.voidutils.main.blocks;

import com.github.unassignedxd.voidutils.main.blocks.tiles.TileInfuser;
import com.github.unassignedxd.voidutils.main.blocks.tiles.render.RenderInfuser;
import com.github.unassignedxd.voidutils.main.registry.ITESRProvider;
import com.github.unassignedxd.voidutils.main.util.ModHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockInfuser extends BlockTileBase implements ITESRProvider {

    public BlockInfuser(String name){
        super (name, Material.IRON, TileInfuser.class, "infuser");
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return ModHelper.putStackInTile(playerIn, hand, pos, 0);
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public Tuple<Class, TileEntitySpecialRenderer> getTESR() {
        return new Tuple<>(TileInfuser.class, new RenderInfuser());
    }
}
