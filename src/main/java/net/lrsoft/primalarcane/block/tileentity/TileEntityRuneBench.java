package net.lrsoft.primalarcane.block.tileentity;

import net.minecraft.item.ItemStack;

public class TileEntityRuneBench extends TileEntityWithContainer{
    public TileEntityRuneBench() {
        // 8个原料 1个核心原料 1个输出
        super(10);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public String getName() { return "rune_bench"; }

    @Override
    public boolean hasCustomName() { return false; }
}
