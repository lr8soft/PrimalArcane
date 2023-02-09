package net.lrsoft.primalarcane.block.tileentity;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

public class TileEntityManaFurnace extends TileEntityWithContainer implements ITickable {
    public TileEntityManaFurnace() {
        super(2);
    }

    @Override
    public void update() {

    }

    @Override
    public String getName() {
        return PrimalArcane.MODID + ".mana_furnace";
    }

    @Override
    public boolean hasCustomName() { return false; }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public int getField(int id) { return 0; }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() { return 0; }
}
