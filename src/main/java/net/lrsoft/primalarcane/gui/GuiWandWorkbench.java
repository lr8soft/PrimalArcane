package net.lrsoft.primalarcane.gui;

import net.lrsoft.primalarcane.block.tileentity.TileEntityWandWorkBench;
import net.lrsoft.primalarcane.container.ContainerWandWorkbench;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class GuiWandWorkbench extends GuiContainer {
	private TileEntityWandWorkBench tileEntity;
	private InventoryPlayer playerInventory;
	public GuiWandWorkbench(InventoryPlayer inventory, TileEntityWandWorkBench te) {
		super(new ContainerWandWorkbench(inventory, te));
		tileEntity = te;
		playerInventory = inventory;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// TODO Auto-generated method stub

	}

}
