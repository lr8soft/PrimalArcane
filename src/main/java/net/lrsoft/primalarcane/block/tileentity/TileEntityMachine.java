package net.lrsoft.primalarcane.block.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public abstract class TileEntityMachine extends TileEntityWithContainer implements ITickable {
    private boolean canWork = false;
    public TileEntityMachine(int containerSize) {
        super(containerSize);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.canWork = compound.getBoolean("canWork");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("canWork", this.canWork);
        return super.writeToNBT(compound);
    }

    protected void setCanWork(boolean value) { this.canWork = value; }
    public boolean getCanWork() { return this.canWork; }
    public abstract int getCurrentProgress();
    public abstract int getMaxProgress();
}
