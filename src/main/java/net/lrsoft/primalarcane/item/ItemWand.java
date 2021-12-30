package net.lrsoft.primalarcane.item;

import java.util.List;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.entity.EntityFireball;
import net.lrsoft.primalarcane.entity.EntityShootSpell;
import net.lrsoft.primalarcane.mana.ManaHelper;
import net.lrsoft.primalarcane.spell.Spell;
import net.lrsoft.primalarcane.spell.SpellManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
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

	public enum WandSlot {
		LEFT, RIGHT
	}

	public void doWandSpell(World worldIn, EntityPlayer player, ItemStack stack, WandSlot slot) {
		if (worldIn.isRemote)
			return;
		
		BlockPos pos = new BlockPos(player);
		Chunk chunk = worldIn.getChunkFromBlockCoords(pos);
		
		Spell spell = getWandSpell(stack, slot);
		if (spell != null) {
			if(ManaHelper.consumeMana(worldIn, chunk, spell.getConsumeManaType(), spell.getSpellCost())){
				spell.onSpell(worldIn, player, stack);
			}
		}
	}


	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack wand) {
		long lastRightClick = getLastClick(wand);
		long currentTime = System.currentTimeMillis();

		if (currentTime - lastRightClick > getSpellInterval(wand, WandSlot.LEFT)) {
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
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		String strSpellLeft = I18n.format("primalarcane.text.spell") + ": ";
		Spell spell = getWandSpell(stack, WandSlot.LEFT);
		if(spell != null) {
			strSpellLeft += I18n.format("item.primalarcane.spell." + spell.getSpellName() + ".name");
		}else {
			strSpellLeft += I18n.format("primalarcane.text.none");
		}
		
		String strSpellRight = I18n.format("primalarcane.text.spell") + ": ";
		spell = getWandSpell(stack, WandSlot.RIGHT);
		if(spell != null) {
			strSpellRight += I18n.format("item.primalarcane.spell." + spell.getSpellName() + ".name");
		}else {
			strSpellRight += I18n.format("primalarcane.text.none");
		}
		
		tooltip.add(strSpellLeft);
		tooltip.add(strSpellRight);
	}
	
    /**
     * Return the spell of the slot
     * @param stack
     * @param slot
     * @return The spell on the slot
     */
	public Spell getWandSpell(ItemStack stack, WandSlot slot) {
		try {
			String spellName = stack.getTagCompound().getString("WandSlot" + slot.toString());
			return SpellManager.getSpell(spellName);
		} catch (Exception expt) {
			return null;
		}
	}
	
	public void setWandSpell(ItemStack stack, WandSlot slot, Spell spell) {
		if(stack.isEmpty())
			return;
		
		NBTTagCompound compound = stack.getTagCompound();
		if(compound == null)
			compound = new NBTTagCompound();

		compound.setString("WandSlot" + slot.toString(), spell.getSpellName());
		stack.setTagCompound(compound);
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
