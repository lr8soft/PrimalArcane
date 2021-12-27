package net.lrsoft.primalarcane.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import scala.reflect.api.Trees.NewApi;
@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class BlockManager {
	
	public static void onBlockRecipeInit()
	{

	}
	
	@SubscribeEvent
	public static void onCommonBlockInit(RegistryEvent.Register<Block> event) {
	    /*event.getRegistry().register(niobiumOre);
	    event.getRegistry().register(titaniumOre);
	    event.getRegistry().register(titaniumBlock);
	    
	    event.getRegistry().register(lighterBlock);
	    
	    event.getRegistry().register(geomagneticPedestal);
	    event.getRegistry().register(geomagneticAntenna);
	    
	    event.getRegistry().register(titaniumScaffold);
	    GameRegistry.registerTileEntity(TileEntityLighterBlock.class,
	    		new ResourceLocation(PrimalArcane.MODID, "lighter_block"));*/
	}
	
	@SubscribeEvent
	public static void onCommonBlockItemInit(RegistryEvent.Register<Item> event) {
		/*event.getRegistry().register(new ItemBlock(niobiumOre).setRegistryName(niobiumOre.getRegistryName()));
		event.getRegistry().register(new ItemBlock(titaniumOre).setRegistryName(titaniumOre.getRegistryName()));
		event.getRegistry().register(new ItemBlock(titaniumBlock).setRegistryName(titaniumBlock.getRegistryName()));
		event.getRegistry().register(new ItemBlock(titaniumScaffold).setRegistryName(titaniumScaffold.getRegistryName()));
		
		event.getRegistry().register(new ItemBlock(geomagneticPedestal).setRegistryName(geomagneticPedestal.getRegistryName()));
		event.getRegistry().register(new ItemBlock(geomagneticAntenna).setRegistryName(geomagneticAntenna.getRegistryName()));*/
	}
	
	private static ItemStack getAllTypeStack(ItemStack itemstack)
	{
		return new ItemStack(itemstack.getItem(), 1, OreDictionary.WILDCARD_VALUE);
	}
	
	private static ItemStack getAllTypeStack(Item item)
	{
		return new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE);
	}
	
}
