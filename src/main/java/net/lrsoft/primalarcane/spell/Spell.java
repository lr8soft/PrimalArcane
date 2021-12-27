package net.lrsoft.primalarcane.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface Spell {
	public void onSpell(World worldIn, EntityPlayer playerIn, ItemStack stack);
	public String getSpellName();
	public float getSpellCost();
	public int getSpellInterval();
}
