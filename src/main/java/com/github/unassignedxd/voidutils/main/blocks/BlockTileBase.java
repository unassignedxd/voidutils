package com.github.unassignedxd.voidutils.main.blocks;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.blocks.tiles.TileBase;
import com.github.unassignedxd.voidutils.main.registry.ICreativeItem;
import com.github.unassignedxd.voidutils.main.registry.IModItem;
import com.github.unassignedxd.voidutils.main.registry.IModelProvider;
import com.github.unassignedxd.voidutils.main.registry.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
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

public class BlockTileBase extends BlockContainer implements IModItem, ICreativeItem, IModelProvider {

    private final String name;

    private final Class<? extends TileEntity> tileClass;
    private final String tileName;

    public BlockTileBase(String name, Material mat, Class<? extends TileEntity> tileClass, String tileName, float hardness, float resistance) {
        super(mat);
        this.name = name;
        this.tileClass = tileClass;
        this.tileName = tileName;

        RegistryHandler.addItemToRegister(this);
    }

    public BlockTileBase(String name, Class<? extends TileEntity> tileClass, String tileName, float hardness, float resistance) { this(name, Material.ROCK, tileClass, tileName, hardness, resistance); }

    public BlockTileBase(String name, Material mat, Class<? extends TileEntity> tileClass, String tileName) { this(name, mat, tileClass, tileName, 5, 5); }

    public BlockTileBase(String name, Class<? extends TileEntity> tileClass, String tileName) { this(name, Material.ROCK, tileClass, tileName, 5, 5); }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        try {
            return tileClass.newInstance();
        } catch(Exception e){
            return null;
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
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
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public String getUnlocalizedName() {
        return this.name;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileBase) ((TileBase) tile).dropInventory();
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if(tileEntity instanceof TileBase) drops.add(((TileBase)tileEntity).getDataDropped(state, fortune));
        else super.getDrops(drops, world, pos, state, fortune);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, false);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        super.harvestBlock(worldIn, player, pos, state, te, stack);
        worldIn.setBlockToAir(pos);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof TileBase)
            ((TileBase) tileEntity).loadData(stack);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.updateRedstoneState(worldIn, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.updateRedstoneState(worldIn, pos);
    }

    private void updateRedstoneState(World world, BlockPos pos) {
        if(!world.isRemote){
            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof TileBase){
                ((TileBase) tileEntity).redstoneActive = world.getRedstonePowerFromNeighbors(pos);
            }
        }
    }
}
