package net.lrsoft.primalarcane.gui;

import org.lwjgl.opengl.GL11;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.block.tileentity.TileEntityWandWorkBench;
import net.lrsoft.primalarcane.container.ContainerWandWorkbench;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiWandWorkbench extends GuiContainer {
	private static ResourceLocation backgroundTex = new ResourceLocation(PrimalArcane.MODID, "textures/gui/work_bench.png");
	
	private TileEntityWandWorkBench tileEntity;
	private InventoryPlayer playerInventory;
	public GuiWandWorkbench(InventoryPlayer inventory, TileEntityWandWorkBench te) {
		super(new ContainerWandWorkbench(inventory, te));
		this.doesGuiPauseGame();
		tileEntity = te;
		playerInventory = inventory;

	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.fontRenderer.drawString(I18n.format(PrimalArcane.MODID + ".text.wand_workbench"), 55, 6, Integer.parseInt("FFFFFF", 16));

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
