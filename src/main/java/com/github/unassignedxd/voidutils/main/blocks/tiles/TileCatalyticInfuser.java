package com.github.unassignedxd.voidutils.main.blocks.tiles;

import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystRecipe;
import com.github.unassignedxd.voidutils.main.VoidConfig;
import com.github.unassignedxd.voidutils.main.init.ModItems;
import com.github.unassignedxd.voidutils.main.util.EnergyStorageCustom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;

public class TileCatalyticInfuser extends TileBase implements IButtonReactor {

    public final ItemStackHandler inv = new ItemStackHandlerCustom(1, this, true) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public EnergyStorageCustom energyStorage = new EnergyStorageCustom(1, VoidConfig.general.catalyticInfuserMaxReceive, 0);
    public int customCapacity = 1; //This is used to set the capacity of this machine to match the recipe requirement.

    public int processTime;
    public int maxProcessTime = 180;
    public boolean doWork = false;

    private boolean hasEnergy = false;

    @Override
    public void update() {
        super.update();

        if(!this.world.isRemote){
            if(this.isVoidInfusionCrafting()) {
                doInfusionCrafting();
            }
            else {
                doResourceCrafting();
            }
        }
    }

    private void doInfusionCrafting() {

    }

    private void doResourceCrafting() {
        ResourceCatalystRecipe recipe = getCatalystFromInput();
        if(recipe == null) return;

        ArrayList<TileInfuser> infusers = getInfusersAround();
        if(infusers.size() != 8) return;

        if(!doesRecipeMatch(recipe, infusers)) return;

        int energyUse = recipe.energyUse;
        energyStorage.setCapacity(energyUse);
        if(this.energyStorage.atCapacity() || this.hasEnergy){
            this.hasEnergy = true;

            this.processTime++;
            boolean done = this.processTime >= this.maxProcessTime;

        }
    }

    private boolean doesRecipeMatch(ResourceCatalystRecipe recipe, ArrayList<TileInfuser> infusers){
        ItemStack[] arrayInf = new ItemStack[8];
        for(int i = 0; i < infusers.size(); i++){
            arrayInf[i] = infusers.get(i).getStackInSlot();
        }
        return recipe.matches(this.inv.getStackInSlot(0), arrayInf[0], arrayInf[1], arrayInf[2], arrayInf[3], arrayInf[4], arrayInf[5], arrayInf[6], arrayInf[7]);
    }

    private ResourceCatalystRecipe getCatalystFromInput() {
        if(this.inv.getStackInSlot(0) == ItemStack.EMPTY) return null;
        for(ResourceCatalystRecipe catalyst : VoidUtilsAPI.RESOURCE_INFUSION_RECIPES.values()){
            if(catalyst.input.apply(this.inv.getStackInSlot(0))) return catalyst;
        }
        return null;
    }

    private ArrayList<TileInfuser> getInfusersAround() {
        ArrayList<TileInfuser> returnList = new ArrayList<>();
        for(EnumFacing facing : EnumFacing.HORIZONTALS){
            BlockPos horzOffset = this.getPos().offset(facing, 4);
            BlockPos diagOffset = this.getPos().offset(facing, 2).offset(facing.rotateYCCW(), 2);
            TileEntity horzTile = this.world.getTileEntity(horzOffset);
            TileEntity diagTile = this.world.getTileEntity(diagOffset);
            if(horzTile instanceof TileInfuser && diagTile instanceof TileInfuser) {
                returnList.add((TileInfuser)horzTile);
                returnList.add((TileInfuser)diagTile);
            }
        }
        return returnList;
    }

    private boolean isVoidInfusionCrafting()  { return this.inv.getStackInSlot(0).getItem() == ModItems.VOID_UNINFUSED_CATALYST; }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.energyStorage;
    }

    @Override
    public IItemHandlerModifiable getItemHandler(EnumFacing facing) {
        return this.inv;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, SaveType type) {
        super.writeToNBT(compound, type);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, SaveType type) {
        super.readFromNBT(compound, type);
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player) {

    }
}
