package net.lrsoft.primalarcane.item;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.entity.EntityFireball;
import net.lrsoft.primalarcane.entity.EntityShootSpell;
import net.lrsoft.primalarcane.spell.Spell;
import net.lrsoft.primalarcane.spell.SpellManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemWand extends Item {
	public ItemWand(String wandName) {
		setUnlocalizedName("primalarcane.wand." + wandName);
		setRegistryName(PrimalArcane.MODID, wandName);
		setCreativeTab(PrimalArcane.CREATIVE_TAB);
		setMaxStackSize(1);
	}

	public enum WandSlot {
		LEFT, RIGHT
	}

	public void doWandSpell(World worldIn, EntityPlayer player, ItemStack stack, WandSlot slot) {
		Spell spell = getWandSpell(stack, slot);
		PrimalArcane.logger.info(spell == null);
		if(spell != null) {
			PrimalArcane.logger.info(spell.getSpellName());
			spell.onSpell(worldIn, player, stack);
		}
	}


	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack wand) {
		long lastRightClick = getLastClick(wand);
		long currentTime = System.currentTimeMillis();

		if (currentTime - lastRightClick > getSpellInterval(wand, WandSlot.RIGHT)) {
			if (entityLiving instanceof EntityPlayer) {
				doWandSpell(entityLiving.world, (EntityPlayer) entityLiving, wand, WandSlot.LEFT);
				setLastClick(wand, lastRightClick);
			}
		}
		
		return super.onEntitySwing(entityLiving, wand);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack wand = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastClick(wand);
		long currentTime = System.currentTimeMillis();

		if (currentTime - lastRightClick > getSpellInterval(wand, WandSlot.RIGHT)) {
			doWandSpell(worldIn, playerIn, wand, WandSlot.RIGHT);
			setLastClick(wand, lastRightClick);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, wand);
		}

		return new ActionResult<ItemStack>(EnumActionResult.FAIL, wand);
	}
	
    /**
     * Return the spell of the slot
     * @param stack
     * @param slot
     * @return The spell on the slot
     */
	public Spell getWandSpell(ItemStack stack, WandSlot slot) {
		try {
			String spellName = stack.getItem().getNBTShareTag(stack).getString("WandSlot" + slot.toString());
			return SpellManager.getSpell(spellName);
		} catch (Exception expt) {
			return SpellManager.fireball;
		}
	}

	
	/**
	 * 
	 * @param stack
	 * @param slot
	 * @return Spell working interval
	 */
	protected int getSpellInterval(ItemStack stack, WandSlot slot) {
		Spell spell = getWandSpell(stack, slot);
		if (spell != null) {
			return spell.getSpellInterval();
		}
		return 0;
	}


	protected void setLastClick(ItemStack stack, long value) {
		try {
			stack.getItem().getNBTShareTag(stack).setLong("LastRightClick", value);
		} catch (Exception expt) {
		}
	}

	protected long getLastClick(ItemStack stack) {
		long value = 0;
		try {
			value = stack.getItem().getNBTShareTag(stack).getLong("LastRightClick");
		} catch (Exception expt) {
		}
		return value;
	}
}
