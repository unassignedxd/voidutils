package com.github.unassignedxd.voidutils.main.registry;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.Map;

/**
 * Implement this when something has a model.
 */
public interface IModelProvider {
    default Map<ItemStack, ModelResourceLocation> getItemStackModelLocations() {
        ItemStack itemStack;

        if(this instanceof Item) {
            itemStack = new ItemStack((Item) this);
        }else {
            itemStack = new ItemStack((Block) this);
        }
        String name = ((IModItem) this).getUnlocalizedName();
        return Collections.singletonMap(itemStack, new ModelResourceLocation(new ResourceLocation(VoidUtils.MOD_ID, name), "inventory"));
    }
}
