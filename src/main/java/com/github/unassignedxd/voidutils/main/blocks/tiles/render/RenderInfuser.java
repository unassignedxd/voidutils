package com.github.unassignedxd.voidutils.main.blocks.tiles.render;

import com.github.unassignedxd.voidutils.main.blocks.tiles.TileInfuser;
import com.github.unassignedxd.voidutils.main.util.ModHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class RenderInfuser extends TileEntitySpecialRenderer<TileInfuser> {

    @Override
    public void render(TileInfuser te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack stack = te.getStackInSlot();
        if(!stack.isEmpty()){
            GlStateManager.pushMatrix();
            GlStateManager.translate(x+.5f, y+1.5f, z+.5f); //todo y: testing to see item.
            float scale = stack.getItem() instanceof ItemBlock ? 0.75f : 0.5f;
            GlStateManager.scale(scale, scale, scale);
            ModHelper.renderItemInWorld(stack);
            GlStateManager.popMatrix();
        }
    }
}
