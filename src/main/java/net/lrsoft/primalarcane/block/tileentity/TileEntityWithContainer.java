package net.lrsoft.primalarcane.block.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public abstract class TileEntityWithContainer extends TileEntity implements IInventory {
    private int containerSize = 0;
    protected NonNullList<ItemStack> inventorySlotItemStack;
    public TileEntityWithContainer(int containerSize)
    {
        this.containerSize = containerSize;
        this.inventorySlotItemStack = NonNullList.<ItemStack>withSize(this.containerSize, ItemStack.EMPTY);
    }

    @Override
    public int getSizeInventory() {return containerSize;}

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
    public ItemStack getStackInSlot(int index) {
        return inventorySlotItemStack.get(index);
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
    public boolean isUsableByPlayer(EntityPlayer player) { return true; }

    @Override
    public void openInventory(EntityPlayer player) {
        this.markDirty();
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        this.markDirty();
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
