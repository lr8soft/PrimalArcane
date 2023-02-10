package net.lrsoft.primalarcane.block.tileentity;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

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

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.getPos(), 1, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(super.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    public void notifyUpdateToClient() {
        IBlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
        world.scheduleBlockUpdate(pos, this.getBlockType(),0,0);
        this.markDirty();
    }

    @Override
    public int getField(int id) { return 0; }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() { return 0; }
}
