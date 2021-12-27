package net.lrsoft.primalarcane.manager;

import java.util.Map.Entry;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.entity.EntityFireball;
import net.lrsoft.primalarcane.entity.EntityShootSpell;
import net.lrsoft.primalarcane.entity.renderer.FireSpellRenderer;
import net.lrsoft.primalarcane.item.ItemSpell;
import net.lrsoft.primalarcane.spell.Spell;
import net.lrsoft.primalarcane.spell.SpellManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = PrimalArcane.MODID)
public class ModelManager {
	@SubscribeEvent
	public static void onItemModelInit(ModelRegistryEvent event) {
		for (Item item : ItemManager.modItemList) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
		// ItemManager.electricShield.setTileEntityItemStackRenderer(new
		// net.lrsoft.mets.renderer.NanoShieldRenderer());
	}

	@SubscribeEvent
	public static void onItemModelInit(SpellManager.SpellRegistryEvent event) {
		for (Entry<String, ItemSpell> entry : event.spellDict.entrySet()) {
			Item item = entry.getValue();
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(PrimalArcane.MODID + ":spell", "inventory"));
		}
	}

	@SubscribeEvent
	public static void onBlockModelInit(ModelRegistryEvent event) {
		// ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BlockManager.niobiumOre),
		// 0, new
		// ModelResourceLocation(BlockManager.niobiumOre.getRegistryName(),"normal"));
		// ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLighterBlock.class,
		// new LighterRenderer());
	}

	@SubscribeEvent
	public static void onEntityModelInit(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(EntityFireball.class, new IRenderFactory<EntityFireball>() {
			public Render<EntityFireball> createRenderFor(RenderManager manager) {
				return (Render<EntityFireball>) new FireSpellRenderer(manager,
						new ResourceLocation(PrimalArcane.MODID, "textures/entity/fireball.png"));
			}
		});
	}

	private static final ResourceLocation fluidLocation = new ResourceLocation(PrimalArcane.MODID, "fluid");

	public static void registerFluidRender(BlockFluidBase blockFluid, Fluid fluid) {
		final Item itemFluid = Item.getItemFromBlock(blockFluid);
		ModelLoader.setCustomMeshDefinition(itemFluid, new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation(fluidLocation, "type=" + fluid.getName());
			}
		});
		ModelLoader.setCustomStateMapper(blockFluid, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(fluidLocation, "type=" + fluid.getName());
			}
		});
	}
}
