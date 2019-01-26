package com.github.unassignedxd.voidutils.main.events;

import com.github.unassignedxd.voidutils.api.capability.voidchunk.IVoidChunk;
import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.block.ModBlocks;
import com.github.unassignedxd.voidutils.main.capability.voidchunk.CapabilityVoidChunk;
import com.github.unassignedxd.voidutils.main.init.IModObject;
import com.github.unassignedxd.voidutils.main.item.ModItems;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ClientEvents {

    // Will store all overlays that are to be displayed
    public static final ResourceLocation OVERLAYS = new ResourceLocation(VoidUtils.MOD_ID, "textures/gui/overlays.png");

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            ScaledResolution res = event.getResolution();
            if (mc.player != null) {
                EntityPlayer player = mc.player;

                ItemStack goggles = ItemStack.EMPTY;

                //todo baubles compat

                //todo add 'void watch' > crafted from goggles, displays even in inventory
                ItemStack helm = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                if (!helm.isEmpty()) {
                    if (helm.getItem() == ModItems.VOID_GOGGLES) {
                        goggles = helm;
                    }
                }

                if (!goggles.isEmpty()) {
                    IVoidChunk voidChunk = CapabilityVoidChunk.getVoidChunk(player.world.getChunk(player.getPosition()));
                    if (voidChunk != null && voidChunk.getVoidType() != null) {
                        int barWidth = MathHelper.ceil(((float) voidChunk.getVoidStored() / (float) voidChunk.getMaxVoidStored() * 40));

                        int sclX = (res.getScaledWidth() / 2) - 150;
                        int sclY = res.getScaledHeight() - 20;

                        GlStateManager.pushMatrix();
                        mc.getTextureManager().bindTexture(OVERLAYS);

                        GlStateManager.color(1f, 1f, 1f);
                        Gui.drawModalRectWithCustomSizedTexture(sclX, sclY, 0, 0, 43, 7, 256, 256);

                        int barColor = voidChunk.getVoidType().getDecimalColor();
                        GlStateManager.color((barColor >> 16 & 255) / 255f, (barColor >> 8 & 255) / 255f, (barColor & 255) / 255f);

                        if(barWidth > 0)
                            Gui.drawModalRectWithCustomSizedTexture(sclX+2, sclY+2, 0, 7, barWidth, 3, 256, 256);

                        float fontScale = .5F;
                        GlStateManager.scale(fontScale, fontScale, fontScale);

                        String s = voidChunk.getVoidStored() + "/" + voidChunk.getMaxVoidStored() + " | " + voidChunk.getVoidType().getId(); //todo make this only view w/ void watch
                        if(player.isSneaking()) {
                            mc.fontRenderer.drawString(s, (sclX / fontScale)+4, (sclY / fontScale)-10, 65280, true);
                        }

                        GlStateManager.color(1f, 1f, 1f);
                        GlStateManager.popMatrix();
                    }
                }
            }
        }
    }

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
