package net.lrsoft.primalarcane.spell;

import net.lrsoft.primalarcane.manager.BlockManager;
import net.lrsoft.primalarcane.util.BlockUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LighterSpell implements Spell {

	@Override
	public boolean onSpell(World worldIn, EntityPlayer playerIn, ItemStack stack) {
		float blockReachDistance = 5.0f;
		
        Vec3d vec3d = playerIn.getPositionEyes(1.0f);
        Vec3d vec3d1 = playerIn.getLook(1.0f);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        
        RayTraceResult result = worldIn.rayTraceBlocks(vec3d, vec3d2, false, false, true);
        if(result == null)
        	return false;

		BlockPos blockPos = new BlockPos(result.hitVec);
		blockPos = BlockUtils.getPlaceablePos(blockPos, playerIn);

        IBlockState state = worldIn.getBlockState(blockPos);
		if(state != Blocks.AIR.getDefaultState())
			return false;

		worldIn.setBlockState(blockPos, BlockManager.lighterBlock.getDefaultState());
		return true;
	}

	@Override
	public String getSpellName() {
		return "spell_lighter";
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
