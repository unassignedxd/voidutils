package com.github.unassignedxd.voidutils.main.inventory.gui;

import com.github.unassignedxd.voidutils.main.VoidUtils;
import com.github.unassignedxd.voidutils.main.blocks.tiles.TileBase;
import com.github.unassignedxd.voidutils.main.blocks.tiles.TileCatalyticInfuser;
import com.github.unassignedxd.voidutils.main.inventory.container.ContainerCatalystInfuser;
import com.github.unassignedxd.voidutils.main.network.PacketHandler;
import com.github.unassignedxd.voidutils.main.network.packets.PacketButtonToTile;
import com.github.unassignedxd.voidutils.main.util.ModHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiCatalystInfuser extends GuiMod {

    private static final ResourceLocation RESOURCE = new ResourceLocation(VoidUtils.MOD_ID, "textures/gui/gui_catalyst_infuser");
    private final TileCatalyticInfuser machine;

    public GuiCatalystInfuser(InventoryPlayer inventoryPlayer, TileBase tile){
        super(new ContainerCatalystInfuser(inventoryPlayer, tile));

        this.machine = (TileCatalyticInfuser) tile;
        this.xSize = 50;
        this.ySize = 50;
    }

    @Override
    public void initGui() {
        super.initGui();

        GuiButton start = new GuiButton(0, this.guiLeft + 25, this.guiTop + 10, 25, 10, "haha n word");
        this.buttonList.add(start);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawStringWithShadow("n word", this.xSize, -10, 0);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(ModHelper.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop+93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(RESOURCE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        PacketHandler.sendToServer(new PacketButtonToTile(machine.getPos().getX(), machine.getPos().getY(), machine.getPos().getZ(),
                                                            machine.getWorld().provider.getDimension(), Minecraft.getMinecraft().player.getEntityId(), button.id));
    }
}
