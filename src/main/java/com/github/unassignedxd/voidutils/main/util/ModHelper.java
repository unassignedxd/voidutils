package com.github.unassignedxd.voidutils.main.util;

import com.github.unassignedxd.voidutils.main.blocks.tiles.TileBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * This class will hold many helper methods that are called more than once, or are just helpful in development.
 */
public final class ModHelper {

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
    public static boolean putStackInTile(EntityPlayer player, EnumHand hand, BlockPos pos, int slotID){
        TileEntity tile = player.world.getTileEntity(pos);
        World world = player.world;
        if(tile instanceof TileBase){
            IItemHandlerModifiable itemHandler = ((TileBase)tile).getItemHandler(null);
            if(itemHandler != null){
                ItemStack handStack = player.getHeldItem(hand);
                if(!handStack.isEmpty()){
                    ItemStack check = itemHandler.insertItem(slotID, handStack, player.world.isRemote);
                    if(!ItemStack.areItemsEqual(check, handStack)){ //checks to see if the player already has the stack on them, and then some.
                        world.playSound(player, pos.getX()+.5, pos.getY()+.5, pos.getZ()+.5, SoundEvents.ENTITY_ITEMFRAME_ADD_ITEM, SoundCategory.PLAYERS, .75f, 1f);
                        if(!world.isRemote) player.setHeldItem(hand, check);
                        return true;
                    }
                }
                if(!itemHandler.getStackInSlot(slotID).isEmpty()){
                    world.playSound(player, pos.getX()+.5, pos.getY()+.5, pos.getZ()+.5, SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.PLAYERS, .75f, 1f);

                    if(!world.isRemote) {
                        player.addItemStackToInventory(itemHandler.getStackInSlot(slotID));
                        itemHandler.setStackInSlot(slotID, ItemStack.EMPTY);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
