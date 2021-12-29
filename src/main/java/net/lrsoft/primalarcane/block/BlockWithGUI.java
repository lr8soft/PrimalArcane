package net.lrsoft.primalarcane.block;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockWithGUI extends Block {

	public BlockWithGUI(Material materialIn, String blockName) {
		super(materialIn);
		setUnlocalizedName("primalarcane.block." + blockName);
		setRegistryName(PrimalArcane.MODID, blockName);
		setCreativeTab(PrimalArcane.CREATIVE_TAB);
	}


}
