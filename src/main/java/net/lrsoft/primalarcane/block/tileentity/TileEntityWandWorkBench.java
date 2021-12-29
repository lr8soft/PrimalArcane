package net.lrsoft.primalarcane.block.tileentity;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import scala.reflect.internal.Trees.This;

public class TileEntityWandWorkBench extends TileEntity implements ITickable, IInventory{

    protected NonNullList<ItemStack> inventorySlotItemStack = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
	
	@Override
	public void update() {
		if(!this.world.isRemote)
			return;
		
		//PrimalArcane.logger.info("te update:" + this.getPos());
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
		return index < 3;
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
		// TODO Auto-generated method stub
		super.readFromNBT(compound);
        this.inventorySlotItemStack =
                NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        ItemStackHelper.saveAllItems(compound, this.inventorySlotItemStack);
		return super.writeToNBT(compound);
		
	}
	
}
