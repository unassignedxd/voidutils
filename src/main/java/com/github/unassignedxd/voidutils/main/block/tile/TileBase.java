package com.github.unassignedxd.voidutils.main.block.tile;

import com.github.unassignedxd.voidutils.main.VoidConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
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

    public int redstonePower;
    public int ticksElasped;
    public TileEntity[] nearbyTiles = new TileEntity[6];

    @Override
    public void update() { //always call super method from impls!
        if (shouldUpdate()) {
            ticksElasped++;
            if (shouldSendData()) {
                if (ticksElasped % getIntervalData() == 0) {
                    this.sendData();
                }
            }
            if (shouldDetectTEs()) {
                for (EnumFacing facing : EnumFacing.VALUES) {
                    nearbyTiles[facing.ordinal()] = world.getTileEntity(pos.offset(facing));
                }
            }
        }
    }

    protected boolean shouldUpdate() {
        return true;
    }

    protected boolean shouldSendData() {
        return true;
    }

    protected int getIntervalData() {
        return VoidConfig.tileEntities.teUpdateInterval;
    }

    protected boolean shouldDetectTEs() {
        return false;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        this.writeNBT(compound, SaveType.TILE);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.readNBT(compound, SaveType.TILE);
    }

    public void writeNBT(NBTTagCompound compound, SaveType saveType) {
        if (saveType != SaveType.BLOCK) {
            compound.setInteger("RedstonePower", this.redstonePower);
            compound.setInteger("TicksElapsed", this.ticksElasped);
            super.writeToNBT(compound);
        }
    }

    public void readNBT(NBTTagCompound compound, SaveType saveType) {
        if (saveType != SaveType.BLOCK) {
            this.redstonePower = compound.getInteger("RedstonePower");
            this.ticksElasped = compound.getInteger("TicksElasped");
            super.readFromNBT(compound);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeNBT(compound, SaveType.SYNC);
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readNBT(tag, SaveType.SYNC);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        this.writeNBT(compound, SaveType.SYNC);
        return new SPacketUpdateTileEntity(this.getPos(), 1, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readNBT(pkt.getNbtCompound(), SaveType.SYNC);
    }

    @SuppressWarnings("ConstantConditions")
    public void sendData() {
        WorldServer server = (WorldServer) this.getWorld();
        PlayerChunkMapEntry chunkMapEntry = server.getPlayerChunkMap().getEntry(this.getPos().getX() >> 4, this.getPos().getZ() >> 4);
        if (chunkMapEntry != null) {
            chunkMapEntry.sendPacket(this.getUpdatePacket());
        }
    }

    /**
     * <--- Capability Start --->
     */

    public IItemHandlerModifiable getItemHandler(EnumFacing facing) {
        return null;
    }

    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.getItemHandler(facing) != null;
        } else if (capability == CapabilityEnergy.ENERGY) {
            return this.getEnergyStorage(facing) != null;
        } else {
            return super.hasCapability(capability, facing);
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) this.getItemHandler(facing);
        } else if (capability == CapabilityEnergy.ENERGY) {
            return (T) this.getEnergyStorage(facing);
        } else {
            return super.getCapability(capability, facing);
        }
    }

    public void dropInventory() {
        IItemHandler itemHandler = this.getItemHandler(null);
        if (itemHandler != null) {
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                ItemStack stack = itemHandler.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    EntityItem item = new EntityItem(this.world,
                            this.getPos().getX() + .5, this.getPos().getY() + .5, this.getPos().getZ() + .5,
                            stack);
                    this.world.spawnEntity(item);
                }
            }
        }
    }

    public ItemStack getDataDrop(IBlockState state, int fortune) {
        Block block = state.getBlock();
        ItemStack stack = new ItemStack(
                block.getItemDropped(state, this.world.rand, fortune),
                block.quantityDropped(state, fortune, this.world.rand),
                block.damageDropped(state)
        );

        NBTTagCompound dataWrite = new NBTTagCompound();
        this.writeNBT(dataWrite, SaveType.BLOCK);

        if (!dataWrite.isEmpty()) {
            stack.setTagCompound(new NBTTagCompound());
            stack.getTagCompound().setTag("SavedTEData", dataWrite);
        }

        return stack;
    }

    public void loadData(ItemStack stack) {
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound().getCompoundTag("SavedTEData");
            if (compound != null) {
                this.readNBT(compound, SaveType.BLOCK);
            }
        }
    }

    public enum SaveType {
        TILE, //saves data to the tile itself
        SYNC, //syncs between client and server
        BLOCK //if saved, the data will be stored on the itemblock
    }
}
