package net.lrsoft.primalarcane.manager;

import java.util.ArrayList;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.item.ItemWand;
import net.lrsoft.primalarcane.item.armor.ItemManaArmor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class ItemManager {
	public static ArrayList<Item> modItemList = new ArrayList<>();
	public static ItemWand wand;

	public static ItemManaArmor mana_head;
	public static ItemManaArmor mana_chest;
	public static ItemManaArmor mana_legs;
	public static ItemManaArmor mana_feet;

	static {
		wand = new ItemWand("normal_wand");
		modItemList.add(wand);
		mana_head = new ItemManaArmor("mana_head", EntityEquipmentSlot.HEAD);
		mana_chest = new ItemManaArmor("mana_chest", EntityEquipmentSlot.CHEST);
		mana_legs = new ItemManaArmor("mana_legs", EntityEquipmentSlot.LEGS);
		mana_feet = new ItemManaArmor("mana_feet", EntityEquipmentSlot.FEET);
		modItemList.add(mana_head);
		modItemList.add(mana_chest);
		modItemList.add(mana_legs);
		modItemList.add(mana_feet);
	}

	@SubscribeEvent
	public static void onItemInit(RegistryEvent.Register<Item> event) {
		for (Item item : modItemList) {
			event.getRegistry().register(item);
		}

	}

	private static void onRecipeInit() {

	}

}
