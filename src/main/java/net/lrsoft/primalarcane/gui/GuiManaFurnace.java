package net.lrsoft.primalarcane.gui;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.block.tileentity.TileEntityManaFurnace;
import net.lrsoft.primalarcane.container.ContainerManaFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiManaFurnace  extends GuiContainer {
    private static ResourceLocation backgroundTex = new ResourceLocation(PrimalArcane.MODID, "textures/gui/mana_furnace.png");
    private static ResourceLocation infoTex = new ResourceLocation(PrimalArcane.MODID, "textures/icons/info.png");
    private static ResourceLocation errorTex = new ResourceLocation(PrimalArcane.MODID, "textures/icons/error.png");
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

        String info = I18n.format("primalarcane.text.mana_furnace");
        this.fontRenderer.drawString(info, 110 - fontRenderer.getStringWidth(info), 6, Integer.parseInt("000000", 16));

        String progress = I18n.format("primalarcane.text.progress");
        progress += " " + tileEntity.getCookTime() + "/" + tileEntity.getTotalCookTime();
        this.fontRenderer.drawString(progress, 70 - fontRenderer.getStringWidth(info), 66, Integer.parseInt("000000", 16));

        if(tileEntity.getCanWork()) {
            this.mc.renderEngine.bindTexture(infoTex);
        }else{
            this.mc.renderEngine.bindTexture(errorTex);
        }

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(backgroundTex);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
    }
}
