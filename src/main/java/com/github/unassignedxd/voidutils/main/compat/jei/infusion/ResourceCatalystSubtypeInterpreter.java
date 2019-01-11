package com.github.unassignedxd.voidutils.main.compat.jei.infusion;

import com.github.unassignedxd.voidutils.api.VoidUtilsAPI;
import com.github.unassignedxd.voidutils.api.recipe.ResourceCatalystRecipe;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.blocks.tiles.TileCatalyticInfuser;
import com.github.unassignedxd.voidutils.main.init.ModItems;
import com.github.unassignedxd.voidutils.main.util.infusion.ResourceCatalyst;
import mezz.jei.api.ISubtypeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;

public class ResourceCatalystSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter {

    public static void registerSubtypes(ISubtypeRegistry registry){
        ResourceCatalystSubtypeInterpreter interpreter = new ResourceCatalystSubtypeInterpreter();
        for(final ResourceCatalystRecipe catalyst : VoidUtilsAPI.RESOURCE_INFUSION_RECIPES.values()){
            ItemStack output = TileCatalyticInfuser.createResourceCatalyst(catalyst);
            registry.registerSubtypeInterpreter(output.getItem(), interpreter);
        }
    }

    @Override
    public String apply(ItemStack itemStack) {
        if(itemStack.isEmpty()){
            throw new NullPointerException("Attempted to write data to an empty itemstack!");
        }
        if(itemStack.hasTagCompound()){
            NBTTagCompound compound = itemStack.getTagCompound();
            return itemStack.getDisplayName() + " " + compound.getTag("id").toString();
        }

        return "";
    }

}
