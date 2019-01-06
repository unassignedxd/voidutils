package com.github.unassignedxd.voidutils.main.blocks;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.blocks.tiles.TileCatalystInfuser;
import com.github.unassignedxd.voidutils.main.blocks.tiles.render.RenderCatalystInfuser;
import com.github.unassignedxd.voidutils.main.inventory.GuiHandler;
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

public class BlockCatalystInfuser extends BlockTileBase implements ITESRProvider {

    public BlockCatalystInfuser(){
        super("catalyst_infuser", Material.IRON, TileCatalystInfuser.class, "catalyst_infuser");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!world.isRemote){
            TileCatalystInfuser machine = (TileCatalystInfuser)world.getTileEntity(pos);
            if(machine != null && (!player.isSneaking() && player.getHeldItem(hand).isEmpty())){
                player.openGui(VoidUtils.instance, GuiHandler.GuiTypes.CRYSTAL_INFUSER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                return true;
            }
        }
        ModHelper.putStackInTile(player, hand, pos, 0);
        return true;
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
        return new Tuple<>(TileCatalystInfuser.class, new RenderCatalystInfuser());
    }
}
