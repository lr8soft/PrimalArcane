package net.lrsoft.primalarcane.spell;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.entity.EntityFireball;
import net.lrsoft.primalarcane.mana.ManaHelper.ManaType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class FireballSpell implements Spell {

	@Override
	public boolean onSpell(World worldIn, EntityPlayer playerIn, ItemStack stack) {
		EntityFireball entity = new EntityFireball(worldIn, playerIn, 6f, 250);
		entity.shoot(playerIn.rotationYaw, playerIn.rotationPitch, 2.0f);
		worldIn.spawnEntity(entity);
		
		worldIn.playSound((EntityPlayer)null, playerIn.posX , playerIn.posY, playerIn.posZ, 
				SoundEvents.ENTITY_ENDERDRAGON_SHOOT, playerIn.getSoundCategory(), 0.8f, 0.55F);
		
		return true;
	}

	@Override
	public float getSpellCost() {
		return 25.0f;
	}

	@Override
	public int getSpellInterval() {
		// 120ms
		return 120;
	}

	@Override
	public String getSpellName() {
		return "spell_fireball";
	}

}
