package net.lrsoft.primalarcane.item;

import net.lrsoft.primalarcane.spell.Spell;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemSpell extends Item {
	protected Spell spell;
	public ItemSpell(Spell spell) {
		this.spell = spell;
	}
	public Spell getSpell() {
		return spell;
	}
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
