package com.github.unassignedxd.voidutils.main.blocks.tiles;

import com.github.unassignedxd.voidutils.main.VoidConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;

public class TileBase extends TileEntity implements ITickable {

    public int ticksAlive;
    public int redstoneActive;
    public TileEntity[] tilesNear = new TileEntity[6];

    /**
     * Call super method from extended TEs
     */
    @Override
    public void update() {
        if(shouldUpdate() && !this.world.isRemote){
            ticksAlive++;

            if(!shouldIgnoreDelay()){
                if(ticksAlive % VoidConfig.general.teUpdate == 0){
                    sendData();
                }
            } else { sendData(); }

            if(shouldDetectTiles()){
                for(EnumFacing facing : EnumFacing.VALUES){
                    TileEntity tile = world.getTileEntity(this.pos.offset(facing));
                    if(tile != null) tilesNear[facing.ordinal()] = tile;
                }
            }
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.writeToNBT(compound, SaveType.SAVE_TILE);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.readFromNBT(compound, SaveType.SAVE_TILE);
    }

    public void writeToNBT(NBTTagCompound compound, SaveType type) {
        if(type != SaveType.SAVE_BLOCK){
            super.writeToNBT(compound);
            compound.setInteger("TicksAlive", this.ticksAlive);
            compound.setInteger("RedstoneActive", this.redstoneActive);
        }
    }

    public void readFromNBT(NBTTagCompound compound, SaveType type){
        if(type != SaveType.SAVE_BLOCK){
            super.readFromNBT(compound);
            this.ticksAlive = compound.getInteger("TicksAlive");
            this.redstoneActive = compound.getInteger("RedstoneActive");
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound, SaveType.SYNC);
        return tagCompound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag, SaveType.SYNC);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        this.writeToNBT(tagCompound, SaveType.SYNC);
        return new SPacketUpdateTileEntity(this.getPos(), 0, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound(), SaveType.SYNC);
    }

    public void sendData() {
        WorldServer server = (WorldServer) this.world;
        PlayerChunkMapEntry chunkMap = server.getPlayerChunkMap().getEntry(this.getPos().getX() >> 4, this.getPos().getZ() >> 4);
        if(chunkMap != null) { chunkMap.sendPacket(this.getUpdatePacket()); }
    }

    public boolean shouldDetectTiles() { return false; }

    public boolean shouldIgnoreDelay() { return false; }

    public boolean shouldUpdate() { return true; }


    /* CAPABILITIES START*/

    public IItemHandlerModifiable getItemHandler(EnumFacing facing){
        return null;
    }

    public IEnergyStorage getEnergyStorage(EnumFacing facing){
        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY){
            return this.getEnergyStorage(facing) != null;
        } else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return this.getItemHandler(facing) != null;
        }

        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityEnergy.ENERGY){
            return (T) this.getEnergyStorage(facing);
        } else if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T) this.getItemHandler(facing);
        }
        return super.getCapability(capability, facing);
    }

    public void dropInventory(){
        IItemHandler handler = this.getItemHandler(null);
        if(handler != null){
            for(int i = 0; i < handler.getSlots(); i++){
                ItemStack stackAt = handler.getStackInSlot(i);
                if(!stackAt.isEmpty()){
                    EntityItem item = new EntityItem(this.world, this.pos.getX()+.5, this.pos.getY()+.5, this.pos.getZ()+.5, stackAt);
                    this.world.spawnEntity(item);
                }
            }
        }
    }

    public ItemStack getDataDropped(IBlockState state, int fortune){
        Block block = state.getBlock();
        ItemStack stack = new ItemStack(block.getItemDropped(state, this.world.rand, fortune), block.quantityDropped(state, fortune, this.world.rand), block.damageDropped(state));

        NBTTagCompound compound = new NBTTagCompound();
        this.writeToNBT(compound, SaveType.SAVE_BLOCK);

        if(!compound.isEmpty()){
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setTag("Data", compound);
        }

        return stack;
    }

    public void loadData(ItemStack stack){
        if(stack.hasTagCompound()){
            NBTTagCompound compound = stack.getTagCompound().getCompoundTag("data");
            if(compound != null) this.readFromNBT(compound, SaveType.SAVE_BLOCK);
        }
    }

    public enum SaveType {
        SAVE_TILE,  //Called when the game tries to save a tile.
        SAVE_BLOCK, //Called when the game tries to save block data, happens onBlockDestroy.
        SYNC        //Called when a packet is needed to be sent to the client.
    }
}
