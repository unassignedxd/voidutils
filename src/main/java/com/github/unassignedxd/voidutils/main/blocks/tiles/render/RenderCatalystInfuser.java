package com.github.unassignedxd.voidutils.main.blocks.tiles.render;

import com.github.unassignedxd.voidutils.main.util.ModHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class RenderCatalystInfuser extends TileEntitySpecialRenderer<TileCatalystInfuser> {

    private final Random rand = new Random();

    @Override
    public void render(TileCatalystInfuser te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack stack = te.inv.getStackInSlot(0);
        if(!stack.isEmpty()){
            GlStateManager.pushMatrix();
            GlStateManager.translate(x+.5f, y+1.5f, z+.5f); //todo y: testing to see item.
            GlStateManager.rotate(((te.getWorld().getWorldTime()*2) % 360), 0f, 1f, 0f);
            float scale = stack.getItem() instanceof ItemBlock ? 0.75f : 0.5f;
            GlStateManager.scale(scale, scale, scale);
            ModHelper.renderItemInWorld(stack);
            GlStateManager.popMatrix();
        }
    }
}
