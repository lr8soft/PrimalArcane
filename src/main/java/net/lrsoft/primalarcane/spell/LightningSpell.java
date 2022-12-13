package net.lrsoft.primalarcane.spell;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.mana.ManaHelper.ManaType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LightningSpell implements Spell {

	@Override
	public boolean onSpell(World worldIn, EntityPlayer playerIn, ItemStack stack) {
		Vec3d lookat = playerIn.getLookVec();
		
		float blockReachDistance = 15.0f;
		
        Vec3d vec3d = playerIn.getPositionEyes(1.0f);
        Vec3d vec3d1 = playerIn.getLook(1.0f);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
		
        RayTraceResult result = worldIn.rayTraceBlocks(vec3d, vec3d2, false, false, true);
        if(result == null)
        	return false;
        
        Vec3d target = result.hitVec;
        if(result.entityHit != null) {
        	result.entityHit.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), 25.0f);
        	target = new Vec3d(result.entityHit.posX, result.entityHit.posY, result.entityHit.posZ);
        }
        
        worldIn.addWeatherEffect(new EntityLightningBolt(worldIn, target.x, target.y - 0.5f, target.z, false));
        
        return true;
	}

	@Override
	public String getSpellName() {
		return "spell_lightning";
	}

	@Override
	public float getSpellCost() {
		// TODO Auto-generated method stub
		return 200;
	}

	@Override
	public int getSpellInterval() {
		// TODO Auto-generated method stub
		return 200;
	}

}
