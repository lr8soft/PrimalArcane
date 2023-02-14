package net.lrsoft.primalarcane.manager;

import java.util.ArrayList;
import java.util.List;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.block.BlockLighter;
import net.lrsoft.primalarcane.block.BlockMachineTemplate;
import net.lrsoft.primalarcane.block.BlockUniform;
import net.lrsoft.primalarcane.block.tileentity.TileEntityManaFurnace;
import net.lrsoft.primalarcane.block.tileentity.TileEntityRuneBench;
import net.lrsoft.primalarcane.block.tileentity.TileEntityWandWorkBench;
import net.lrsoft.primalarcane.gui.RenderGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class BlockManager {
	public static List<BlockUniform> modBlockList = new ArrayList<>();
	public static BlockUniform wandWorkBench;
	public static BlockUniform manaFurnace;
	public static BlockUniform runeBench;
	public static BlockUniform lighterBlock;
	
	static {
		wandWorkBench = new BlockMachineTemplate(Material.IRON, "wand_workbench", TileEntityWandWorkBench.class, RenderGuiHandler.WAND_WORKBENCH_ID);
		modBlockList.add(wandWorkBench);

		manaFurnace = new BlockMachineTemplate(Material.IRON, "mana_furnace", TileEntityManaFurnace.class, RenderGuiHandler.MANA_FUNRACE_ID, true);
		modBlockList.add(manaFurnace);

		runeBench = new BlockMachineTemplate(Material.IRON, "rune_bench", TileEntityRuneBench.class, RenderGuiHandler.RUNE_BENCH_ID);
		modBlockList.add(runeBench);

		lighterBlock = new BlockLighter();
		modBlockList.add(lighterBlock);
	}
	
	@SubscribeEvent
	public static void onCommonBlockInit(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();

		List<BlockUniform> activeList = new ArrayList<>();

		for(BlockUniform block : modBlockList) {
			registry.register(block);
			if(block.hasTileEntity()) {
				GameRegistry.registerTileEntity(block.getTileEntityClazz(), block.getRegistryName());
				if(block instanceof BlockMachineTemplate) {
					// 注册有active状态的方块
					BlockMachineTemplate activeClone = (BlockMachineTemplate)((BlockMachineTemplate) block).getActiveClone();
					if(activeClone != null) {
						registry.register(activeClone);
						activeList.add(activeClone);
					}
				}
			}
		}
		// 添加激活方块到列表去
		modBlockList.addAll(activeList);
	}
	
	@SubscribeEvent
	public static void onCommonBlockItemInit(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		for(BlockUniform block : modBlockList) {
			if(block.getNeedItemBlock()) {
				registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
			}
		}
	}
	
	public static void onBlockRecipeInit() {

	}
	
}
