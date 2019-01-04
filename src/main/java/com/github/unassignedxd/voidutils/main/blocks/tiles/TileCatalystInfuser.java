package com.github.unassignedxd.voidutils.main.blocks.tiles;

import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import com.github.unassignedxd.voidutils.api.recipe.CatalystInfusionRecipe;
import com.github.unassignedxd.voidutils.main.util.EnergyStorageCustom;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class TileCatalystInfuser extends TileBase {

    public final ItemStackHandler inv = new ItemStackHandlerCustom(1, this, true){
        @Override
        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return 1;
        }
    };

    public final EnergyStorageCustom energyStorage = new EnergyStorageCustom(1, 500000000, 500000000);

    public int customCapacity = 0;
    public int processTime = 0;
    public final int maxProcessTime = 180; //9 seconds - todo 90s

    private CatalystInfusionRecipe currentRecipe;

    @Override
    public void update() {
        super.update();
        if(!this.world.isRemote){

            if(currentRecipe == null){
                this.currentRecipe = getRecipeFromInput();
                this.resetMachine();
            } else if(getInfusers() != null){
                int energyNeed = currentRecipe.energyUse;
                this.energyStorage.setCapacity(energyNeed);
                if(hasMatchingIngredients(currentRecipe) && energyStorage.atCapacity()){
                    float energyPerTick = (float)energyNeed / (float)processTime;
                    this.processTime++;

                    boolean done = this.processTime >= this.maxProcessTime;
                    this.energyStorage.extractEnergy((int)energyPerTick, false);

                    for(TileInfuser infuser : getInfusers()){
                        if(done){
                            infuser.getStackInSlot().shrink(1);
                            infuser.markDirty();
                        }
                    }

                    if(this.processTime % 5 == 0 && this.world instanceof WorldServer) {
                        ((WorldServer) this.world).spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, false, this.pos.getX() + 0.5, this.pos.getY() + 1.1, this.pos.getZ() + 0.5, 10, 0, 0, 0, 0.1D);
                    }

                    if(done){
                        this.inv.setStackInSlot(0, currentRecipe.output.copy());
                        this.markDirty();
                        ((WorldServer) this.world).spawnParticle(EnumParticleTypes.END_ROD, false, this.pos.getX() + 0.5, this.pos.getY() + 1.1, this.pos.getZ() + 0.5, 100, 0, 0, 0, 0.25D);
                        this.resetMachine();
                    }

                }
            } else {
                resetMachine();
            }

        }
    }

    public ArrayList<TileInfuser> getInfusers() {
        ArrayList<TileInfuser> nearInfusers = new ArrayList<>();
        for(EnumFacing facing : EnumFacing.HORIZONTALS){
            BlockPos offsetHorz = this.getPos().offset(facing, 4).up();
            BlockPos offsetDiag = this.getPos().offset(facing, 3).offset(facing.rotateYCCW(), 3).up();
            TileEntity horzTile = world.getTileEntity(offsetHorz);
            TileEntity diagTile = world.getTileEntity(offsetDiag);
            if(horzTile instanceof TileInfuser && diagTile instanceof TileInfuser) {
                nearInfusers.add((TileInfuser)horzTile);
                nearInfusers.add((TileInfuser)diagTile);
            } else return null;
        }

        return nearInfusers;
    }

    public CatalystInfusionRecipe getRecipeFromInput(){
        ItemStack inputStack = this.inv.getStackInSlot(0);
        if(!inputStack.isEmpty()){
            for(CatalystInfusionRecipe recipe : VoidUtilsAPI.RESOURCE_INFUSION_RECIPES.values()){
                if(recipe.input.apply(inputStack)){
                    return recipe;
                }
            }
        }
        return null;
    }

    private boolean hasMatchingIngredients(CatalystInfusionRecipe recipe){
        ItemStack[] stacks = new ItemStack[8];
        ArrayList<TileInfuser> infusers = getInfusers();
        for(int i = 0; i < infusers.size(); i++){
            stacks[i] = infusers.get(i).getStackInSlot();
        }
        return recipe.matches(this.inv.getStackInSlot(0), stacks[0], stacks[1], stacks[2], stacks[3], stacks[4], stacks[5], stacks[6], stacks[7]);
    }

    private void resetMachine() {
        this.processTime=0;
        this.customCapacity=0;
        this.energyStorage.setCapacity(customCapacity);
    }

    @Override
    public IItemHandlerModifiable getItemHandler(EnumFacing facing) {
        return this.inv;
    }

    @Override
    public IEnergyStorage getEnergyStorage(EnumFacing facing) {
        return this.energyStorage;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, SaveType type) {
        super.readFromNBT(compound, type);
        this.energyStorage.deserializeNBT(compound);
        if(type != SaveType.SAVE_BLOCK){
            this.inv.deserializeNBT(compound);
            this.customCapacity = compound.getInteger("CustomCapacity");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound, SaveType type) {
        super.writeToNBT(compound, type);
        this.energyStorage.serializeNBT();
        if(type != SaveType.SAVE_BLOCK){
            this.inv.serializeNBT();
            compound.setInteger("CustomCapacity", this.customCapacity);
        }
    }
}
