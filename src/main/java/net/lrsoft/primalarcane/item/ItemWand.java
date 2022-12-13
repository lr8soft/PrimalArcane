package net.lrsoft.primalarcane.item;

import java.util.List;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.mana.ManaHelper;
import net.lrsoft.primalarcane.spell.Spell;
import net.lrsoft.primalarcane.spell.SpellManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ItemWand extends Item {
	public ItemWand(String wandName) {
		setUnlocalizedName("primalarcane.wand." + wandName);
		setRegistryName(PrimalArcane.MODID, wandName);
		setCreativeTab(PrimalArcane.CREATIVE_TAB);
		setMaxStackSize(1);
	}

	public void doWandSpell(World worldIn, EntityPlayer player, ItemStack stack, int slot) {
		// 仅限服务器端
		if (worldIn.isRemote)
			return;
		
		BlockPos pos = new BlockPos(player);
		Chunk chunk = worldIn.getChunkFromBlockCoords(pos);
		
		Spell spell = getSlotSpell(stack, slot);
		if (spell != null) {
			if(ManaHelper.canConsumeMana(worldIn, chunk, spell.getSpellCost())){
				if(spell.onSpell(worldIn, player, stack)) {
					ManaHelper.consumeMana(worldIn, chunk, spell.getSpellCost());
				}
			}
		}
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		// 客户端检测切换法术动作
		if(worldIn.isRemote) {
			Item currentItem = stack.getItem();
			if(!isSelected) return;
			if(!(entityIn instanceof EntityPlayer)) return;

			if(currentItem instanceof ItemWand) {

			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack wand = playerIn.getHeldItem(handIn);
		long lastRightClick = getLastClick(wand);
		long currentTime = System.currentTimeMillis();
		int currentSlot = getCurrentSpellSlot(wand);
		if (currentTime - lastRightClick > getSpellInterval(wand, currentSlot)) {
			doWandSpell(worldIn, playerIn, wand, currentSlot);
			setLastClick(wand, lastRightClick);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, wand);
		}

		return new ActionResult<ItemStack>(EnumActionResult.FAIL, wand);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		int maxSlot = getMaxSpellSlot();
		int currentSlot = getCurrentSpellSlot(stack);
		Spell currentSpell = getSlotSpell(stack, currentSlot);
		String currentStr = I18n.format("primalarcane.text.currentspell") + ": ";
		if(currentSpell != null) {
			currentStr += I18n.format("item.primalarcane.spell." + currentSpell.getSpellName() + ".name");
		}else {
			currentStr += I18n.format("primalarcane.text.none");
		}
		tooltip.add(currentStr);

		for(int slot = 0; slot != maxSlot; slot++) {
			String slotStr = I18n.format("primalarcane.text.spell") + ": ";;
			Spell spell = getSlotSpell(stack, slot);
			if(spell != null) {
				slotStr += I18n.format("item.primalarcane.spell." + spell.getSpellName() + ".name");
			}else {
				slotStr += I18n.format("primalarcane.text.none");
			}
			tooltip.add(slotStr);
		}
	}
	
    /**
     * Return the spell of the slot
     * @param stack
     * @param slot
     * @return The spell on the slot
     */
	public Spell getSlotSpell(ItemStack stack, int slot) {
		try {
			String spellName = stack.getTagCompound().getString("WandSlot" + slot);
			return SpellManager.getSpell(spellName);
		} catch (Exception expt) {
			return null;
		}
	}

	public void setSlotSpell(ItemStack stack, int slot, Spell spell) {
		if(stack.isEmpty())
			return;
		
		NBTTagCompound compound = stack.getTagCompound();
		if(compound == null)
			compound = new NBTTagCompound();

		compound.setString("WandSlot" + slot, spell.getSpellName());
		stack.setTagCompound(compound);
	}

	/**
	 * 
	 * @param stack
	 * @param slot
	 * @return Spell working interval
	 */
	protected int getSpellInterval(ItemStack stack, int slot) {
		Spell spell = getSlotSpell(stack, slot);
		if (spell != null) {
			return spell.getSpellInterval();
		}
		return 0;
	}
	public int getMaxSpellSlot() {
		return 2;
	}
	public static void setCurrentSpellSlot(ItemStack stack, int value) {
		try {
			stack.getItem().getNBTShareTag(stack).setInteger("CurrentSlot", value);
		} catch (Exception expt) {}
	}

	public static int getCurrentSpellSlot(ItemStack stack) {
		int value = 0;
		try {
			value = stack.getItem().getNBTShareTag(stack).getInteger("CurrentSlot");
		} catch (Exception expt) {}
		return value;
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
