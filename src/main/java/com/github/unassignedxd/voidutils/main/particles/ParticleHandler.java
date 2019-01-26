package com.github.unassignedxd.voidutils.main.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class ParticleHandler {

    private static final ArrayList<Particle> CUR_PARTICLES = new ArrayList<>();

    public static void spawnParticle(Supplier<Particle> supplier, double x, double y, double z, int viewRange) {
        Minecraft mc = Minecraft.getMinecraft();
        if(mc.player.getDistanceSq(x, y, z) <= (viewRange * viewRange)) {
            int maxPart = mc.gameSettings.particleSetting;
            if(maxPart != 0 && (maxPart != 1 || mc.world.rand.nextInt(3) != 0) && (maxPart != 2 || mc.world.rand.nextInt(9) != 0))
                return;
        }
        CUR_PARTICLES.add(supplier.get());
    }

    public static void renderParticles(float partials) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if(player != null) {
            float rotX = ActiveRenderInfo.getRotationX();
            float rotXZ = ActiveRenderInfo.getRotationXZ();
            float rotZ = ActiveRenderInfo.getRotationZ();
            float rotYZ = ActiveRenderInfo.getRotationYZ();
            float rotXY = ActiveRenderInfo.getRotationXY();

            Particle.interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partials;
            Particle.interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partials;
            Particle.interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partials;
            Particle.cameraViewDir = player.getLook(partials);

            GlStateManager.pushMatrix();
            GlStateManager.enableAlpha(); //allows for alpha transparency
            GlStateManager.enableBlend(); //allows for color blending
            GlStateManager.alphaFunc(516, 0.003921569F); //default minecraft rendering func
            GlStateManager.disableCull(); //disable model culling

            GlStateManager.depthMask(false); //disable depth masking

            mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buf = tessellator.getBuffer();

            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            buf.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            for (Particle particle : CUR_PARTICLES) {
                particle.renderParticle(buf, player, partials, rotX, rotXZ, rotZ, rotYZ, rotXY);
            }

            tessellator.draw();

            GlStateManager.enableCull();
            GlStateManager.depthMask(true);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, .1f); //reset func
            GlStateManager.popMatrix();

        }
    }

    public static void updateParticles() {
        for(Particle particle : CUR_PARTICLES) {
            particle.onUpdate();
            if(!particle.isAlive())
                CUR_PARTICLES.remove(particle);
        }
    }

    public static int getParticleAmount() { return CUR_PARTICLES.size(); }

    public static void clearParticles() {
        if(!CUR_PARTICLES.isEmpty()){
            CUR_PARTICLES.clear();
        }
    }
}
