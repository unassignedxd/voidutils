package com.github.unassignedxd.voidutils.main.items;

import com.github.unassignedxd.voidutils.main.util.ModHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemResourceCatalyst extends ItemBase {

    public ItemResourceCatalyst(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    public static void storeData(ItemStack stack, ItemStack dupeItem, int powerUse, double depletionRateAmount){
        NBTTagCompound compound = stack.getTagCompound();
        if(compound == null)
            compound = new NBTTagCompound();

        dupeItem.writeToNBT(compound);
        compound.setInteger("EnergyUse", powerUse);
        compound.setDouble("DepletionRate", depletionRateAmount);

        stack.setTagCompound(compound);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = player.getHeldItem(hand);
        if(!world.isRemote){
            if(player.isSneaking()){
                ModHelper.clearNBT(held, "EnergyUse", "DepletionRate", "id");
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
        if(!stack.isEmpty()) {
            NBTTagCompound compound = stack.getTagCompound();
            if(compound != null) {
                if(compound.hasKey("id") && compound.hasKey("EnergyUse") && compound.hasKey("DepletionRate")) {
                    tooltip.add("Item: " + compound.getTag("id"));
                    tooltip.add("Power Use / t: " + compound.getInteger("PowerUse"));
                    tooltip.add("Depletion Rate / dt: " + compound.getDouble("DepletionRate"));
                }
            }
        }
    }
}
