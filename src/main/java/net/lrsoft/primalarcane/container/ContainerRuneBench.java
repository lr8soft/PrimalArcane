package net.lrsoft.primalarcane.container;

import net.lrsoft.primalarcane.block.tileentity.TileEntityRuneBench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerRuneBench extends Container {
    private TileEntityRuneBench tileEntity;

    public ContainerRuneBench(InventoryPlayer inventory, TileEntityRuneBench te) {
        tileEntity = te;

        //addSlotToContainer(new SlotWithFilter(te, 0, 54, 41));
        //addSlotToContainer(new SlotWithFilter(te, 1, 113, 41));

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
