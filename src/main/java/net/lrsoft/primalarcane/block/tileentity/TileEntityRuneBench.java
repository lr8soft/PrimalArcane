package net.lrsoft.primalarcane.block.tileentity;

import net.minecraft.item.ItemStack;

public class TileEntityRuneBench extends TileEntityWithContainer{
    public TileEntityRuneBench() {
        // 6个原料 1个输出
        super(7);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        // 原料槽index(0,6]
        if(index > 0 && index <= 6)
            return true;
        return false;
    }

    @Override
    public String getName() { return "rune_bench"; }

    @Override
    public boolean hasCustomName() { return false; }
}
