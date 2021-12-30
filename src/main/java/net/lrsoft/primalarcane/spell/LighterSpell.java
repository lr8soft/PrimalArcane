package net.lrsoft.primalarcane.spell;

import net.lrsoft.primalarcane.mana.ManaHelper.ManaType;
import net.lrsoft.primalarcane.manager.BlockManager;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LighterSpell implements Spell {

	@Override
	public void onSpell(World worldIn, EntityPlayer playerIn, ItemStack stack) {
		/*Vec3d lookat = playerIn.getLookVec();
		
		float blockReachDistance = 5.0f;
		
        Vec3d vec3d = playerIn.getPositionEyes(1.0f);
        Vec3d vec3d1 = playerIn.getLook(1.0f);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        
        RayTraceResult result = worldIn.rayTraceBlocks(vec3d, vec3d2, false, false, true);
        if(result == null)
        	return;
        
        Vec3d target = result.hitVec;
        
        BlockPos blockPos = new BlockPos(target);
        blockPos = blockPos.add(0, 1, 0);
        
        IBlockState state = worldIn.getBlockState(blockPos);
        
        ItemBlock.setTileEntityNBT(worldIn, playerIn, blockPos, stack);
        BlockManager.lighterBlock.onBlockPlacedBy(worldIn, blockPos, state, playerIn, stack);
        
        if (playerIn instanceof EntityPlayerMP)
        	CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)playerIn, blockPos, stack);*/
	}

	@Override
	public String getSpellName() {
		return "spell_lighter";
	}

	@Override
	public ManaType getConsumeManaType() {
		return ManaType.POSITIVE;
	}

	@Override
	public float getSpellCost() {
		return 50.0f;
	}

	@Override
	public int getSpellInterval() {
		return 100;
	}

}
