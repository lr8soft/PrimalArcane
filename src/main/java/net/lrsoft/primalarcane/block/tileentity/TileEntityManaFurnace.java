package net.lrsoft.primalarcane.block.tileentity;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.mana.ManaHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.world.chunk.Chunk;

public class TileEntityManaFurnace extends TileEntityWithContainer implements ITickable {
    protected final float consumeMana = 10.0f;
    private int cookTime = 0;
    private int totalCookTime = 100;

    public TileEntityManaFurnace() {
        super(2);
    }

    @Override
    public void update() {
        if(this.world.isRemote)
            return;

        ItemStack sourceSlot = getStackInSlot(0);
        ItemStack targetSlot = getStackInSlot(1);
        // 检测输入是否为空，输出满了没有
        if(sourceSlot.isEmpty()) return;
        if(targetSlot.getCount() == targetSlot.getMaxStackSize()) return;

        // 检测输入物品是否有输出
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(sourceSlot);
        if(result.isEmpty()) return;
        // 如果已有输出物品 检测是否与结果一致
        if(!targetSlot.isEmpty()) {
            if(targetSlot.getItem() != result.getItem())
                return;
        }

        Chunk chunk = this.world.getChunkFromBlockCoords(this.getPos());
        if(ManaHelper.consumeMana(chunk, consumeMana)) {
            this.cookTime += 1;
            // 煮好了
            if(this.cookTime == this.totalCookTime) {
                sourceSlot.shrink(1);
                if(targetSlot.isEmpty()) {
                    setInventorySlotContents(1, result);
                }else{
                    targetSlot.grow(1);
                }
            }
            this.markDirty();
        }

    }

    @Override
    public String getName() { return PrimalArcane.MODID + ".mana_furnace"; }

    @Override
    public boolean hasCustomName() { return false; }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if(stack.isEmpty())
            return false;

        Item item = stack.getItem();
        // slot 0 原料
        // slot 1 成品
        if(index == 0) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);
            return !result.isEmpty();
        }
        return false;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        super.setInventorySlotContents(index, stack);
        if(index == 0) {
            this.cookTime = 0;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.cookTime = compound.getInteger("cookTime");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("cookTime", this.cookTime);
        return super.writeToNBT(compound);
    }

    @Override
    public int getField(int id) { return 0; }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() { return 0; }
}
