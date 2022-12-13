package net.lrsoft.primalarcane.manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.block.BlockLighter;
import net.lrsoft.primalarcane.block.BlockUniformTemplate;
import net.lrsoft.primalarcane.block.tileentity.TileEntityWandWorkBench;
import net.lrsoft.primalarcane.gui.RenderGuiHandler;
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
import net.minecraftforge.registries.IForgeRegistry;
import scala.reflect.api.Trees.NewApi;
@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class BlockManager {
	public static List<BlockUniformTemplate> modBlockList = new ArrayList<>();
	public static BlockUniformTemplate wandWorkBench;
	public static BlockUniformTemplate lighterBlock;
	
	static {
		wandWorkBench = new BlockUniformTemplate(Material.IRON, "wand_workbench", TileEntityWandWorkBench.class, RenderGuiHandler.WAND_WORKBENCH_ID);
		modBlockList.add(wandWorkBench);
		
		lighterBlock = new BlockLighter();
		modBlockList.add(lighterBlock);
	}
	
	@SubscribeEvent
	public static void onCommonBlockInit(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		for(BlockUniformTemplate block : modBlockList) {
			registry.register(block);
			if(block.hasTileEntity()) {
				GameRegistry.registerTileEntity(block.getTileEntityClazz(), block.getRegistryName());
			}
		}
	}
	
	@SubscribeEvent
	public static void onCommonBlockItemInit(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		for(BlockUniformTemplate block : modBlockList) {
			if(block.getNeedItemBlock()) {
				registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
			}
		}
	}
	
	public static void onBlockRecipeInit() {

	}
	
}
