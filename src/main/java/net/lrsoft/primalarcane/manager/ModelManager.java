package net.lrsoft.primalarcane.manager;

import java.util.Map.Entry;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.block.BlockMachineTemplate;
import net.lrsoft.primalarcane.block.BlockUniform;
import net.lrsoft.primalarcane.block.renderer.LighterRenderer;
import net.lrsoft.primalarcane.block.tileentity.TileEntityLighterBlock;
import net.lrsoft.primalarcane.entity.EntityFireball;
import net.lrsoft.primalarcane.entity.renderer.FireSpellRenderer;
import net.lrsoft.primalarcane.item.ItemSpell;
import net.lrsoft.primalarcane.item.armor.ItemManaArmor;
import net.lrsoft.primalarcane.spell.SpellManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = PrimalArcane.MODID)
public class ModelManager {
	@SubscribeEvent
	public static void onItemBlockModelInit(ModelRegistryEvent event) {
		for (BlockUniform block : BlockManager.modBlockList) {
			if(block.getNeedItemBlock()) {
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
						new ModelResourceLocation(block.getRegistryName(), "normal"));
			}
		}
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLighterBlock.class, new LighterRenderer());
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

	@SubscribeEvent
	public static void onItemModelInit(ModelRegistryEvent event) {
		for (Item item : ItemManager.modItemList) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(item.getRegistryName(), "inventory"));
		}
	}

	@SubscribeEvent
	public static void onItemModelInit(SpellManager.SpellRegistryEvent event) {
		for (Entry<String, ItemSpell> entry : event.spellDict.entrySet()) {
			Item item = entry.getValue();
			ModelLoader.setCustomModelResourceLocation(item, 0,
					new ModelResourceLocation(PrimalArcane.MODID + ":spell", "inventory"));
		}
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
