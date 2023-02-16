package net.lrsoft.primalarcane.gui;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.block.tileentity.TileEntityManaFurnace;
import net.lrsoft.primalarcane.block.tileentity.TileEntityRuneBench;
import net.lrsoft.primalarcane.container.ContainerRuneBench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiRuneBench extends GuiContainer {
    private static ResourceLocation backgroundTex = new ResourceLocation(PrimalArcane.MODID, "textures/gui/rune_bench.png");
    private TileEntityRuneBench tileEntity;
    private InventoryPlayer playerInventory;
    public GuiRuneBench(InventoryPlayer inventory, TileEntityRuneBench te) {
        super(new ContainerRuneBench(inventory, te));
        tileEntity = te;
        playerInventory = inventory;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // 渲染ui标题
        String info = I18n.format("primalarcane.text.rune_bench");
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
