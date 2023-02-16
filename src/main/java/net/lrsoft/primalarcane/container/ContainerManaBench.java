package net.lrsoft.primalarcane.container;

import net.lrsoft.primalarcane.block.tileentity.TileEntityManaBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerManaBench extends Container {
    private TileEntityManaBench tileEntity;

    public ContainerManaBench(InventoryPlayer inventory, TileEntityManaBench te) {
        tileEntity = te;

        int slotSize = 21;

        int startX = 74, startY = 30;
        // 输出槽
        addSlotToContainer(new SlotWithFilter(te, 0, 134, 41));
        // 第一行材料
        addSlotToContainer(new SlotWithFilter(te, 3, startX, startY));
        addSlotToContainer(new SlotWithFilter(te, 2, startX - slotSize, startY));
        addSlotToContainer(new SlotWithFilter(te, 1, startX - slotSize - slotSize, startY));
        // 第二行材料
        int offsetY = 1;
        addSlotToContainer(new SlotWithFilter(te, 6, startX, startY + slotSize + offsetY));
        addSlotToContainer(new SlotWithFilter(te, 5, startX - slotSize, startY + slotSize + offsetY));
        addSlotToContainer(new SlotWithFilter(te, 4, startX - slotSize - slotSize, startY + slotSize + offsetY));

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
}
