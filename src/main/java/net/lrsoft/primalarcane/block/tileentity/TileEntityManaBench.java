package net.lrsoft.primalarcane.block.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

public class TileEntityManaBench extends TileEntityMachine{
    public TileEntityManaBench() {
        // 6个原料 1个输出
        super(7);
    }
    
    @Override
    public void update() {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        // 原料槽index(0,6]
        if(index > 0 && index <= 6)
            return true;
        return false;
    }

    @Override
    public String getName() { return "mana_bench"; }

    @Override
    public boolean hasCustomName() { return false; }

    @Override
    public int getCurrentProgress() {
        return 0;
    }

    @Override
    public int getMaxProgress() {
        return 100;
    }

}
