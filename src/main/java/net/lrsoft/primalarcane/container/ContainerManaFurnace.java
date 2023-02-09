package net.lrsoft.primalarcane.container;

import net.lrsoft.primalarcane.block.tileentity.TileEntityManaFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerManaFurnace extends Container{

    private TileEntityManaFurnace tileEntity;

    public ContainerManaFurnace(InventoryPlayer inventory, TileEntityManaFurnace te) {
        tileEntity = te;

        addSlotToContainer(new SlotWithFilter(te, 0, 51, 43));
        addSlotToContainer(new SlotWithFilter(te, 1, 110, 43));

        // 背包
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                addSlotToContainer(new Slot(inventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        // 快捷栏
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tileEntity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index) {
        // 0-1: Contain inventory
        // 2-28: Player inventory
        // 29-38: Hot bar in the player inventory

        int containEndIndex = 1;
        int playerInventoryStartIndex = containEndIndex + 1;
        int playerInventoryEndIndex = playerInventoryStartIndex + 26;

        int hotbarStartIndex = playerInventoryEndIndex + 1;
        int hotbarEndIndex = hotbarStartIndex + 9;

        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();

            if (index >= 0 && index <= containEndIndex) {
                if (!this.mergeItemStack(itemStack1, playerInventoryStartIndex, hotbarEndIndex, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemStack1, itemStack);
            } else if (index >= playerInventoryStartIndex) {
                if (index >= playerInventoryStartIndex && index < hotbarStartIndex) {
                    if (!this.mergeItemStack(itemStack1, hotbarStartIndex, hotbarEndIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= hotbarStartIndex && index < hotbarEndIndex && !this.mergeItemStack(itemStack1,
                        playerInventoryStartIndex, playerInventoryEndIndex, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemStack1, playerInventoryStartIndex, hotbarEndIndex, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(par1EntityPlayer, itemStack1);
        }

        return itemStack;
    }
}
