package com.github.unassignedxd.voidutils.main.util;

import com.github.unassignedxd.voidutils.main.VoidConfig;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.blocks.tiles.TileBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.oredict.OreDictionary;

import javax.vecmath.Vector3f;

/**
 * This class will hold many helper methods that are called more than once, or are just helpful in development.
 */
public final class ModHelper {

    public static final ResourceLocation GUI_INVENTORY_LOCATION = new ResourceLocation(VoidUtils.MOD_ID, "textures/gui/gui_inventory.png");

    /**
     * Renders an item in the world at Gl translated positions
     * @param stack the stack to be rendered
     */
    @SideOnly(Side.CLIENT)
    public static void renderItemInWorld(ItemStack stack) {
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }

    /**
     * Put an ItemStack into an accepting TileEntity's Inventory
     * @param player the player doing the interaction
     * @param hand the hand with the item
     * @param pos the position of the block being interacted with
     * @param slotID the slot where the interaction is happening
     * @return whether the interaction was sucessful or not.
     */
    public static boolean putStackInTile(EntityPlayer player, EnumHand hand, BlockPos pos, int slotID) {
        TileEntity tile = player.world.getTileEntity(pos);
        if (tile instanceof TileBase) {
            IItemHandlerModifiable handler = ((TileBase) tile).getItemHandler(null);
            if (handler != null) {
                ItemStack handStack = player.getHeldItem(hand);
                ItemStack mergeStack = handler.getStackInSlot(slotID);
                if (mergeStack.isEmpty()) {
                    if (!handStack.isEmpty()) {
                        player.world.playSound(player, tile.getPos(), SoundEvents.ENTITY_ITEMFRAME_PLACE, SoundCategory.BLOCKS, .5f, 1f);
                        if (!player.world.isRemote) {
                            ItemStack setStack = handStack.copy();
                            setStack.setCount(1);
                            handler.insertItem(slotID, setStack, false);
                            handStack.shrink(1);
                        }
                        return true;
                    }
                }else {
                    player.world.playSound(player, tile.getPos(), SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.BLOCKS, .5f, 1f);
                    if (!player.world.isRemote) {
                        player.inventory.addItemStackToInventory(mergeStack.copy());
                        handler.extractItem(slotID, 1, false);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * @return The catalyst types that exist.
     */
    public static NonNullList<ItemStack> getAcceptableCatalystTypes() {
        NonNullList<ItemStack> returnList = NonNullList.create();

        for(String oreName : OreDictionary.getOreNames()) {
            if(oreName.contains("ingot")){
                for(ItemStack ingot : OreDictionary.getOres(oreName)){
                    for(String idBlack : VoidConfig.general.resourceCatalystBlacklist){
                        if(idBlack != ingot.getItem().getRegistryName().toString()){
                            returnList.add(ingot);
                        }
                    }
                }
            }
        }
        for(String stringLoc : VoidConfig.general.resourceCatalystTypes){
            ResourceLocation location = new ResourceLocation(stringLoc);
            if(ForgeRegistries.ITEMS.containsKey(location)){
                returnList.add(new ItemStack(ForgeRegistries.ITEMS.getValue(location)));
            }else if(ForgeRegistries.BLOCKS.containsKey(location)){
                returnList.add(new ItemStack(ForgeRegistries.BLOCKS.getValue(location)));
            }
        }

        return returnList;
    }

    public static ItemStack applyData(ItemStack stack, NBTTagCompound data) {
        stack.setTagCompound(data);
        return stack;
    }

    /**
     * Gets the NBTTagCompound from
     * @param stack to ge the compound from
     * @return the data attached to the compound.
     */
    public static NBTTagCompound getData(ItemStack stack){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null) return new NBTTagCompound();

        return compound;
    }

    /**
     * Clears keys from an ItemStack
     * @param stack the stack to remove the data from
     * @param keys the key names themselves.
     */
    public static void clearNBT(ItemStack stack, String... keys){
        if(stack.hasTagCompound()){
            NBTTagCompound compound = stack.getTagCompound();
            for(String key : keys){
                compound.removeTag(key);
            }
        }
    }

    public static void spawnInfuseParticle(double posX, double posY, double posZ, double motionX, double motionY, double motionZ, int color, float alpha, float scale,
                                    int maxAge, float gravity, boolean collision, boolean fade){
        VoidUtils.proxy.spawnInfusionParticle(posX, posY, posZ, motionX, motionY, motionZ, color, alpha, scale, maxAge, gravity, collision, fade);
    }

    public static void spawnInfuseParticleStream(float startX, float startY, float startZ, float endX, float endY, float endZ, float speed, int color, float alpha, float scale){
        Vector3f vector = new Vector3f(endX-startX, endY-startY, endZ-startZ);
        if(vector.length() > 0) {
            int maxAge = (int)(vector.length() / speed);
            vector.normalize();

            spawnInfuseParticle(startX, startY, startZ,
                    vector.x * speed, vector.y * speed, vector.z * speed,
                    color, alpha, scale, maxAge, 0f, false, false);
        }
    }

}
