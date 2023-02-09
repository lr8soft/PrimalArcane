package net.lrsoft.primalarcane.block.tileentity;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.item.ItemSpell;
import net.lrsoft.primalarcane.item.ItemWand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityWandWorkBench extends TileEntityWithContainer{
	public TileEntityWandWorkBench() {
		super(3);
	}

	public void updateWand() {
		if(this.world.isRemote)
			return;

		ItemStack wandStack = getStackInSlot(0);
		ItemStack spellStack0 = getStackInSlot(1);
		ItemStack spellStack1 = getStackInSlot(2);
		
		if(wandStack.isEmpty()) 
			return;

		if(!(wandStack.getItem() instanceof ItemWand))
			return;
		
		ItemWand wand = (ItemWand)wandStack.getItem();
		ItemSpell spell0 = getSpellItem(spellStack0);
		ItemSpell spell1 = getSpellItem(spellStack1);

		// write spell the wand
		if(spell0 != null) {
			wand.setSlotSpell(wandStack, 0, spell0.getSpell());
		}

		if(spell1 != null) {
			wand.setSlotSpell(wandStack, 1, spell1.getSpell());
		}
	}
	
	private static ItemSpell getSpellItem(ItemStack stack) {
		if(stack.isEmpty()) {
			return null;
		}
		
		Item item = stack.getItem();
		if(item instanceof ItemSpell) {
			return (ItemSpell)item;
		}
		
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(stack.isEmpty())
			return false;
		
		Item item = stack.getItem();
		if(index == 0) {
			if(item instanceof ItemWand) {
				return true;
			}
		}
			
		if(index == 1 || index == 2) {
			if(item instanceof ItemSpell) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return PrimalArcane.MODID + ".wand_workbench";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}
}
