package net.lrsoft.primalarcane.spell;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.item.ItemSpell;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class SpellManager {
	private static Map<String, Spell> spellDict = new HashMap<String, Spell>();
	private static Map<String, ItemSpell> spellItemDict = new HashMap<>();

	public static class SpellRegistryEvent extends Event {
		public final Map<String, ItemSpell> spellDict;

		public SpellRegistryEvent(Map<String, ItemSpell> dict) {
			spellDict = dict;
		}
	}
	
	public static Spell fireball;
	public static Spell lightning;
	public static Spell lighter;

	static {
		fireball = new FireballSpell();
		lightning = new LightningSpell();
		lighter = new LighterSpell();
		
		addSpell(fireball);
		addSpell(lightning);
		addSpell(lighter);
	}

	public static Spell getSpell(String spellName) {
		return spellDict.getOrDefault(spellName, null);
	}

	public static ItemSpell getSpellItem(String spellName) {
		return spellItemDict.getOrDefault(spellName, null);
	}

	public static void addSpell(Spell spell) {
		spellDict.put(spell.getSpellName(), spell);
	}

	@SubscribeEvent
	public static void onItemInit(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		for (Entry<String, Spell> entry : spellDict.entrySet()) {
			ItemSpell spellItem = new ItemSpell(entry.getValue());
			spellItem.setRegistryName(PrimalArcane.MODID, entry.getKey());
			spellItem.setUnlocalizedName("primalarcane.spell." + entry.getKey());
			spellItem.setCreativeTab(PrimalArcane.CREATIVE_TAB);
			spellItem.setMaxStackSize(1);
			registry.register(spellItem);

			spellItemDict.put(entry.getKey(), spellItem);
		}
		MinecraftForge.EVENT_BUS.post(new SpellRegistryEvent(spellItemDict));
	}

}
