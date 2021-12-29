package net.lrsoft.primalarcane.spell;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.mana.ManaHelper.ManaType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LightningSpell implements Spell {

	@Override
	public void onSpell(World worldIn, EntityPlayer playerIn, ItemStack stack) {
		// TODO Auto-generated method stub
		//worldIn.li
		Vec3d lookat = playerIn.getLookVec();
		PrimalArcane.logger.info("lookat:" + lookat);
	}

	@Override
	public String getSpellName() {
		// TODO Auto-generated method stub
		return "spell_lightning";
	}

	@Override
	public ManaType getConsumeManaType() {
		return ManaType.NEGATIVE;
	}

	@Override
	public float getSpellCost() {
		// TODO Auto-generated method stub
		return 30;
	}

	@Override
	public int getSpellInterval() {
		// TODO Auto-generated method stub
		return 200;
	}

}
