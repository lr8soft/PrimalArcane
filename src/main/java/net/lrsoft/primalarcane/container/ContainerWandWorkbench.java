package net.lrsoft.primalarcane.container;

import net.lrsoft.primalarcane.block.tileentity.TileEntityWandWorkBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerWandWorkbench extends Container {

	public ContainerWandWorkbench(InventoryPlayer inventory, TileEntityWandWorkBench te) {
		// backpack
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column)
            {
                addSlotToContainer(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
        
        // shortcut bar
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return true;
	}
}
