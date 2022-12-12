package net.lrsoft.primalarcane.block.tileentity;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.item.ItemSpell;
import net.lrsoft.primalarcane.item.ItemWand;
import net.lrsoft.primalarcane.manager.ItemManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import scala.reflect.internal.Trees.This;

public class TileEntityWandWorkBench extends TileEntity implements ITickable, IInventory{
	public final int NEED_UPDATE_WAND_ID = 0;

    protected NonNullList<ItemStack> inventorySlotItemStack = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);

	@Override
	public void update() {}

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
			wand.setWandSpell(wandStack, 0, spell0.getSpell());
		}

		if(spell1 != null) {
			wand.setWandSpell(wandStack, 1, spell1.getSpell());
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
	public String getName() {
		return PrimalArcane.MODID + ".wand_workbench";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : this.inventorySlotItemStack) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
        return inventorySlotItemStack.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack = ItemStackHelper.getAndSplit(inventorySlotItemStack, index, count);

        if (!itemstack.isEmpty()) {
            this.markDirty();
        }

        return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(inventorySlotItemStack, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        inventorySlotItemStack.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
        this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		this.markDirty();
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		this.markDirty();
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
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
        inventorySlotItemStack.clear();
	}
	 
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
        this.inventorySlotItemStack = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventorySlotItemStack);
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        ItemStackHelper.saveAllItems(compound, this.inventorySlotItemStack);
		return super.writeToNBT(compound);
	}

}
