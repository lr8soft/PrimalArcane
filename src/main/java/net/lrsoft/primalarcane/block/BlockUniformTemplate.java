package net.lrsoft.primalarcane.block;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUniformTemplate extends Block implements ITileEntityProvider{
	private Class<? extends TileEntity> clazz;
	private int guiId;
	private boolean needItemBlock = true;
	public BlockUniformTemplate(Material materialIn, String blockName, Class<? extends TileEntity> clazz) {
		this(materialIn, blockName, clazz, -1);
	}
	
	public BlockUniformTemplate(Material materialIn, String blockName, Class<? extends TileEntity> clazz, int gui) {
		super(materialIn);
		setUnlocalizedName("primalarcane.block." + blockName);
		setRegistryName(PrimalArcane.MODID, blockName);
		setCreativeTab(PrimalArcane.CREATIVE_TAB);
		this.clazz = clazz;
		this.guiId = gui;
	}

	@Override
	public boolean hasTileEntity() {
		return this.clazz != null;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(!hasTileEntity()) {
			return null;
		}
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Class<? extends TileEntity> getTileEntityClazz(){
		return clazz;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote) {
			return true;
		}
		
		if(guiId < 0) {
			return true;
		}
		
		TileEntity te = worldIn.getTileEntity(pos);
		if(te != null) {
			if(te.getClass() == clazz) {
				playerIn.openGui(PrimalArcane.Instance, guiId, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}

		return true;
	}
	
	public void setNeedItemBlock(boolean value) {
		this.needItemBlock = value;
	}
	
	public boolean getNeedItemBlock() {
		return this.needItemBlock;
	}
}
