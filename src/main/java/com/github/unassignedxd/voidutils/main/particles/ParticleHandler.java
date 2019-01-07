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
import org.lwjgl.opengl.GLSync;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SideOnly(Side.CLIENT)
public class ParticleHandler {

    private static final ArrayList<Particle> PARTICLES = new ArrayList<>();

    public static void spawnParticle(Supplier<Particle> supplier, double x, double y, double z, int viewRange){
        if(Minecraft.getMinecraft().player.getDistanceSq(x,y,z) <= viewRange * viewRange) {
            Minecraft mc = Minecraft.getMinecraft();
            int setting = mc.gameSettings.particleSetting;

            /*Making sure my particles apply to the ingame particle setting.*/
            if(setting != 0 && (setting != 1 || mc.world.rand.nextInt(3) != 0) && (setting != 2 || mc.world.rand.nextInt(10) != 0)) return;
            //if(setting < 1 && mc.world.rand.nextDouble() > setting) return;

            PARTICLES.add(supplier.get());
        }
    }

    public static void updateParticles() {
        for(int i = 0; i < PARTICLES.size(); i++){
            Particle particle = PARTICLES.get(i);
            particle.onUpdate();
            if(!particle.isAlive()) {
                PARTICLES.remove(i);
            }
        }
    }

    public static int getParticleAmount() { return PARTICLES.size(); }

    public static void clearParticles() {
        if (!PARTICLES.isEmpty()){
            PARTICLES.clear();
        }
    }

    /*Rendering particles using only partial ticks and using the PARTICLES list to render them. */
    public static void renderParticles(float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        if(player != null) {
            float x = ActiveRenderInfo.getRotationX();
            float z = ActiveRenderInfo.getRotationZ();
            float yz = ActiveRenderInfo.getRotationYZ();
            float xy = ActiveRenderInfo.getRotationXY();
            float xz = ActiveRenderInfo.getRotationXZ();

            Particle.interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
            Particle.interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
            Particle.interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
            Particle.cameraViewDir = player.getLook(partialTicks);

            GlStateManager.pushMatrix();

            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.alphaFunc(516, 0.003921569F);
            GlStateManager.disableCull();

            GlStateManager.depthMask(false);

            mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();

            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
            for(Particle particle : PARTICLES) {
                particle.renderParticle(buffer, player, partialTicks, x, xz, z, yz, xy);
            }

            tessellator.draw();

            GlStateManager.enableCull();
            GlStateManager.depthMask(true);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1f);

            GlStateManager.popMatrix();
        }
    }
}
