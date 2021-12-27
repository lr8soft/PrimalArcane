package net.lrsoft.primalarcane.entity;

import java.util.Random;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFireball extends EntityShootSpell {
	private float power;
	public EntityFireball(World world, EntityPlayer player, float power, int maxTick) {
		super(world, maxTick);
		this.shooter = player;
		this.power = power;
		setPosition(player.posX, player.posY + (double)shooter.getEyeHeight() - 0.55, player.posZ);
	}
	
	public EntityFireball(World world)
	{
		super(world, 250);
		this.power = 3;
	}
	
	private int getRandomFromRange(int max, int min)
	{
		return this.rand.nextInt(max - min) + min;
	}
	
	@Override
	public void onSpellUpdate() {
		double var2 = rand.nextGaussian() * 0.02D;
		double var4 = rand.nextGaussian() * 0.02D;
		double var6 = rand.nextGaussian() * 0.02D;
		double var8 = 4.0D;
		this.world.spawnParticle(EnumParticleTypes.FLAME,
				this.posX + (double) (rand.nextFloat() * this.width * 2.0F) - (double) this.width - var2 * var8,
				this.posY + (double) (rand.nextFloat() * this.height) - var4 * var8,
				this.posZ + (double) (rand.nextFloat() * this.width * 2.0F) - (double) this.width - var6 * var8, var2,
				var4, var6);
	}
	
	@Override
	public void onSpellHitEntity(Entity entity) {
		if(shooter == null)
			return;
		
		entity.attackEntityFrom(DamageSource.causePlayerDamage(shooter), power);
		entity.setFire(Math.round(power));
	}
	
	@Override
	public void onSpellOnGround(BlockPos pos) {
		
		int yOffset = 0;
		BlockPos burnPos = pos.add(0, 1, 0);
		IBlockState state = this.world.getBlockState(burnPos);
		Block block = state.getBlock();
		if(block == Blocks.AIR || block.isWood(world, burnPos))
			this.world.setBlockState(burnPos, Blocks.FIRE.getDefaultState(), 11);
	}
	
	@Override
	public void onSpellFinish() {
		for(int var1 = 0; var1 < 6; ++var1)
		{
			double var2 = rand.nextGaussian() * 0.06D;
			double var4 = rand.nextGaussian() * 0.06D;
			double var6 = rand.nextGaussian() * 0.06D;
			double var8 = 10.0D;
			this.world.spawnParticle(EnumParticleTypes.FLAME,
					this.posX,
					this.posY,
					this.posZ,
					var2 * 4.0d,
					var4 * 4.0d,
					var6 * 4.0d);
			
		}
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.power = compound.getFloat("power");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) 
	{
		super.writeEntityToNBT(compound);
        compound.setFloat("power", this.power);
	}

}
