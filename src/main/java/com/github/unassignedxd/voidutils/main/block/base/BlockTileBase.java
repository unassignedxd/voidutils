package com.github.unassignedxd.voidutils.main.block.base;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.block.base.tile.TileBase;
import com.github.unassignedxd.voidutils.main.init.IModObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class BlockTileBase extends BlockContainer implements IModObject {

    private final String baseName;

    private final Class<? extends TileBase> tileClass;
    private final String tileName;

    public BlockTileBase(String name, Material material, Class<? extends TileBase> clazz, String tileName) {
        super(material);

        this.baseName = name;
        this.setRegistryName(VoidUtils.MOD_ID, name);
        this.setTranslationKey(VoidUtils.MOD_ID + "." + name);

        this.tileClass = clazz;
        this.tileName = tileName;
    }

    @Override
    public String getBaseName() {
        return this.baseName;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        try {
            return this.tileClass.newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Override
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(this.tileClass, new ResourceLocation(VoidUtils.MOD_ID, this.tileName));
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof TileBase)
                ((TileBase) tileEntity).dropInventory();
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof TileBase)
            drops.add(((TileBase) tileEntity).getDataDrop(state, fortune));
        else
            super.getDrops(drops, world, pos, state, fortune);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false); //makes data able to save to block, hacky forge fix
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos); //FlowerPot data fix; allows data to be saved to tiles before it is broken and removed
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileBase)
            ((TileBase) tileEntity).loadData(stack);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.updateRedstone(worldIn, pos);
    }

    //Not actually deprecated --> Can override, not call. I believe this is the case with other 'deprecated' functions.
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.updateRedstone(worldIn, pos);
    }

    private void updateRedstone(World world, BlockPos pos) {
        if (world != null && !world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof TileBase)
                ((TileBase) tileEntity).redstonePower = world.getRedstonePowerFromNeighbors(pos);
        }
    }
}
