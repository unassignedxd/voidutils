package com.github.unassignedxd.voidutils.main.block.functional.voidnode;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderVoidNode extends TileEntitySpecialRenderer<TileVoidNode> {

    public static int sphereIdOut;
    public static int sphereIdIn;

    @Override
    public void render(TileVoidNode te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(te != null) {
            Tessellator tessy = Tessellator.getInstance();
            BufferBuilder buf = tessy.getBuffer();

            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            GlStateManager.disableLighting();

            GlStateManager.alphaFunc(GL11.GL_ALWAYS, 0);
            GlStateManager.translate(x+.5f, y+.5f, z+.5f);
            GlStateManager.rotate(((te.getWorld().getWorldTime() / (float).1) % 360), 1, 0, 1);

            buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

            //south side [pos z] [parent x]
            buf.pos(-0.25f, +0.25f, +0.25f).endVertex();
            buf.pos(-0.25f, -0.25f, +0.25f).endVertex();
            buf.pos(+0.25f, -0.25f, +0.25f).endVertex();
            buf.pos(+0.25f, +0.25f, +0.25f).endVertex();

            //north side [neg z] [parent x]
            buf.pos(-0.25f, +0.25f, -0.25f).endVertex();
            buf.pos(-0.25f, -0.25f, -0.25f).endVertex();
            buf.pos(+0.25f, -0.25f, -0.25f).endVertex();
            buf.pos(+0.25f, +0.25f, -0.25f).endVertex();

            //east side [pos x] [parent z]
            buf.pos(+0.25f, +0.25f, -0.25f).endVertex();
            buf.pos(+0.25f, -0.25f, -0.25f).endVertex();
            buf.pos(+0.25f, -0.25f, +0.25f).endVertex();
            buf.pos(+0.25f, +0.25f, +0.25f).endVertex();

            //west side [neg x] [parent z]
            buf.pos(-0.25f, -0.25f, +0.25f).endVertex();
            buf.pos(-0.25f, -0.25f, -0.25f).endVertex();
            buf.pos(-0.25f, +0.25f, -0.25f).endVertex();
            buf.pos(-0.25f, +0.25f, +0.25f).endVertex();

            //top [pos y] [parent x & y]
            buf.pos(+0.25f, +0.25f, -0.25f).endVertex();
            buf.pos(+0.25f, +0.25f, +0.25f).endVertex();
            buf.pos(-0.25f, +0.25f, +0.25f).endVertex();
            buf.pos(-0.25f, +0.25f, -0.25f).endVertex();

            //bottom [neg y] [parent x & y]
            buf.pos(+0.25f, -0.25f, -0.25f).endVertex();
            buf.pos(+0.25f, -0.25f, +0.25f).endVertex();
            buf.pos(-0.25f, -0.25f, +0.25f).endVertex();
            buf.pos(-0.25f, -0.25f, -0.25f).endVertex();

            tessy.draw();

            GlStateManager.enableLighting();
            GlStateManager.enableCull();
            GlStateManager.disableAlpha();
            GlStateManager.popMatrix();
        }
    }
}
