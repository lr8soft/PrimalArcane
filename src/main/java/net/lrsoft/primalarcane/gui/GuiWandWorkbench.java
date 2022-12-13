package net.lrsoft.primalarcane.gui;

import net.lrsoft.primalarcane.network.NetworkHandler;
import net.lrsoft.primalarcane.network.Message.*;
import org.lwjgl.opengl.GL11;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.block.tileentity.TileEntityWandWorkBench;
import net.lrsoft.primalarcane.container.ContainerWandWorkbench;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiWandWorkbench extends GuiContainer {
	private static ResourceLocation backgroundTex = new ResourceLocation(PrimalArcane.MODID, "textures/gui/work_bench.png");
	private TileEntityWandWorkBench tileEntity;
	private InventoryPlayer playerInventory;
	private GuiButton submitButton;

	private final int SUBMIT_BUTTON_ID = 0;

	public GuiWandWorkbench(InventoryPlayer inventory, TileEntityWandWorkBench te) {
		super(new ContainerWandWorkbench(inventory, te));
		tileEntity = te;
		playerInventory = inventory;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		this.fontRenderer.drawString(I18n.format("primalarcane.text.wand_workbench"), 60, 6, Integer.parseInt("000000", 16));
	}

	@Override
	public void initGui() {
		super.initGui();
		int baseX =  (this.width - this.xSize) / 2;
		int baseY = (this.height - this.ySize) / 2;
		// 确认按钮
		this.addButton(new GuiButton(0,baseX + 130, baseY + 43, 20, 20, I18n.format("primalarcane.text.submit")));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(backgroundTex);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(!button.enabled) return;

		// 点击确认按钮
		if(button.id == SUBMIT_BUTTON_ID) {
			NetworkHandler.INSTANCE.sendMessageToServer(new MessageGuiButton(button.id));
		}
	}
}
