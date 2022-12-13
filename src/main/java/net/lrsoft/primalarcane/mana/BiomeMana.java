package net.lrsoft.primalarcane.mana;

import java.util.HashMap;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class BiomeMana {
	private static HashMap<Biome, Float> biomeManaRecoverySpeed = new HashMap<>();
	static {
		// mana recovery speed
		addBiomeManaRecoverySpeed(Biomes.VOID, 10.0f);
		addBiomeManaRecoverySpeed(Biomes.HELL, 10.0f);
		addBiomeManaRecoverySpeed(Biomes.OCEAN, 6.0f);
		addBiomeManaRecoverySpeed(Biomes.BIRCH_FOREST, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.BIRCH_FOREST_HILLS, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.FOREST, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.JUNGLE, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.JUNGLE_EDGE, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.JUNGLE_HILLS, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.REDWOOD_TAIGA, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.REDWOOD_TAIGA_HILLS, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.ROOFED_FOREST, 5.0f);
	}

	public static void addBiomeManaRecoverySpeed(Biome biome, float speed) {
		biomeManaRecoverySpeed.put(biome, speed);
	}
	
	/**
	 * Return the mana recovery speed of the biome, default 1.0f
	 * @param biome
	 * @return The positive-negative ratio of the mana
	 */
	public static float getBiomeManaRecoverySpeed(Biome biome) {
		return biomeManaRecoverySpeed.getOrDefault(biome, 1.0f);
	}
}
