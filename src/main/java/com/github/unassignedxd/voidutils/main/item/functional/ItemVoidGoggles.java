package com.github.unassignedxd.voidutils.main.item.functional;

import com.github.unassignedxd.voidutils.main.item.base.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemVoidGoggles extends ItemBase {

    public ItemVoidGoggles() {
        super("void_goggles");
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn != null && playerIn != null) {
            ItemStack held = playerIn.getHeldItem(handIn);
            if (!held.isEmpty()) {
                if (held.getItem() == this) {
                    ItemStack helm = playerIn.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                    if (!helm.isEmpty()) {
                        playerIn.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1);
                        if (worldIn.isRemote) {
                            ItemStack set = helm.copy();
                            playerIn.setItemStackToSlot(EntityEquipmentSlot.HEAD, held.copy());
                            playerIn.setHeldItem(handIn, set);
                        }
                    }else {
                        playerIn.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1, 1);
                        if (worldIn.isRemote) {
                            playerIn.setItemStackToSlot(EntityEquipmentSlot.HEAD, held.copy());
                            playerIn.setHeldItem(handIn, ItemStack.EMPTY);
                        }
                    }
                }
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

     @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EntityEquipmentSlot.HEAD;
    }
}
