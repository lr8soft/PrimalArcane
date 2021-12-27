package net.lrsoft.primalarcane.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityShootSpell extends Entity implements IProjectile{
	public static final Predicate<Entity> GUN_TARGETS = Predicates.and(EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>()
    {
        public boolean apply(@Nullable Entity entity)
        {
            return entity.canBeCollidedWith();
        }
    });
	
	protected EntityPlayer shooter;
	protected int ticksInAir;
	protected int maxExistTicks;
	protected float velocity;

	public EntityShootSpell(World world, int maxTicks)
	{
		super(world);
		setSize(0.39F, 0.39F);
		this.maxExistTicks = maxTicks;
		this.ticksInAir = 0;
	}
	
	public EntityShootSpell(World world)
	{
		super(world);
		setSize(0.39F, 0.39F);
		this.maxExistTicks = 250;
		this.ticksInAir = 0;
	}
	
	public void onSpellUpdate()
	{
	}
	
	public void onSpellOnGround(BlockPos pos)
	{
	}
	
	
	public void onSpellHitEntity(Entity entity)
	{
	}
	
	public void onSpellFinish()
	{
	}
	
	public void onUpdate() 
	{
		super.onUpdate();
		
		if(IsSpellFinish())
		{
			onSpellFinish();
			setDead();
			return;
		}
		
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
            this.rotationPitch = (float)(MathHelper.atan2(this.motionY, (double)f) * (180D / Math.PI));
            this.prevRotationYaw = this.rotationYaw;
            this.prevRotationPitch = this.rotationPitch;
        }
        BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        
        if(!world.isRemote) {
            if (iblockstate.getMaterial() != Material.AIR)
            {
                AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);

                if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ)))
                {
                	onSpellOnGround(blockpos);
                	setSpellDead();
                    return;
                }
            }
        }
        

        ++this.ticksInAir;
        Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
        vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
        vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        
        if (raytraceresult != null)
        {
            vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
        }
        
        Entity entity = this.findEntityOnPath(vec3d1, vec3d);

        if (entity != null)
        {
            raytraceresult = new RayTraceResult(entity);
        }
        
        if (raytraceresult != null && !isDead)
        {
            Entity target = raytraceresult.entityHit;
            if (target != null)
            {
            	target.hurtResistantTime = 0;
            	
            	if(shooter != null && target != shooter)
            	{
            		onSpellHitEntity(target);
                	setSpellDead();
                	return;
            	}

            }
        }
        
        if(this.ticksInAir > maxExistTicks)
        {
        	setDead();
        	return;
        }

        onSpellUpdate();
        
        
        //from arrow
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;

        this.setPosition(this.posX, this.posY, this.posZ);
        this.doBlockCollisions();
	}
	
	protected Entity findEntityOnPath(Vec3d start, Vec3d end)
    {
		Entity entity = null;
		List<Entity> list = this.world.getEntitiesInAABBexcluding(this, 
				this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D), GUN_TARGETS);
		double d0 = 0.0D;

		for (int i = 0; i < list.size(); ++i) {
			Entity entity1 = list.get(i);

			if (shooter != null && shooter == entity1) {
				continue;
			}
			AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(0.3D);
			RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);

			if (raytraceresult != null) {
				double d1 = start.squareDistanceTo(raytraceresult.hitVec);

				if (d1 < d0 || d0 == 0.0D) {
					entity = entity1;
					d0 = d1;
				}
			}
		}

        return entity;
    }
	
	public void shoot(float yaw, float pitch, float velocity)
	{
        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float f1 = -MathHelper.sin(pitch * 0.017453292F);
        float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        this.shoot((double)f, (double)f1, (double)f2, velocity);
        
        if(shooter != null) {
            this.motionX += shooter.motionX;
            this.motionY += shooter.motionY;
            this.motionZ += shooter.motionZ;
        }

        this.velocity = velocity;
	}

	
    protected void shoot(double x, double y, double z, float velocity)
    {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double)f;
        y = y / (double)f;
        z = z / (double)f;
        x = x + this.rand.nextGaussian() * 0.007;
        y = y + this.rand.nextGaussian() * 0.007;
        z = z + this.rand.nextGaussian() * 0.007;
        x = x * (double)velocity;
        y = y * (double)velocity;
        z = z * (double)velocity;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
        this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    
	private static final DataParameter<Float> RotationZ = EntityDataManager.<Float>createKey(EntityShootSpell.class, DataSerializers.FLOAT);
	private static DataParameter<Boolean> SpellFinish = EntityDataManager.<Boolean>createKey(EntityShootSpell.class, DataSerializers.BOOLEAN);
    
	@Override
	protected void entityInit() {
		dataManager.register(RotationZ, (float)Math.random() * 360.0f);
		dataManager.register(SpellFinish, false);
	}
	
	
	public float getSpellRotationZ()
	{
		return dataManager.get(RotationZ);
	}
	

	public void setSpellDead() {
		dataManager.set(SpellFinish, true);
	}
	
	public boolean IsSpellFinish()
	{
		return dataManager.get(SpellFinish);
		
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		this.ticksInAir =  compound.getInteger("ticksInAir");
		this.velocity = compound.getFloat("velocity");
		this.maxExistTicks = compound.getInteger("maxExistTicks");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) 
	{
        compound.setInteger("ticksInAir", this.ticksInAir);
        compound.setFloat("velocity", this.velocity);
        compound.setInteger("maxExistTicks", this.maxExistTicks);
	}
	
	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		shoot(x, y, z, velocity);
	}
}
