package net.lrsoft.primalarcane.block.tileentity;

import net.minecraft.item.ItemStack;

public class TileEntityRuneBench extends TileEntityWithContainer{
    public TileEntityRuneBench() {
        super(9);
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
