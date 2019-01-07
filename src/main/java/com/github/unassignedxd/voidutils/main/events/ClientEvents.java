package com.github.unassignedxd.voidutils.main.events;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.particles.ParticleHandler;
import com.github.unassignedxd.voidutils.main.particles.ParticleInfuse;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public void onDebugRender(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.profiler.startSection(VoidUtils.MOD_ID + ":renderOverlays");
        if(mc.gameSettings.showDebugInfo) {
            String debugPrefix = TextFormatting.DARK_GRAY + "[" + VoidUtils.NAME + "]" + TextFormatting.RESET + " ";
            ArrayList<String> left = event.getLeft();
            left.add("");
            left.add(debugPrefix + "Rendered Particles: " + ParticleHandler.getParticleAmount());
        }
        mc.profiler.endSection();
    }

    @SubscribeEvent
    public void onRenderLast(RenderWorldLastEvent event) {
        ParticleHandler.renderParticles(event.getPartialTicks());
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent event) {
        event.getMap().registerSprite(ParticleInfuse.TEXTURE);
    }

    @SubscribeEvent
    public void onWorldRender(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END){
            Minecraft mc = Minecraft.getMinecraft();
            if(!mc.isGamePaused()) {
                ParticleHandler.updateParticles();
            }
            if(mc.world == null) { ParticleHandler.clearParticles(); }
        }
    }
}
