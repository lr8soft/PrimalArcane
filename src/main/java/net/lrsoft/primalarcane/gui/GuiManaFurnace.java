package net.lrsoft.primalarcane.gui;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.block.tileentity.TileEntityManaFurnace;
import net.lrsoft.primalarcane.container.ContainerManaFurnace;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiManaFurnace  extends GuiContainer {
    private static ResourceLocation backgroundTex = new ResourceLocation(PrimalArcane.MODID, "textures/gui/mana_furnace.png");
    private TileEntityManaFurnace tileEntity;
    private InventoryPlayer playerInventory;

    public GuiManaFurnace(InventoryPlayer inventory, TileEntityManaFurnace te) {
        super(new ContainerManaFurnace(inventory, te));
        tileEntity = te;
        playerInventory = inventory;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        // 渲染ui标题
        String info = I18n.format("primalarcane.text.mana_furnace");
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


        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        // 渲染进度条
        this.mc.renderEngine.bindTexture(backgroundTex);
        {
            // 这图像就在xsize=176右边开始
            int imageStartX = this.xSize;
            // 上面塞了高icon=26的icon
            int imageStartY = 26;
            // 这破箭头宽22像素高16像素
            int imageWidth = 22, imageHeight = 16;
            int renderWidth = getCookingProgressScale(imageWidth);

            // 把我整无语了还是一点点测的
            int xOffset = 80;
            int yOffset = 42;

            this.drawTexturedModalRect(x + xOffset, y + yOffset, imageStartX, imageStartY, renderWidth, imageHeight);
        }


        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            // 能工作显示蓝色的info图标
            int imageStartX = this.xSize;
            int imageStartY = 0;
            int imageWidth = 22, imageHeight = 21;

            int xOffset = 15;
            int yOffset = 40;

            // 不能工作就显示红色error
            if(!tileEntity.getCanWork())
                imageStartX += imageWidth;

            this.drawTexturedModalRect(x + xOffset, y + yOffset, imageStartX, imageStartY, imageWidth, imageHeight);
        }
    }

    private int getCookingProgressScale(int pixels) {
        return tileEntity.getCookTime() * pixels / tileEntity.getTotalCookTime();
    }
}
