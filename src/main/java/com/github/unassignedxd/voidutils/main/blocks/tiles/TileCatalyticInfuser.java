package com.github.unassignedxd.voidutils.main.blocks.tiles;

import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import com.github.unassignedxd.voidutils.api.infusion.modifier.IVoidModifier;
import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystRecipe;
import com.github.unassignedxd.voidutils.main.VoidConfig;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.init.ModItems;
import com.github.unassignedxd.voidutils.main.items.ItemVoidCatalystUninfused;
import com.github.unassignedxd.voidutils.main.network.PacketHandler;
import com.github.unassignedxd.voidutils.main.network.packets.PacketParticleStream;
import com.github.unassignedxd.voidutils.main.util.EnergyStorageCustom;
import com.github.unassignedxd.voidutils.main.util.ModHelper;
import com.github.unassignedxd.voidutils.main.util.infusion.ResourceCatalyst;
import com.github.unassignedxd.voidutils.main.util.infusion.VoidInfusion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileCatalyticInfuser extends TileBase implements IButtonReactor {

    public final ItemStackHandler inv = new ItemStackHandlerCustom(1, this, true) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    };

    public EnergyStorageCustom energyStorage = new EnergyStorageCustom(500000000, VoidConfig.general.catalyticInfuserMaxReceive, 0);
    public int customCapacity = 1; //This is used to set the capacity of this machine to match the recipe requirement.

    public int processTime;
    public int maxProcessTime = 180;
    public boolean doWork = false;

    private boolean hasEnergy = false;

    @Override
    public void update() {
        super.update();

        if(!this.world.isRemote){
            if(doWork || this.redstoneActive > 0) {
                if(this.isVoidInfusionCrafting()) {
                    if(!doInfusionCrafting()) {
                        this.resetMachine();
                    }
                }
                else {
                    if(!doResourceCrafting()){
                        this.resetMachine();
                    }
                }
            }
        }
    }

    private boolean doInfusionCrafting() {                      /* -- VOID CRAFTING START --*/
        ArrayList<TileInfuser> infusers = getInfusersAround();
        if(infusers.size() < 2) return false;

        VoidInfusion infusion = getVoidInfusion(infusers, this.inv.getStackInSlot(0));
        if(infusion == null) return false;

        int energyUse = infusion.getEnergyUse(); //todo custom capacity
        if(energyUse < this.energyStorage.getEnergyStored() || this.hasEnergy){
            this.hasEnergy = true;

            this.processTime++;
            boolean done = this.processTime >= this.maxProcessTime;

            for(TileInfuser infuser : infusers) {
                if(infuser.hasItemStack()) {
                    if(this.ticksAlive % 5 == 0)
                        PacketHandler.sendToAllAround(this.world, this.getPos(), 32, new PacketParticleStream(
                                infuser.getPos().getX()+.5f, infuser.getPos().getY()+1.5f, infuser.getPos().getZ()+.5f,
                                this.getPos().getX()+.5f, this.getPos().getY()+1.5f, this.getPos().getZ()+.5f,
                                0.5f, 3093045, 1f, 1
                        ));
                }
                if(done) {
                    infuser.inv.setStackInSlot(0, ItemStack.EMPTY);
                    infuser.markDirty();
                }
            }

            if(done) {
                this.inv.setStackInSlot(0, createVoidCatalyst(infusion));
                this.markDirty();
                return true;
            }
        } else { return false; }

        return true;
    }

    private ItemStack createVoidCatalyst(VoidInfusion infusion) {
        ItemStack returnStack = new ItemStack(ModItems.VOID_CATALYST);
        NBTTagCompound compound = writeVoidCatalystData(infusion, new NBTTagCompound());

        ModHelper.applyData(returnStack, compound);

        if(!returnStack.hasTagCompound() ||
                !returnStack.getTagCompound().hasKey("VoidAmount") || !returnStack.getTagCompound().hasKey("PowerAmount") || !returnStack.getTagCompound().hasKey("DepletionRateAmount") || !returnStack.getTagCompound().hasKey("EnergyUse")) {
            VoidUtils.logger.error("Failed to properly write catalytic data to a catalyst! This should not happen!");
        }

        return returnStack;
    }

    private NBTTagCompound writeVoidCatalystData(VoidInfusion infusion, NBTTagCompound compound){
        compound.setDouble("VoidAmount", infusion.getVoidAmount());
        compound.setDouble("PowerAmount", infusion.getPowerAmount());
        compound.setDouble("DepletionRateAmount", infusion.getDepletionAmount());
        compound.setInteger("EnergyUse", infusion.getEnergyUse());
        return compound;
    }

    private VoidInfusion getVoidInfusion(ArrayList<TileInfuser> infusers, ItemStack catalyst) {
        ArrayList<ItemStack> items = new ArrayList<>();
        for(int i = 0; i < infusers.size(); i++){
            items.add(infusers.get(i).getStackInSlot());
        }
        if(items.size() < 8) return null;

        ArrayList<IVoidModifier> listModifiers = new ArrayList<>();
        for(ItemStack stack : items) {
            for(IVoidModifier modifier : VoidUtilsAPI.VOID_INFUSION_MODIFIERS.values()){
                if(ItemStack.areItemsEqual(modifier.getItem(), stack)){
                    listModifiers.add(modifier);
                }
            }
        }

        IVoidModifier[] arrayModifiers = new IVoidModifier[listModifiers.size()];
        for(int i = 0; i < listModifiers.size(); i++){
            arrayModifiers[i] = listModifiers.get(i);
        }

        VoidInfusion infusion = new VoidInfusion(arrayModifiers);

        ModHelper.limitInfusion(infusion, catalyst);

        return infusion.isUseless() ? null : infusion;
    }

    private boolean doResourceCrafting() {                      /* -- RESOURCE CRAFTING START --*/
        ResourceCatalystRecipe recipe = getCatalystFromInput();
        if(recipe == null) return false;

        ArrayList<TileInfuser> infusers = getInfusersAround();
        if(infusers.size() != 8) return false;

        if(!doesRecipeMatch(recipe, infusers)) return false;

        int energyUse = recipe.energyUse; //todo custom capacity
        if(energyUse < this.energyStorage.getEnergyStored()  || this.hasEnergy){
            this.hasEnergy = true;

            this.processTime++;
            boolean done = this.processTime >= this.maxProcessTime;

            for(TileInfuser infuser : infusers) {
                if(infuser.hasItemStack()) {
                    if(this.ticksAlive % 5 == 0)
                    PacketHandler.sendToAllAround(this.world, this.getPos(), 32, new PacketParticleStream(
                            infuser.getPos().getX()+.5f, infuser.getPos().getY()+1.5f, infuser.getPos().getZ()+.5f,
                            this.getPos().getX()+.5f, this.getPos().getY()+1.5f, this.getPos().getZ()+.5f,
                            0.5f, 3093045, 1f, 1
                            ));
                }
                if(done) {
                    infuser.inv.setStackInSlot(0, ItemStack.EMPTY);
                    infuser.markDirty();
                }
            }

            if(done) {
                this.inv.setStackInSlot(0, createResourceCatalyst(recipe));
                this.markDirty();
                return true;
            }
        } else return false;

        return true;
    }

    public static ItemStack createResourceCatalyst(ResourceCatalystRecipe recipe) {
        ResourceCatalyst catalyst = recipe.output;
        if(catalyst == null) return ItemStack.EMPTY;

        ItemStack returnStack = new ItemStack(ModItems.RESOURCE_CATALYST);
        NBTTagCompound compound = writeResourceCatalystData(catalyst, new NBTTagCompound());

        ModHelper.applyData(returnStack, compound);

        if(!returnStack.hasTagCompound() ||
                !returnStack.getTagCompound().hasKey("EnergyUse") || !returnStack.getTagCompound().hasKey("DepletionRate") || !returnStack.getTagCompound().hasKey("ProcessRate") || !returnStack.getTagCompound().hasKey("id")) {
            VoidUtils.logger.error("Failed to properly write catalytic data to a catalyst! This should not happen!");
        }

        return returnStack;
    }

    private static NBTTagCompound writeResourceCatalystData(ResourceCatalyst catalyst, NBTTagCompound compound){
        compound.setInteger("EnergyUse", catalyst.getPowerUseAmount());
        compound.setDouble("DepletionRate", catalyst.getDepletionRate());
        compound.setInteger("ProcessRate", catalyst.getDupeRate());
        catalyst.getResourceDupe().writeToNBT(compound);
        return compound;
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
            BlockPos horzOffset = this.getPos().offset(facing, 4).up();
            BlockPos diagOffset = this.getPos().offset(facing, 3).offset(facing.rotateYCCW(), 3).up();
            TileEntity horzTile = this.world.getTileEntity(horzOffset);
            TileEntity diagTile = this.world.getTileEntity(diagOffset);
            if(horzTile instanceof TileInfuser && diagTile instanceof TileInfuser) {
                returnList.add((TileInfuser)horzTile);
                returnList.add((TileInfuser)diagTile);
            }
        }
        return returnList;
    }

    private boolean isVoidInfusionCrafting()  {
        return this.inv.getStackInSlot(0).getItem() instanceof ItemVoidCatalystUninfused;
    }

    private void resetMachine() {
        this.processTime = 0;
        this.hasEnergy = false;
        this.doWork = false;
        this.customCapacity = 1;
        //this.energyStorage.setCapacity(customCapacity);
    }

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
        this.energyStorage.serializeNBT();
        if(type != SaveType.SAVE_BLOCK){
            inv.serializeNBT();
            compound.setInteger("ProcessTime", this.processTime);
            compound.setInteger("CustomCapacity", this.customCapacity);
            compound.setBoolean("DoWork", this.doWork);
            compound.setBoolean("HasEnergy", this.hasEnergy);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound, SaveType type) {
        super.readFromNBT(compound, type);
        this.energyStorage.deserializeNBT(compound);
        if(type != SaveType.SAVE_BLOCK) {
            inv.deserializeNBT(compound);
            this.processTime = compound.getInteger("ProcessTime");
            this.customCapacity = compound.getInteger("CustomCapacity");
            this.doWork = compound.getBoolean("DoWork");
            this.hasEnergy = compound.getBoolean("HasEnergy");
        }
    }

    @Override
    public void onButtonPressed(int buttonID, EntityPlayer player) {
        if(buttonID == 0) this.doWork = !this.doWork;
    }
}
