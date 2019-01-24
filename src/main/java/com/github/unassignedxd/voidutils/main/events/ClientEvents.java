package com.github.unassignedxd.voidutils.main.events;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.block.ModBlocks;
import com.github.unassignedxd.voidutils.main.init.IModObject;
import com.github.unassignedxd.voidutils.main.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ClientEvents {

    public static HashMap<ItemStack, ModelResourceLocation> OBJECT_MODEL_REG = new HashMap<>();

    @SubscribeEvent
    public void onModelRegistryEvent(ModelRegistryEvent event) {
        for (Item item : ModItems.ITEMS) {
            if (item instanceof IModObject) {
                ((IModObject) item).registerRenderers();
            }
        }
        for (Block block : ModBlocks.BLOCKS) {
            if (block instanceof IModObject) {
                ((IModObject) block).registerRenderers();
            }
        }

        for (Map.Entry<ItemStack, ModelResourceLocation> entry : OBJECT_MODEL_REG.entrySet()) {
            ModelLoader.setCustomModelResourceLocation(entry.getKey().getItem(), entry.getKey().getItemDamage(), entry.getValue());
        }

        VoidUtils.logger.info("Successfully Registered Models!");
    }
}
