package net.lrsoft.primalarcane.proxy;
import java.util.Set;

import net.lrsoft.primalarcane.manager.BlockManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
public class CommonProxy 
{
	public void preInit(FMLPreInitializationEvent event) 
	{
		//FluidManager.onFluidInit();
	}

	public void init(FMLInitializationEvent event) 
	{
		BlockManager.onBlockRecipeInit();
	}

	public void postInit(FMLPostInitializationEvent event) {}

}