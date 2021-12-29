package net.lrsoft.primalarcane.block.tileentity;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import scala.reflect.internal.Trees.This;

public class TileEntityWandWorkBench extends TileEntity implements ITickable{

	@Override
	public void update() {
		if(!this.world.isRemote)
			return;
		
		//PrimalArcane.logger.info("te update:" + this.getPos());
	}
	 
	
}
