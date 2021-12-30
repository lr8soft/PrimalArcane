package net.lrsoft.primalarcane.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotWithFilter extends Slot {
	private IInventory inventory;
	private int slotIndex;
	public SlotWithFilter(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		inventory = inventoryIn;
		slotIndex = index;
	}
	
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return inventory.isItemValidForSlot(slotIndex, stack);
	}

}
