package com.github.unassignedxd.voidutils.main.block.world;

import com.github.unassignedxd.voidutils.main.block.base.BlockBase;
import com.github.unassignedxd.voidutils.main.item.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockVoidOre extends BlockBase {

    public BlockVoidOre() {
        super("void_ore", Material.ROCK);
        this.setHardness(5f);
        this.setResistance(-1);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.VOID_SHARD;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return random.nextInt(3) + 1;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return false;
    }

}
