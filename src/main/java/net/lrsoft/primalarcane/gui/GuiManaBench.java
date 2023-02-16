package net.lrsoft.primalarcane.gui;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.block.tileentity.TileEntityManaBench;
import net.lrsoft.primalarcane.container.ContainerManaBench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiManaBench extends GuiContainer {
    private static ResourceLocation backgroundTex = new ResourceLocation(PrimalArcane.MODID, "textures/gui/mana_bench.png");
    private TileEntityManaBench tileEntity;
    private InventoryPlayer playerInventory;
    public GuiManaBench(InventoryPlayer inventory, TileEntityManaBench te) {
        super(new ContainerManaBench(inventory, te));
        tileEntity = te;
        playerInventory = inventory;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // 渲染ui标题
        String info = I18n.format("primalarcane.text.mana_bench");
        this.fontRenderer.drawString(info, 110 - fontRenderer.getStringWidth(info), 6, Integer.parseInt("000000", 16));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        // 渲染大背景图
        {
            this.mc.renderEngine.bindTexture(backgroundTex);
            this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        }
    }
}
